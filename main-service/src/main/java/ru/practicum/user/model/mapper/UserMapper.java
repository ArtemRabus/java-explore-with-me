package ru.practicum.user.model.mapper;

import ru.practicum.user.model.User;
import ru.practicum.user.model.dto.UserOutDto;
import ru.practicum.user.model.dto.UserShortDto;


public class UserMapper {

    public static UserOutDto fromUser(User user) {
        return new UserOutDto(user.getId(), user.getName(), user.getEmail());
    }

    public static UserShortDto fromUserToShortUser(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public static User fromDtoToUser(UserOutDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }

}
