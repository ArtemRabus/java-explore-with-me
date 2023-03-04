package ru.practicum.compilation.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.model.dto.CompilationDto;
import ru.practicum.compilation.model.dto.NewCompilationDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.mapper.EventMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventMapper eventMapper;

    public Compilation fromNewDto(NewCompilationDto newCompilationDto, Set<Event> eventSet) {
        return Compilation.builder()
                .id(null)
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .events(eventSet)
                .build();
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(), compilation.getPinned(), compilation.getTitle(),
                        compilation.getEvents().stream().map(eventMapper::toShortEvent).collect(Collectors.toSet()));
    }
}
