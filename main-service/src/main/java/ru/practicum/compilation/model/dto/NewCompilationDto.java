package ru.practicum.compilation.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {
    @NotNull(message = "The title cannot be null")
    @NotBlank(message = "The title cannot be blank")
    String title;
    Boolean pinned = false;
    List<Long> events;
}
