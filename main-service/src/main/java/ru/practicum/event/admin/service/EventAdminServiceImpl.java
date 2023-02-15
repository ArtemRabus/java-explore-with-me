package ru.practicum.event.admin.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.enums.State;
import ru.practicum.enums.StateActionAdmin;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.dto.EventFullOutDto;
import ru.practicum.event.model.dto.UpdateEventRequest;
import ru.practicum.event.model.mapper.EventMapper;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.LocationRepository;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.exception.model.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {
    final EventRepository eventRepository;
    final EventMapper mapper;
    final CategoryRepository categoryRepository;
    final LocationRepository locationRepository;

    @Override
    public List<EventFullOutDto> getAllByAdmin(List<Long> users, List<State> states, List<Long> categories,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                               PageRequest pageRequest) {
        return eventRepository.findAllByInitiator_IdInAndState_InAndCategory_IdInAndEventDateBetween(users,
                states, categories, rangeStart, rangeEnd, pageRequest).stream().map(mapper::toEventFull).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullOutDto updateAdmin(Long eventId, UpdateEventRequest eventRequestDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("event with id: " + eventId + " not found"));
        if (eventRequestDto.getStateAction() == StateActionAdmin.PUBLISH_EVENT) {
            if (event.getState() != State.PENDING) {
                throw new ConflictException("Event with id=" + eventId + " cannot be published");
            }
            LocalDateTime published = LocalDateTime.now();
            event.setPublishedOn(published);
            event.setState(State.PUBLISHED);
        }

        if (eventRequestDto.getStateAction() == StateActionAdmin.REJECT_EVENT) {
            if (event.getState() == State.PUBLISHED && event.getPublishedOn().isBefore(LocalDateTime.now())) {
                throw new ConflictException("Event with id=" + eventId + " cannot be rejected");
            }
            event.setState(State.CANCELED);
        }
        if (eventRequestDto.getAnnotation() != null) {
            event.setAnnotation(eventRequestDto.getAnnotation());
        }

        if (eventRequestDto.getCategory() != null) {
            event.setCategory(categoryRepository.findById(eventRequestDto.getCategory()).orElseThrow(() ->
                    new NotFoundException("category not found")));
        }

        if (eventRequestDto.getDescription() != null) {
            event.setDescription(eventRequestDto.getDescription());
        }

        if (eventRequestDto.getEventDate() != null) {
            if (eventRequestDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ConflictException("event date is not correct");
            }
            event.setEventDate(eventRequestDto.getEventDate());
        }

        if (eventRequestDto.getLocation() != null) {
            locationRepository.save(eventRequestDto.getLocation());
            event.setLocation(eventRequestDto.getLocation());
        }

        if (eventRequestDto.getPaid() != null) {
            event.setPaid(eventRequestDto.getPaid());
        }

        if (eventRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventRequestDto.getParticipantLimit());
        }

        if (eventRequestDto.getRequestModeration() != null) {
            event.setRequestModeration(eventRequestDto.getRequestModeration());
        }

        if (eventRequestDto.getTitle() != null) {
            event.setTitle(eventRequestDto.getTitle());
        }

        event = eventRepository.save(event);
        return mapper.toEventFull(event);
    }
}
