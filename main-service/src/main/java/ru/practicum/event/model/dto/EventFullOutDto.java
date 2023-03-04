package ru.practicum.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.category.model.dto.CategoryDto;
import ru.practicum.enums.State;
import ru.practicum.event.model.Location;
import ru.practicum.user.model.dto.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.utility.TimePattern.TIME_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullOutDto {
    Long id;
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    @JsonFormat(pattern = TIME_PATTERN)
    LocalDateTime createdOn = LocalDateTime.now();
    String description;
    @JsonFormat(pattern = TIME_PATTERN)
    LocalDateTime eventDate;
    UserShortDto initiator;
    Location location;
    boolean paid;
    int participantLimit;
    @JsonFormat(pattern = TIME_PATTERN)
    LocalDateTime publishedOn;
    boolean requestModeration;
    State state;
    String title;
    Long views;
}
