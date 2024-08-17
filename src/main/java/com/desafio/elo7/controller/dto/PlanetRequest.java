package com.desafio.elo7.controller.dto;

import com.desafio.elo7.exception.handler.ResponseError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlanetRequest {

    @NotBlank(message = "Planet name field cannot be empty or null")
    private String name;

    @NotNull(message = "Planet Area cannot be null")
    private Integer maxX;

    @NotNull(message = "Planet Area cannot be null")
    private Integer maxY;
}
