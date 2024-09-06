package com.desafio.elo7.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlanetRequest {

    @NotBlank(message = "Planet name field cannot be empty or null")
    private String name;

    @NotNull(message = "Planet Area cannot be null")
    private Integer maxX;

    @NotNull(message = "Planet Area cannot be null")
    private Integer maxY;

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
