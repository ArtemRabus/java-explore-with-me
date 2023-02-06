package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;
import ru.practicum.service.StatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    final StatService statService;

    @PostMapping("/hit")
    public ResponseEntity<HttpStatus> saveInfo(@RequestBody EndpointHit endpointHit) {
        statService.save(endpointHit);
        log.info("Save information that there was a request to the endpoints (StatsController)");
        return ResponseEntity.ok().build();
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
