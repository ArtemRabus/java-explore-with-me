package ru.practicum.compilation.common.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.exception.model.NotFoundException;
import ru.practicum.compilation.model.dto.CompilationDto;
import ru.practicum.compilation.model.mapper.CompilationMapper;
import ru.practicum.compilation.repository.CompilationRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationCommonServiceImpl implements CompilationCommonService {
    final CompilationRepository compilationRepository;
    final CompilationMapper mapper;

    @Override
    public Collection<CompilationDto> getAll(PageRequest pageRequest, Boolean pinned) {
        if (pinned != null) {
            return compilationRepository.findAllByPinned(pinned, pageRequest).stream()
                    .map(mapper::toCompilationDto).collect(Collectors.toList());
        } else {
            return compilationRepository.findAll(pageRequest).stream()
                    .map(mapper::toCompilationDto).collect(Collectors.toList());
        }
    }

    @Override
    public CompilationDto getById(Long id) {
        return mapper.toCompilationDto(compilationRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Compilation with id:" + id + " not found")));
    }
}
