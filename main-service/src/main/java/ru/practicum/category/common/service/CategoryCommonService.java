package ru.practicum.category.common.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.category.model.dto.CategoryDto;

import java.util.Collection;

public interface CategoryCommonService {
    Collection<CategoryDto> getCategories(PageRequest pageRequest);

    CategoryDto getCategoryById(Long id);
}
