package ru.practicum.category.admin.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.dto.CategoryDto;
import ru.practicum.category.model.dto.NewCategoryDto;
import ru.practicum.category.model.mapper.CategoryMapper;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.exception.model.NotFoundException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryAdminServiceImpl implements CategoryAdminService {
    final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(NewCategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new ConflictException("Category with name: " + categoryDto.getName() + " already exist");
        }
        return CategoryMapper.fromCategory(categoryRepository.save(CategoryMapper.toCategory(categoryDto)));
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, NewCategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Category with id: " + id + " not found"));
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new ConflictException("Category with name: " + categoryDto.getName() + " already exist");
        }
        category.setName(categoryDto.getName());
        return CategoryMapper.fromCategory(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
            categoryRepository.deleteById(id);
    }
}
