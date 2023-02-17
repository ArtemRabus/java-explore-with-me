package ru.practicum.user.admin.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.exception.model.NotFoundException;
import ru.practicum.user.model.dto.UserOutDto;
import ru.practicum.user.model.mapper.UserMapper;
import ru.practicum.user.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAdminServiceImpl implements UserAdminService {
    final UserRepository userRepository;

    @Override
    public UserOutDto createUser(UserOutDto userDto) {
        try {
            return UserMapper.fromUser(userRepository.save(UserMapper.fromDtoToUser(userDto)));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("User with email: " + userDto.getEmail() + " already exist");
        }
    }

    @Override
    public Collection<UserOutDto> getUsers(List<Long> usersId, PageRequest pageRequest) {
        return userRepository.findByIdIsIn(usersId, pageRequest)
                .stream().map(UserMapper::fromUser).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id: " + id + " not found"));
        userRepository.deleteById(id);
    }
}
