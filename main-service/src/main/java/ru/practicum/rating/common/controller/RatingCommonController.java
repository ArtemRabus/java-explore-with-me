package ru.practicum.rating.common.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.event.model.dto.EventShortOutDto;
import ru.practicum.rating.common.service.RatingCommonService;
import ru.practicum.user.model.dto.UserShortDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingCommonController {
    final RatingCommonService ratingCommonService;

    @GetMapping("/users")
    public Collection<UserShortDto> getPopularUsers(
            @PositiveOrZero @RequestParam(defaultValue = "0") int from,
            @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("get popular users: from: {}, size: {}", from, size);
        return ratingCommonService.getPopularUsers(PageRequest.of(from, size));
    }

    @GetMapping("/events")
    public Collection<EventShortOutDto> getPopularEvents(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                         @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("get popular events: from: {}, size: {}", from, size);
        return ratingCommonService.getPopularEvents(from, size);
    }
}
