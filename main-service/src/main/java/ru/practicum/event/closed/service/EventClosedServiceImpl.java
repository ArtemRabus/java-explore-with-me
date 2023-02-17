package ru.practicum.event.closed.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.enums.State;
import ru.practicum.enums.StateActionUser;
import ru.practicum.enums.Status;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.dto.*;
import ru.practicum.event.model.mapper.EventMapper;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.LocationRepository;
import ru.practicum.exception.model.*;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.dto.ParticipationRequestDto;
import ru.practicum.request.model.mapper.RequestMapper;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.enums.Status.CONFIRMED;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class EventClosedServiceImpl implements EventClosedService {
    final EventRepository eventRepository;
    final RequestRepository requestRepository;
    final RequestMapper requestMapper;
    final EventMapper mapper;
    final UserRepository userRepository;
    final CategoryRepository categoryRepository;
    final LocationRepository locationRepository;

    @Override
    public EventFullOutDto create(Long userId, NewEventInDto newEventDto) {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("invalid event date");
        }
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with id: " + userId + " not found"));
        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() ->
                new NotFoundException("Category with id: " + newEventDto.getCategory() + " not found"));
        Location location = locationRepository.save(newEventDto.getLocation());
        Event event = eventRepository.save(mapper.toEvent(newEventDto, category, user, location));
        return mapper.toEventFull(event);
    }

    @Override
    @Transactional
    public EventFullOutDto updateByUser(Long userId, Long eventId, UpdateUserEvent eventRequestDto) {
        Event event = eventRepository.findAllByInitiatorIdAndId(userId, eventId).orElseThrow(() ->
                new NotFoundException("event with id: " + eventId + " not found"));
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("event is published");
        }
        if (eventRequestDto.getCategory() != null) {
            Category category = categoryRepository.findById(eventRequestDto.getCategory()).orElseThrow(() ->
                    new NotFoundException("category with id:" + eventRequestDto.getCategory() + " not found"));
            event.setCategory(category);
        }
        if (eventRequestDto.getEventDate() != null) {
            if (eventRequestDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ConflictException("invalid event date");
            }
        }
        if (eventRequestDto.getStateAction() == StateActionUser.SEND_TO_REVIEW) {
            event.setState(State.PENDING);
        }

        if (eventRequestDto.getStateAction() == StateActionUser.CANCEL_REVIEW) {
            event.setState(State.CANCELED);
        }
        if (eventRequestDto.getAnnotation() != null) {
            event.setAnnotation(eventRequestDto.getAnnotation());
        }
        if (eventRequestDto.getDescription() != null) {
            event.setDescription(eventRequestDto.getDescription());
        }
        if (eventRequestDto.getTitle() != null) {
            event.setTitle(eventRequestDto.getTitle());
        }

        if (eventRequestDto.getRequestModeration() != null) {
            event.setRequestModeration(eventRequestDto.getRequestModeration());
        }
        if (eventRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventRequestDto.getParticipantLimit());
        }

        if (eventRequestDto.getPaid() != null) {
            event.setPaid(eventRequestDto.getPaid());
        }
        if (eventRequestDto.getLocation() != null) {
            event.setLocation(eventRequestDto.getLocation());
        }
        return mapper.toEventFull(event);
    }

    @Override
    @Transactional
    public Collection<EventFullOutDto> getByUser(Long userId, PageRequest pageRequest) {
        return eventRepository.findAllByInitiatorId(userId, pageRequest).stream()
                .map(mapper::toEventFull).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullOutDto getByUserAndEvent(Long userId, Long eventId) {
        return mapper.toEventFull(eventRepository.findAllByInitiatorIdAndId(userId, eventId).orElseThrow(() ->
                new NotFoundException("event with id: " + eventId + " not found")));
    }

    @Override
    @Transactional
    public Collection<ParticipationRequestDto> getParticipationRequestsUser(Long userId, Long eventId) {
        return requestRepository.findAllByRequesterIdAndEventId(userId, eventId).stream()
                .map(requestMapper::fromRequest).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Event with id : " + eventId + " not found"));
        List<Request> requests = requestRepository.findRequestsForUpdate(eventId, eventRequestStatusUpdateRequest.getRequestIds());
        return getResult(requests, Status.valueOf(eventRequestStatusUpdateRequest.getStatus()), event);

    }

    private EventRequestStatusUpdateResult getResult(List<Request> requests, Status status, Event event) {
        switch (status) {
            case CONFIRMED:
                return confirmed(requests, event);
            case REJECTED:
                return reject(requests);
            default:
                return new EventRequestStatusUpdateResult();
        }
    }

    private EventRequestStatusUpdateResult confirmed(List<Request> requests, Event event) {
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult(new ArrayList<>(), new ArrayList<>());
        for (Request request : requests) {
            try {
                validRequestStatus(request);
            } catch (ConflictException e) {
                continue;
            }
            if (event.getParticipantLimit() - requestRepository.findConfirmedRequests(event.getId(), CONFIRMED) <= 0) {
                throw new ConflictException("event was max limit");
            } else {
                request.setStatus(CONFIRMED);
                request = requestRepository.save(request);
                result.getConfirmedRequests().add(requestMapper.fromRequest(request));
            }
        }
        return result;
    }

    private EventRequestStatusUpdateResult reject(List<Request> requests) {
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult(new ArrayList<>(), new ArrayList<>());
        for (Request request : requests) {
            validRequestStatus(request);
            request.setStatus(Status.REJECTED);
            request = requestRepository.save(request);
            result.getRejectedRequests().add(requestMapper.fromRequest(request));
        }
        return result;
    }

    private void validRequestStatus(Request request) throws ConflictException {
        if (!request.getStatus().equals(Status.PENDING)) {
            throw new ConflictException("request status not pending");
        }
    }
}
