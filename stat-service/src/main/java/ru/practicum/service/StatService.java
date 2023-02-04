package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.Mapper;
import ru.practicum.model.ViewStats;
import ru.practicum.repository.StatRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StatService {
    final StatRepository statRepository;

    public void save(EndpointHit endpointHit) {
        statRepository.save(endpointHit);
        log.info("Information about the endpoint request is saved");
    }

    public List<ViewStats> findStat(String start, String end, String[] uris, boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(
                URLDecoder.decode(start, StandardCharsets.UTF_8),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
        LocalDateTime endTime = LocalDateTime.parse(
                URLDecoder.decode(end, StandardCharsets.UTF_8),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );

        List<EndpointHit> endpointHits;
        if (unique) {
            if (uris != null) {
                endpointHits = statRepository.findAllUniqueByUri(startTime, endTime, uris);
            } else {
                endpointHits = statRepository.findAllUnique(startTime, endTime);
            }
        } else {
            if (uris != null) {
                endpointHits = statRepository.findAllNoUniqueByUri(startTime, endTime, uris);
            } else {
                endpointHits = statRepository.findAllNoUnique(startTime, endTime);
            }
        }
        log.info("Received statistics on visits");
        return endpointHits.stream()
                .map(Mapper::toViewStats)
                .collect(Collectors.toList());
    }
}
