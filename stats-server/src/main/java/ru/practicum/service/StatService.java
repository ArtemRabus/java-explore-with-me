package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.dto.EndpointDto;
import ru.practicum.model.dto.ViewStats;
import ru.practicum.repository.StatRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.model.dto.Mapper.toEndpointDto;
import static ru.practicum.model.dto.Mapper.toEndpointHit;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StatService {
    final StatRepository statRepository;

    public EndpointDto save(EndpointDto endpointDto) {
        EndpointDto result = toEndpointDto(statRepository.save(toEndpointHit(endpointDto)));
        log.info("Information about the endpoint request is saved");
        return result;
    }

    public List<ViewStats> findStat(String start, String end, String[] uris, Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(
                URLDecoder.decode(start, StandardCharsets.UTF_8),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
        LocalDateTime endTime = LocalDateTime.parse(
                URLDecoder.decode(end, StandardCharsets.UTF_8),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );

        List<EndpointHit> endpointHits = statRepository.findAllByTimestampBetweenAndUriIn(startTime, endTime, uris);

        List<ViewStats> viewStats = new ArrayList<>();
        for (EndpointHit hit : endpointHits) {
            Long hitCount;
            if (unique) {
                hitCount = statRepository.findHitCountByUriWithUniqueIp(hit.getUri());
            } else {
                hitCount = statRepository.findHitCountByUri(hit.getUri());
            }
            viewStats.add(ViewStats.builder()
                    .app(hit.getApp())
                    .uri(hit.getUri())
                    .hits(hitCount).build());
        }
        Set<String> uriSet = new HashSet<>();
        List<ViewStats> viewStatsUnique = viewStats.stream()
                .filter(e -> uriSet.add(e.getUri()))
                .sorted(Comparator.comparing(ViewStats::getHits).reversed())
                .collect(Collectors.toList());
        log.info("Received statistics on visits");
        return viewStatsUnique;
    }
}
