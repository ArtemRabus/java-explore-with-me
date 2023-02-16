package ru.practicum.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.enums.StateActionUser;
import ru.practicum.event.model.Location;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.utility.TimePattern.TIME_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserEvent {
    @Size(max = 2000, min = 20)
    String annotation;
    @Size(max = 120, min = 3)
    String title;
    Long category;
    @Size(max = 7000, min = 20)
    String description;
    @JsonFormat(pattern = TIME_PATTERN)
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Long eventId;
    @PositiveOrZero
    Integer participantLimit;
    Boolean requestModeration;
    StateActionUser stateAction;
}

