package ru.practicum.category.model.mapper;

import ru.practicum.category.model.Category;
import ru.practicum.category.model.dto.CategoryDto;
import ru.practicum.category.model.dto.NewCategoryDto;

public class CategoryMapper {

    public static CategoryDto fromCategory(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category toCategory(NewCategoryDto categoryDto) {
        return new Category(null, categoryDto.getName());
    }
}
