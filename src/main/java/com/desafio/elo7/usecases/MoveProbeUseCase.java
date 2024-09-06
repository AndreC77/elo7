package com.desafio.elo7.usecases;

import com.desafio.elo7.controller.dto.SpaceProbeResponse;
import com.desafio.elo7.entities.MovementCommands;
import com.desafio.elo7.exception.CommandsException;
import com.desafio.elo7.exception.PlanetException;
import com.desafio.elo7.exception.SpaceProbeException;

public interface MoveProbeUseCase {
    SpaceProbeResponse execute(MovementCommands movementCommands, Long SpaceProbeId) throws CommandsException, SpaceProbeException, PlanetException;
}
