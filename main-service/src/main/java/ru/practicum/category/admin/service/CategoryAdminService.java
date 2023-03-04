package ru.practicum.category.admin.service;

import ru.practicum.category.model.dto.CategoryDto;
import ru.practicum.category.model.dto.NewCategoryDto;

public interface CategoryAdminService {
    CategoryDto updateCategory(Long id, NewCategoryDto categoryDto);

    CategoryDto createCategory(NewCategoryDto categoryDto);

    void deleteCategory(Long id);
}
