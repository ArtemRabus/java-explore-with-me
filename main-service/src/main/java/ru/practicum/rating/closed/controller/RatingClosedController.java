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

    @PatchMapping("/like/{eventId}")
    public EventShortOutDto addLike(@PathVariable Long userId,
                                    @PathVariable Long eventId) {
        log.info("user with id {} to like event {}", userId, eventId);
        return ratingClosedService.addLike(userId, eventId);
    }

    @DeleteMapping("/like/{eventId}")
    public void deleteLike(@PathVariable Long userId,
                           @PathVariable Long eventId) {
        log.info("user {} delete like event {}", userId, eventId);
        ratingClosedService.deleteLike(userId, eventId);
    }

    @PatchMapping("/dislike/{eventId}")
    public EventShortOutDto addDislike(@PathVariable Long userId,
                                       @PathVariable Long eventId) {
        log.info("user {} dislike event {}", userId, eventId);
        return ratingClosedService.addDislike(userId, eventId);
    }

    @DeleteMapping("/dislike/{eventId}")
    public void deleteDisLike(@PathVariable Long userId,
                              @PathVariable Long eventId) {
        log.info("user {} delete dislike event {}", userId, eventId);
        ratingClosedService.deleteDislike(userId, eventId);
    }

    @GetMapping("/like")
    public Collection<EventShortOutDto> getUserLikes(@PathVariable Long userId) {
        log.info("user with id {} get liked events", userId);
        return ratingClosedService.getUserLikes(userId);
    }

    @GetMapping("/dislike")
    public Collection<EventShortOutDto> getUserDislikes(@PathVariable Long userId) {
        log.info("user with id {} get disliked events", userId);
        return ratingClosedService.getUserDislikes(userId);
    }
}