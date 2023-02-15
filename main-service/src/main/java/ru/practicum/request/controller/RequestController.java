package ru.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.model.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestService;

import java.util.Collection;

@RestController
@RequestMapping("users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestService service;

    @GetMapping
    public Collection<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) {
        log.info("user with id: {} get all requests", userId);
        return service.getUserRequests(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable Long userId,
                                                 @RequestParam Long eventId) {
        log.info("user with id: {} create requests to event with id: {}", userId, eventId);
        return service.create(userId, eventId);
    }

    @PatchMapping("/{reqId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long reqId) {
        log.info("user with id: {} cancel request with id: {}", userId, reqId);
        return service.cancel(userId, reqId);
    }
}

