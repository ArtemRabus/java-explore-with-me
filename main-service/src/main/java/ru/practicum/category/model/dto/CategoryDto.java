package ru.practicum.category.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {
    @NotNull(message = "Id cannot be null")
    Long id;
    @NotNull(message = "Category name cannot be null")
    String name;
}
