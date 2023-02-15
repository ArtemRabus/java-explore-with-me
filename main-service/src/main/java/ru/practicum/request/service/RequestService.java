package ru.practicum.request.service;

import ru.practicum.request.model.dto.ParticipationRequestDto;

import java.util.Collection;

public interface RequestService {
    Collection<ParticipationRequestDto> getUserRequests(Long userId);
    ParticipationRequestDto create(Long userId, Long eventId);
    ParticipationRequestDto cancel(Long userId, Long requestId);
}
