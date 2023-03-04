package ru.practicum.user.admin.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.user.model.dto.UserOutDto;

import java.util.Collection;
import java.util.List;

public interface UserAdminService {
    UserOutDto createUser(UserOutDto userDto);

    Collection<UserOutDto> getUsers(List<Long> usersId, PageRequest pageRequest);

    void delete(Long id);
}
