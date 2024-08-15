package com.desafio.elo7.controller.dto;

import com.desafio.elo7.entities.enums.Direction;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpaceProbeRequest {
    private String name;
    private Integer positionX;
    private Integer positionY;
    private Direction direction;
    private Long planetId;
}
