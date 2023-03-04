package ru.practicum.event.common.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.event.model.dto.EventFullOutDto;
import ru.practicum.event.model.dto.EventShortOutDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventCommonService {
    List<EventShortOutDto> getAll(String text, List<Long> categories,
                               Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                               Boolean onlyAvailable, PageRequest pageRequest);

    EventFullOutDto getById(Long id);
}
