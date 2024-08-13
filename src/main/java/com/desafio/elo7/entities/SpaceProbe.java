package com.desafio.elo7.entities;

import com.desafio.elo7.entities.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceProbe {

    private Long id;
    private String name;
    private String positionX;
    private String positionY;
    private Direction direction;
    private Planet planet;
}
