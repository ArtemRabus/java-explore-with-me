package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.State;
import ru.practicum.enums.Status;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.exception.model.NotFoundException;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.dto.ParticipationRequestDto;
import ru.practicum.request.model.mapper.RequestMapper;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceIml implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public Collection<ParticipationRequestDto> getUserRequests(Long userId) {
        return requestRepository.findAllByRequesterId(userId).stream()
                .map(requestMapper::fromRequest).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto create(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            throw new NotFoundException("event with id:" + eventId + " not found");
        });
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new NotFoundException("user with id:" + userId + " not found");
                });
        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("user was initiator");
        }
        if (event.getState() != State.PUBLISHED) {
            throw new ConflictException("event not publisher");
        }
        List<Request> requests = requestRepository.findAllByEvent_IdIs(eventId);
        if (requests.stream().anyMatch(r -> r.getRequester().getId().equals(userId))) {
            throw new ConflictException("user already sent request");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= requests.size()) {
            throw new ConflictException("event was max limit");
        }
        Request request = requestMapper.createRequest(requester, event);
        return requestMapper.fromRequest(requestRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        Request request = requestRepository.findByIdAndRequesterId(requestId, userId).orElseThrow(() ->
                new NotFoundException("request not found"));
        request.setStatus(Status.CANCELED);
        return requestMapper.fromRequest(requestRepository.save(request));
    }
}
