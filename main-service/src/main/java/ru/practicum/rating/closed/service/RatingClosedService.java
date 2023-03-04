package ru.practicum.rating.closed.service;

import ru.practicum.event.model.dto.EventShortOutDto;

import java.util.Collection;

public interface RatingClosedService {
    EventShortOutDto addLike(Long userId, Long eventId);

    void deleteLike(Long userId, Long eventId);

    EventShortOutDto addDislike(Long userId, Long eventId);

    void deleteDislike(Long userId, Long eventId);

    Collection<EventShortOutDto> getUserLikes(Long userId);

    Collection<EventShortOutDto> getUserDislikes(Long userId);
}
