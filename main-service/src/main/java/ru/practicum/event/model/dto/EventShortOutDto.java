package ru.practicum.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.category.model.dto.CategoryDto;
import ru.practicum.user.model.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.Set;

import static ru.practicum.utility.TimePattern.TIME_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortOutDto {
    Long id;
    String title;
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    @JsonFormat(pattern = TIME_PATTERN)
    LocalDateTime eventDate;
    UserShortDto initiator;
    Boolean paid;
    Long views;
    @JsonIgnore
    Integer participantLimit;
    Long ratings;
    Set<UserShortDto> likes;
    Set<UserShortDto> dislikes;
}
