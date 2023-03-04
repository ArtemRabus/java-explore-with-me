package ru.practicum.compilation.admin.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.admin.service.CompilationAdminService;
import ru.practicum.compilation.model.dto.CompilationDto;
import ru.practicum.compilation.model.dto.NewCompilationDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationAdminController {
    final CompilationAdminService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@RequestBody @Valid NewCompilationDto compilationDto) {
        log.info("create new compilation: {}", compilationDto);
        return service.create(compilationDto);
    }

    @PatchMapping("/{id}")
    public CompilationDto update(@PathVariable Long id,
                                 @RequestBody NewCompilationDto compilationDto) {
        log.info("update compilation with id: {} to {}", id, compilationDto);
        return service.update(id, compilationDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("delete compilation with id: {}", id);
        service.delete(id);
    }
}
