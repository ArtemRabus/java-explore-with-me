package ru.practicum.user.admin.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.admin.service.UserAdminService;
import ru.practicum.user.model.dto.UserOutDto;
import ru.practicum.utility.Create;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAdminController {
    final UserAdminService service;

    @GetMapping
    public Collection<UserOutDto> getAll(@RequestParam(required = false) List<Long> ids,
                                      @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                      @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("admin get all users with ids: {} with param: from: {}, size: {}", ids, from, size);
        return service.getUsers(ids, PageRequest.of(from, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutDto createUser(@RequestBody @Validated(Create.class) UserOutDto userDto) {
        log.info("admin create user : {}", userDto);
        return service.createUser(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("admin delete user with id: {}", id);
        service.delete(id);
    }
}
