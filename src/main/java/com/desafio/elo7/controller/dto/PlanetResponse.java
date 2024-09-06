package com.desafio.elo7.controller.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class PlanetResponse {
    private Long id;
    private String name;
    private Integer maxX;
    private Integer maxY;
    private List<SpaceProbeResponse> probes;

    public String getName() {
        return name;
    }

    public Integer getMaxX() {
        return maxX;
    }

    public Integer getMaxY() {
        return maxY;
    }
}
