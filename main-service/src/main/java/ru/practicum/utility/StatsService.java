package ru.practicum.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.client.StatClient;
import ru.practicum.event.client.model.EndpointHitInputDto;
import ru.practicum.event.client.model.ViewStatsEndpointDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.utility.TimePattern.TIME_PATTERN;


@Service
@RequiredArgsConstructor
public class StatsService {
    private static final String APP_NAME = "main-service";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private final StatClient client;

    public Long getViews(String uri) {
        String start = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC).format(formatter);
        String end = LocalDateTime.now().plusYears(1000).format(formatter);
        List<ViewStatsEndpointDto> listStats = client.getStats(start, end, List.of(new String[]{uri}), false);
        if (listStats != null && listStats.size() > 0) {
            listStats = listStats.stream()
                    .filter(x -> APP_NAME.equals(x.getApp()))
                    .collect(Collectors.toList());
            return listStats.size() > 0 ? listStats.get(0).getHits() : 0L;
        } else {
            return 0L;
        }
    }

    public void setHits(String uri, String ip) {
        EndpointHitInputDto endpointHit = new EndpointHitInputDto(APP_NAME, uri, ip, LocalDateTime.now());
        client.addToStat(endpointHit);
    }
}
