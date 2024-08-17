package com.desafio.elo7.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovementCommandsRequest {

    @NotBlank(message = "Commands cannot be empty or null")
    private String commands;
    @NotNull(message = "Space probe id cannot be null")
    private Long spaceProbeId;
}
