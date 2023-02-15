package ru.practicum.compilation.common.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.compilation.model.dto.CompilationDto;

import java.util.Collection;

public interface CompilationCommonService {
    Collection<CompilationDto> getAll(PageRequest pageRequest, Boolean pinned);

    CompilationDto getById(Long id);
}
