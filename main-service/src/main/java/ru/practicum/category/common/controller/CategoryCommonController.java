package ru.practicum.category.common.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.common.service.CategoryCommonService;
import ru.practicum.category.model.dto.CategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryCommonController {
    final CategoryCommonService service;

    @GetMapping
    public Collection<CategoryDto> getAllCategories(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                    @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("public get all categories with param: from: {}, size: {}", from, size);
        return service.getCategories(PageRequest.of(from, size));
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        log.info("get category by id: {}", id);
        return service.getCategoryById(id);
    }
}
