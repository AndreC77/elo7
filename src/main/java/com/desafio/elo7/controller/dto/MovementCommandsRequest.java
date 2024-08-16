package com.desafio.elo7.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovementCommandsRequest {
    private String commands;
    private Long spaceProbeId;
}
