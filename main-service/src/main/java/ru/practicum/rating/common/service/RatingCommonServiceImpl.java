package ru.practicum.rating.common.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.enums.State;
import ru.practicum.event.model.dto.EventShortOutDto;
import ru.practicum.event.model.mapper.EventMapper;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.user.model.dto.UserShortDto;
import ru.practicum.user.model.mapper.UserMapper;
import ru.practicum.user.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingCommonServiceImpl implements RatingCommonService {
    final EventRepository eventRepository;
    final UserRepository userRepository;
    final EventMapper eventMapper;

    @Override
    public List<EventShortOutDto> getPopularEvents(int from, int size) {
        return eventRepository.findAllByState(State.PUBLISHED, PageRequest.of(from, size)).stream()
                .map(eventMapper::toShortEvent).sorted(Comparator.comparingLong(EventShortOutDto::getRatings).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<UserShortDto> getPopularUsers(PageRequest pageRequest) {
        return userRepository.findAllSortByOrderByRatingsDesc(pageRequest).stream()
                .map(UserMapper::fromUserToShortUser).collect(Collectors.toList());
    }
}
