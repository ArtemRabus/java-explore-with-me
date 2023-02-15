package ru.practicum.user.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.utility.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserOutDto {
    Long id;
    @NotBlank(groups = {Create.class})
    String name;
    @Email(groups = {Create.class})
    @NotBlank(groups = {Create.class})
    String email;
}
