package com.desafio.elo7.usecases;

import com.desafio.elo7.database.PlanetRepository;
import com.desafio.elo7.database.SpaceProbeRepository;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.database.domain.SpaceProbeData;
import com.desafio.elo7.entities.MovementCommands;
import com.desafio.elo7.entities.SpaceProbe;
import com.desafio.elo7.entities.enums.Direction;
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
public class MoveProbeUseCase {

    private final List<Character> validCommands = List.of('M', 'L','R');
    private final SpaceProbeRepository spaceProbeRepository;
    private final PlanetRepository planetRepository;

    public SpaceProbe execute(MovementCommands movementCommands, Long SpaceProbeId) throws CommandsException, SpaceProbeException, PlanetException {
        char[] commands = movementCommands.getCommands().toUpperCase().toCharArray();
        validateCommands(commands);
        Optional<SpaceProbeData> spaceProbeData = spaceProbeRepository.findById(SpaceProbeId);
        if (spaceProbeData.isEmpty()) throw new SpaceProbeException("Space probe does not exist");
        Optional<PlanetData> planetData = planetRepository.findById(spaceProbeData.get().getPlanetData().getId());
        if (planetData.isEmpty()) throw new PlanetException("Planet does not exist");
        planetData.get().getProbes().remove(spaceProbeData.get());

        for (char command : commands) {
            if (command == validCommands.get(0)) {
                move(spaceProbeData.get(), planetData.get());
            }
            if (command == validCommands.get(1)) {
                turnLeft(spaceProbeData.get());
            }
            if (command == validCommands.get(2)) {
                turnRight(spaceProbeData.get());
            }

        }

        validateProbeLanding(spaceProbeData.get(), planetData.get());
        spaceProbeRepository.save(spaceProbeData.get());
        return Converters.convertToSpaceProbe(spaceProbeData.get());
    }

    private void turnLeft(SpaceProbeData spaceProbeData) {
            if (spaceProbeData.getDirection() == Direction.NORTH) {
                spaceProbeData.setDirection(Direction.WEST);
            } else if (spaceProbeData.getDirection() == Direction.WEST) {
                spaceProbeData.setDirection(Direction.SOUTH);
            } else if (spaceProbeData.getDirection() == Direction.SOUTH) {
                spaceProbeData.setDirection(Direction.EAST);
            } else if (spaceProbeData.getDirection() == Direction.EAST) {
                spaceProbeData.setDirection(Direction.NORTH);
            }
        }

    private void turnRight(SpaceProbeData spaceProbeData) {
        if (spaceProbeData.getDirection() == Direction.NORTH) {
            spaceProbeData.setDirection(Direction.EAST);
        } else if (spaceProbeData.getDirection() == Direction.EAST) {
            spaceProbeData.setDirection(Direction.SOUTH);
        } else if (spaceProbeData.getDirection() == Direction.SOUTH) {
            spaceProbeData.setDirection(Direction.WEST);
        } else if (spaceProbeData.getDirection() == Direction.WEST) {
            spaceProbeData.setDirection(Direction.NORTH);
        }
    }

    private void move(SpaceProbeData spaceProbeData, PlanetData planetData){
        if (spaceProbeData.getDirection() == Direction.NORTH) {
            int positionY = spaceProbeData.getPositionY() + 1;
            positionY = adjustPositionY(positionY, planetData);
            spaceProbeData.setPositionY(positionY);
        } else if (spaceProbeData.getDirection() == Direction.SOUTH) {
            int positionY = spaceProbeData.getPositionY() - 1;
            positionY = adjustPositionY(positionY, planetData);
            spaceProbeData.setPositionY(positionY);
        } else if (spaceProbeData.getDirection() == Direction.EAST) {
            int positionX = spaceProbeData.getPositionX() + 1;
            positionX = adjustPositionX(positionX, planetData);
            spaceProbeData.setPositionX(positionX);
        } else if (spaceProbeData.getDirection() == Direction.WEST) {
            int positionX = spaceProbeData.getPositionX() - 1;
            positionX = adjustPositionX(positionX, planetData);
            spaceProbeData.setPositionX(positionX);
        }
    }

    private int adjustPositionY(int positionY, PlanetData planetData){
        if(positionY > planetData.getMaxY()) positionY = 1;
        if(positionY < 1) positionY = planetData.getMaxY();
        return positionY;
    }

    private int adjustPositionX(int positionX, PlanetData planetData){
        if(positionX > planetData.getMaxX()) positionX = 1;
        if(positionX < 1) positionX = planetData.getMaxY();
        return positionX;
    }

    private void validateCommands(char[] commands) throws CommandsException {
        for(char command : commands){
            if(!validCommands.contains(command)){
                throw new CommandsException("Command " + command + " is invalid");
            }
        }
    }

    private void validateProbeLanding(SpaceProbeData spaceProbeData, PlanetData planetData) throws PlanetException {
        for(SpaceProbeData probe : planetData.getProbes()){
            if(spaceProbeData.getPositionX() == probe.getPositionX()
                    && spaceProbeData.getPositionY() == probe.getPositionY()){
                throw new PlanetException("Landing area blocked");
            }
        }
    }
}
