package com.desafio.elo7.controller.dto;

import com.desafio.elo7.entities.enums.Direction;
import lombok.Builder;

@Builder
public class SpaceProbeResponse {
    private Long id;
    private String name;
    private Integer positionX;
    private Integer positionY;
    private Direction direction;
    private Long planet;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPositionX() {
        return positionX;
    }


    public Integer getPositionY() {
        return positionY;
    }

    public Direction getDirection() {
        return direction;
    }
}
