package ru.practicum.rating.closed.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.dto.EventShortOutDto;
import ru.practicum.rating.closed.service.RatingClosedService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/{userId}/")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingClosedController {
    final RatingClosedService ratingClosedService;

    @GetMapping("/like")
    public Collection<EventShortOutDto> getUserLikes(@PathVariable Long userId) {
        log.info("the user with the id {} who get liked events", userId);
        return ratingClosedService.getUserLikes(userId);
    }

    @GetMapping("/dislike")
    public Collection<EventShortOutDto> getUserDislikes(@PathVariable Long userId) {
        log.info("the user with the id {} who get disliked events", userId);
        return ratingClosedService.getUserDislikes(userId);
    }

    @PatchMapping("/like/{eventId}")
    public EventShortOutDto addLike(@PathVariable Long userId,
                                    @PathVariable Long eventId) {
        log.info("the user with the id {} who liked the event {}", userId, eventId);
        return ratingClosedService.addLike(userId, eventId);
    }

    @DeleteMapping("/like/{eventId}")
    public void deleteLike(@PathVariable Long userId,
                           @PathVariable Long eventId) {
        log.info("the user with the id {} who delete like of the event {}", userId, eventId);
        ratingClosedService.deleteLike(userId, eventId);
    }

    @PatchMapping("/dislike/{eventId}")
    public EventShortOutDto addDislike(@PathVariable Long userId,
                                       @PathVariable Long eventId) {
        log.info("the user with the id {} who disliked the event {}", userId, eventId);
        return ratingClosedService.addDislike(userId, eventId);
    }

    @DeleteMapping("/dislike/{eventId}")
    public void deleteDisLike(@PathVariable Long userId,
                              @PathVariable Long eventId) {
        log.info("the user with the id {} who delete dislike of the event {}", userId, eventId);
        ratingClosedService.deleteDislike(userId, eventId);
    }
}