package ru.practicum.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.model.Location;
import ru.practicum.utility.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.utility.TimePattern.TIME_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventInDto {
    Long id;
    @NotBlank(groups = {Create.class})
    @Size(max = 2000, min = 20)
    String annotation;
    @NotNull(groups = {Create.class})
    Long category;
    @NotBlank(groups = {Create.class})
    @Size(max = 7000, min = 20)
    String description;
    @JsonFormat(pattern = TIME_PATTERN)
    LocalDateTime eventDate;
    @NotNull(groups = {Create.class})
    Location location;
    boolean paid;
    @PositiveOrZero
    int participantLimit;
    boolean requestModeration;
    @NotBlank(groups = {Create.class})
    @Size(max = 120, min = 3)
    String title;
}
