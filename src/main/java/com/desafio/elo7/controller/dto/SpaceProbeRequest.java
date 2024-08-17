package com.desafio.elo7.controller.dto;

import com.desafio.elo7.entities.enums.Direction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpaceProbeRequest {
    @NotBlank(message = "Probe name field cannot be empty or null")
    private String name;
    @NotNull(message = "Position X of the space probe cannot be null")
    private Integer positionX;

    @NotNull(message = "Position Y of the space probe cannot be null")
    private Integer positionY;

    @NotNull(message = "Space probe direction cannot be null")
    private Direction direction;
    @NotNull(message = "Id do Planeta cannot be null")
    private Long planetId;
}
