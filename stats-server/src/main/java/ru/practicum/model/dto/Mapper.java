package ru.practicum.model.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.model.EndpointHit;

@UtilityClass
public class Mapper {
    public static EndpointHit toEndpointHit(EndpointDto endpointDto) {
        return EndpointHit.builder()
                .app(endpointDto.getApp())
                .ip(endpointDto.getIp())
                .uri(endpointDto.getUri())
                .timestamp(endpointDto.getTimestamp())
                .build();
    }

    public static EndpointDto toEndpointDto(EndpointHit endpointHit) {
        return EndpointDto.builder()
                .id(endpointHit.getId())
                .app(endpointHit.getApp())
                .ip(endpointHit.getIp())
                .uri(endpointHit.getUri())
                .timestamp(endpointHit.getTimestamp())
                .build();
    }
}
