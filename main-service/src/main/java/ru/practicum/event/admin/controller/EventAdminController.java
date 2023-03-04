package ru.practicum.event.admin.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.enums.State;
import ru.practicum.event.admin.service.EventAdminService;
import ru.practicum.event.model.dto.EventFullOutDto;
import ru.practicum.event.model.dto.UpdateEventRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.utility.TimePattern.TIME_PATTERN;

@RestController
@RequestMapping("/admin/events")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EventAdminController {
    final EventAdminService service;

    @PatchMapping("/{eventId}")
    public EventFullOutDto update(@PathVariable Long eventId,
                               @RequestBody UpdateEventRequest updateEventAdminRequest) {
        log.info("admin update event with id: {} to {}", eventId, updateEventAdminRequest);
        return service.updateAdmin(eventId, updateEventAdminRequest);
    }

    @GetMapping
    public Collection<EventFullOutDto> getAll(@RequestParam(defaultValue = "") Set<Long> users,
                                           @RequestParam(defaultValue = "") Set<String> states,
                                           @RequestParam(defaultValue = "") Set<Long> categories,
                                           @RequestParam(defaultValue = "") @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeStart,
                                           @RequestParam(defaultValue = "") @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeEnd,
                                           @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                           @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("admin get all events with param: users: {}, states: {}, categories:{}, range start: {}, range end: {}, from: {}, size: {}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        return service.getAllByAdmin(new ArrayList<>(users), states.stream().map(State::valueOf).collect(Collectors.toList()),
                new ArrayList<>(categories), rangeStart, rangeEnd, PageRequest.of(from, size));
    }
}
