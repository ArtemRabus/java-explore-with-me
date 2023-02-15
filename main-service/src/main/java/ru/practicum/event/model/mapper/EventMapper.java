package ru.practicum.event.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.mapper.CategoryMapper;
import ru.practicum.enums.State;
import ru.practicum.enums.Status;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.dto.EventFullOutDto;
import ru.practicum.event.model.dto.EventShortOutDto;
import ru.practicum.event.model.dto.NewEventInDto;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.model.mapper.UserMapper;
import ru.practicum.utility.StatsService;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final RequestRepository requestRepository;
    private final StatsService statsService;

    public EventFullOutDto toEventFull(Event event) {
        Long confirmed = (long) requestRepository.findAllByEventIdAndStatus(event.getId(), Status.CONFIRMED).size();
        Long views = statsService.getViews("/events/" + event.getId());
        return new EventFullOutDto(
                event.getId(), event.getAnnotation(), CategoryMapper.fromCategory(event.getCategory()),
                confirmed, event.getCreatedOn(), event.getDescription(), event.getEventDate(),
                UserMapper.fromUserToShortUser(event.getInitiator()), event.getLocation(),
                event.getPaid(), event.getParticipantLimit(), event.getPublishedOn(), event.getRequestModeration(),
                event.getState(), event.getTitle(), views);
    }

    public Event toEvent(NewEventInDto newEventDto, Category category, User user, Location location) {
        return new Event(newEventDto.getId(), newEventDto.getAnnotation(), category, LocalDateTime.now(),
                user, newEventDto.getDescription(), newEventDto.getTitle(), newEventDto.getEventDate(),
                location, newEventDto.isPaid(), newEventDto.getParticipantLimit(), LocalDateTime.now(), newEventDto.isRequestModeration(),
                State.PENDING);
    }

    public EventShortOutDto toShortEvent(Event event) {
        Long confirmed = (long) requestRepository.findAllByEventIdAndStatus(event.getId(), Status.CONFIRMED).size();
        Long views = statsService.getViews("/events/" + event.getId());
        return new EventShortOutDto(event.getId(), event.getTitle(), event.getAnnotation(),
                CategoryMapper.fromCategory(event.getCategory()), confirmed, event.getEventDate(),
                UserMapper.fromUserToShortUser(event.getInitiator()), event.getPaid(),
                views, event.getParticipantLimit());
    }
}
