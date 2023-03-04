package ru.practicum.event.closed.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.event.model.dto.*;
import ru.practicum.request.model.dto.ParticipationRequestDto;

import java.util.Collection;

public interface EventClosedService {
    EventFullOutDto create(Long userId, NewEventInDto newEventDto);

    EventFullOutDto updateByUser(Long userId, Long eventId, UpdateUserEvent eventRequestDto);

    Collection<EventFullOutDto> getByUser(Long userId, PageRequest pageRequest);

    EventFullOutDto getByUserAndEvent(Long userId, Long eventId);

    Collection<ParticipationRequestDto> getParticipationRequestsUser(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);

}
