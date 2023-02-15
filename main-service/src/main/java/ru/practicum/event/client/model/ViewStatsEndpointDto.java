package ru.practicum.event.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewStatsEndpointDto {
    private String app;
    private String uri;
    private Integer hits;
}
