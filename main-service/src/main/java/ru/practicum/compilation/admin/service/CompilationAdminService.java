package ru.practicum.compilation.admin.service;

import ru.practicum.compilation.model.dto.CompilationDto;
import ru.practicum.compilation.model.dto.NewCompilationDto;

public interface CompilationAdminService {
    CompilationDto create(NewCompilationDto newCompilationDto);

    CompilationDto update(Long id, NewCompilationDto newCompilationDto);

    void delete(Long id);

}
