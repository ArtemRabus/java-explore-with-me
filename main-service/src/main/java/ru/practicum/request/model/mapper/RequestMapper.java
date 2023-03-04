package ru.practicum.request.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.enums.Status;
import ru.practicum.event.model.Event;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.dto.ParticipationRequestDto;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    public Request createRequest(User user, Event event) {
        return new Request(null, LocalDateTime.now(), event, user, event.getRequestModeration() ? Status.PENDING : Status.CONFIRMED);
    }

    public ParticipationRequestDto fromRequest(Request request) {
        return new ParticipationRequestDto(request.getId(), request.getCreated(), request.getEvent().getId(),
                request.getRequester().getId(), request.getStatus());
    }
}
