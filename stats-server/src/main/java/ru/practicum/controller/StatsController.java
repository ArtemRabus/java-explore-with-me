package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.EndpointDto;
import ru.practicum.model.dto.ViewStats;
import ru.practicum.service.StatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatService statService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointDto saveInfo(@RequestBody EndpointDto endpointDto) {
        log.info("Save information that there was a request to the endpoints (StatsController)");
        return statService.save(endpointDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> findStat(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam(required = false) String[] uris,
                                    @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Get statistics on visits (StatsController)");
        return statService.findStat(start, end, uris, unique);
    }
}
