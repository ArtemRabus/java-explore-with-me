package ru.practicum.rating.closed.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.enums.State;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.dto.EventShortOutDto;
import ru.practicum.event.model.mapper.EventMapper;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.exception.model.NotFoundException;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingClosedServiceImpl implements RatingClosedService {
    final UserRepository userRepository;
    final EventRepository eventRepository;
    final EventMapper eventMapper;

    @Override
    public EventShortOutDto addLike(Long userId, Long eventId) {
        User user = getUser(userId);
        Event event = getEvent(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            if (event.getLikes().contains(user)) {
                throw new ConflictException("User already like this event");
            }
            user.getDislikedEvents().remove(event);
            user.getLikedEvents().add(event);
            event.getDislikes().remove(user);
            event.getLikes().add(user);
            incrementUserRating(event.getInitiator());
            userRepository.save(user);
            return eventMapper.toShortEvent(event);
        } else {
            throw new ConflictException("event not published");
        }
    }

    @Override
    public void deleteLike(Long userId, Long eventId) {
        User user = getUser(userId);
        Event event = getEvent(eventId);
        user.getLikedEvents().remove(event);
        decrementUserRating(event.getInitiator());
        userRepository.save(user);
    }

    @Override
    public EventShortOutDto addDislike(Long userId, Long eventId) {
        User user = getUser(userId);
        Event event = getEvent(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            if (event.getDislikes().contains(user)) {
                throw new ConflictException("user already dislike this event");
            }
            event.getLikes().remove(user);
            event.getDislikes().add(user);
            user.getLikedEvents().remove(event);
            user.getDislikedEvents().add(event);
            decrementUserRating(event.getInitiator());
            userRepository.save(user);
            return eventMapper.toShortEvent(event);
        } else {
            throw (new ConflictException("event not published"));
        }
    }

    @Override
    public void deleteDislike(Long userId, Long eventId) {
        User user = getUser(userId);
        Event event = getEvent(eventId);
        user.getDislikedEvents().remove(event);
        incrementUserRating(event.getInitiator());
        userRepository.save(user);
    }

    @Override
    public Collection<EventShortOutDto> getUserLikes(Long userId) {
        User user = getUser(userId);
        return user.getLikedEvents().stream().map(eventMapper::toShortEvent).collect(Collectors.toList());
    }

    @Override
    public Collection<EventShortOutDto> getUserDislikes(Long userId) {
        User user = getUser(userId);
        return user.getDislikedEvents().stream().map(eventMapper::toShortEvent).collect(Collectors.toList());
    }

    private void incrementUserRating(User initiator) {
        initiator.setRatings(initiator.getRatings() + 1);
        userRepository.save(initiator);
    }

    private void decrementUserRating(User initiator) {
        initiator.setRatings(initiator.getRatings() - 1);
        userRepository.save(initiator);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("user with id: " + userId + " not found"));
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("event with id: " + eventId + " not found"));
    }
}
