package com.desafio.elo7.controller.dto;

import com.desafio.elo7.entities.SpaceProbe;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlanetResponse {
    private Long id;
    private String name;
    private Integer maxX;
    private Integer maxY;
    private List<SpaceProbeResponse> probes;
}
