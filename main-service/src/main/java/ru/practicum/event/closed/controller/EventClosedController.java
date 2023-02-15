package ru.practicum.event.closed.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.closed.service.EventClosedService;
import ru.practicum.event.model.dto.*;
import ru.practicum.request.model.dto.ParticipationRequestDto;
import ru.practicum.utility.Create;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/events")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Validated
public class EventClosedController {
    final EventClosedService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullOutDto create(@PathVariable Long userId,
                               @RequestBody @Validated(Create.class) NewEventInDto newEventDto) {
        log.info("user with id: {} create event: {}", userId, newEventDto);
        return service.create(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullOutDto getById(@PathVariable Long userId,
                                @PathVariable Long eventId) {
        log.info("user with id: {}, get event with id: {}", userId, eventId);
        return service.getByUserAndEvent(userId, eventId);
    }

    @GetMapping
    public Collection<EventFullOutDto> getUserEvents(@PathVariable Long userId,
                                                  @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                  @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("user gets all his events with param: from: {}, size: {}", from, size);
        return service.getByUser(userId, PageRequest.of(from, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullOutDto update(@PathVariable Long userId, @PathVariable Long eventId,
                               @RequestBody @Validated UpdateUserEvent updateUserEvent) {
        log.info("user with id: {} update event with id: {} to {}", userId, eventId, updateUserEvent);
        return service.updateByUser(userId, eventId, updateUserEvent);
    }

    @GetMapping("/{eventId}/requests")
    public Collection<ParticipationRequestDto> getUserEventRequest(@PathVariable Long userId,
                                                                   @PathVariable Long eventId) {
        log.info("user with id: {} get all his requests to event with id: {}", userId, eventId);
        return service.getParticipationRequestsUser(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateUserEventRequests(@PathVariable() Long userId,
                                                                  @PathVariable() Long eventId,
                                                                  @RequestBody() EventRequestStatusUpdateRequest request) {
        log.info("user with id: {} update requests to event with id: {} to {}", userId, eventId, request);
        return service.updateRequests(userId, eventId, request);
    }

}