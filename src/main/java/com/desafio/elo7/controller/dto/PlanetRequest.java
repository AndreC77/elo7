package com.desafio.elo7.controller.dto;

import lombok.Data;

@Data
public class PlanetRequest {
    private String name;
    private Integer maxX;
    private Integer maxY;
}
