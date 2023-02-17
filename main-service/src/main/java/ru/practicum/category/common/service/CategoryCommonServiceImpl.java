package ru.practicum.category.common.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.dto.CategoryDto;
import ru.practicum.category.model.mapper.CategoryMapper;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.model.NotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CategoryCommonServiceImpl implements CategoryCommonService {
    final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Collection<CategoryDto> getCategories(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest)
                .stream().map(CategoryMapper::fromCategory).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDto getCategoryById(Long id) {
        return CategoryMapper.fromCategory(categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Category with id: " + id + " not found")));
    }
}
