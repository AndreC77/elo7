package com.desafio.elo7.usecases;

import com.desafio.elo7.controller.dto.SpaceProbeResponse;
import com.desafio.elo7.database.PlanetDatabase;
import com.desafio.elo7.database.SpaceProbeDatabase;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.database.domain.SpaceProbeData;
import com.desafio.elo7.entities.MovementCommands;
import com.desafio.elo7.exception.CommandsException;
import com.desafio.elo7.exception.PlanetException;
import com.desafio.elo7.exception.SpaceProbeException;
import com.desafio.elo7.utils.Converters;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MoveProbeUseCaseImpl implements MoveProbeUseCase{

    private final List<Character> validCommands = List.of('M', 'L','R');
    private final SpaceProbeDatabase probeDatabase;
    private final PlanetDatabase planetDatabase;

    @Override
    public SpaceProbeResponse execute(MovementCommands movementCommands, Long SpaceProbeId) throws CommandsException, SpaceProbeException, PlanetException {
        char[] commands = movementCommands.getCommands().toUpperCase().toCharArray();
        validateCommands(commands);
        log.info("[MoveProbeUseCase] === Moving the space probe ===");

        log.info("[MoveProbeUseCase] === Searching Space probe by ID {} ===", SpaceProbeId);
        Optional<SpaceProbeData> spaceProbeData = probeDatabase.findById(SpaceProbeId);
        if (spaceProbeData.isEmpty()) throw new SpaceProbeException("Space probe does not exist");

        log.info("[PlanetUseCase] === Searching Planet by ID {} ===", spaceProbeData.get().getPlanetData().getId());
        Optional<PlanetData> planetData = planetDatabase.findById(spaceProbeData.get().getPlanetData().getId());
        if (planetData.isEmpty()) throw new PlanetException("Planet does not exist");
        planetData.get().getProbes().remove(spaceProbeData.get());

        for (char command : commands) {
            if (command == validCommands.get(0)) {
                spaceProbeData.get().move(planetData.get());
            }
            if (command == validCommands.get(1)) {
                spaceProbeData.get().turnLeft();
            }
            if (command == validCommands.get(2)) {
                spaceProbeData.get().turnRight();
            }
        }

        planetData.get().validateProbeLanding(spaceProbeData.get());
        probeDatabase.save(spaceProbeData.get());
        log.info("[MoveProbeUseCase] === Space probe movement carried out successfully ===");
        return Converters.convertToSpaceProbe(spaceProbeData.get());
    }

    private void validateCommands(char[] commands) throws CommandsException {
        for(char command : commands){
            if(!validCommands.contains(command)){
                throw new CommandsException("Command " + command + " is invalid");
            }
        }
    }
}
