package ru.practicum.rating.common.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.event.model.dto.EventShortOutDto;
import ru.practicum.user.model.dto.UserShortDto;

import java.util.List;

public interface RatingCommonService {
    List<EventShortOutDto> getPopularEvents(int from, int size);

    List<UserShortDto> getPopularUsers(PageRequest pageRequest);
}
