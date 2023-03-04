package ru.practicum.compilation.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.model.dto.EventShortOutDto;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDto {
    @NotNull(message = "Id compilation cannot be null")
    Long id;
    @NotNull(message = "Pinned cannot be null")
    boolean pinned;
    @NotNull(message = "Title compilation cannot be null")
    String title;
    Set<EventShortOutDto> events;
}
