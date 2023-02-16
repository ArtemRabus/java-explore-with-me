package ru.practicum.event.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static ru.practicum.utility.TimePattern.TIME_PATTERN;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EndpointHitInputDto {
    String app;
    String uri;
    String ip;
    @JsonFormat(pattern = TIME_PATTERN)
    LocalDateTime timestamp;
}

