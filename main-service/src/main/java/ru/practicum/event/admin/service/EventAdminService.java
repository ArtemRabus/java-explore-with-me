package ru.practicum.event.admin.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.enums.State;
import ru.practicum.event.model.dto.EventFullOutDto;
import ru.practicum.event.model.dto.UpdateEventRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface EventAdminService {
    List<EventFullOutDto> getAllByAdmin(List<Long> users, List<State> states, List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        PageRequest pageRequest);

    EventFullOutDto updateAdmin(Long eventId, UpdateEventRequest eventRequestDto);

}
