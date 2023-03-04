package ru.practicum.compilation.common.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.common.service.CompilationCommonService;
import ru.practicum.compilation.model.dto.CompilationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping("/compilations")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationCommonController {
    final CompilationCommonService service;

    @GetMapping
    public Collection<CompilationDto> getAll(@RequestParam(required = false) Boolean pinned,
                                             @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                             @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("get all compilation with param: pinned: {}, from: {}, size: {}", pinned, from, size);
        return service.getAll(PageRequest.of(from, size), pinned);
    }

    @GetMapping("/{id}")
    public CompilationDto getById(@PathVariable Long id) {
        log.info("get compilation with id: {}", id);
        return service.getById(id);
    }
}
