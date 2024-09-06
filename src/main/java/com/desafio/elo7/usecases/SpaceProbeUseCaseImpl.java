package com.desafio.elo7.usecases;

import com.desafio.elo7.controller.dto.SpaceProbeRequest;
import com.desafio.elo7.controller.dto.SpaceProbeResponse;
import com.desafio.elo7.database.PlanetDatabase;
import com.desafio.elo7.database.SpaceProbeDatabase;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.database.domain.SpaceProbeData;
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
public class SpaceProbeUseCaseImpl implements SpaceProbeUseCase{
    final SpaceProbeDatabase spaceProbeDatabase;
    final PlanetDatabase planetDatabase;


    @Override
    public SpaceProbeResponse createSpaceProbe(final SpaceProbeRequest spaceProbe, final Long planetId) throws PlanetException, SpaceProbeException {
        log.info("[SpaceProbeUseCase] === Landing a new space probe ===");
        PlanetData planet = getPlanet(planetId);
        SpaceProbeData spaceProbeData = Converters.convertToSpaceProbeData(spaceProbe);
        spaceProbeData.setPlanetData(planet);

        if(spaceProbe.getPositionX() > planet.getMaxY() || spaceProbe.getPositionY() > planet.getMaxY()){
            throw new SpaceProbeException("Invalid landing area");
        }
        planet.validateProbeLanding(spaceProbeData);

        log.info("[SpaceProbeUseCase] === Space probe landing successfully ===");
        return Converters.convertToSpaceProbe(spaceProbeDatabase.save(spaceProbeData));
    }

    @Override
    public SpaceProbeResponse findSpaceProbeById(final Long id) throws SpaceProbeException {
        log.info("[SpaceProbeUseCase] === Searching Space probe by ID {} ===", id);
        Optional<SpaceProbeData> spaceProbeData = spaceProbeDatabase.findById(id);
        if (spaceProbeData.isPresent()){
            log.info("[SpaceProbeUseCase] === Space probe successfully found ===");
            return Converters.convertToSpaceProbe(spaceProbeData.get());
        }
        throw new SpaceProbeException("Space probe does not exist");
    }

    @Override
    public void deleteSpaceProbeById(final Long id){
        log.info("[SpaceProbeUseCase] === Deactivating space probe ID {}===", id);
        spaceProbeDatabase.deleteById(id);
    }

    @Override
    public List<SpaceProbeResponse> findAllSpacesProbe(){
        log.info("[PlanetUseCase] === Searching all registered Space probes ===");
        List<SpaceProbeData> spacesProbe = spaceProbeDatabase.findAll();
        return spacesProbe.stream().map(Converters::convertToSpaceProbe).toList();
    }

    private PlanetData getPlanet(final Long planetId) throws PlanetException {
        log.info("[SpaceProbeUseCase] === Searching Planet by ID {} ===", planetId);
        Optional<PlanetData> planetData = planetDatabase.findById(planetId);
        if(planetData.isPresent()){
            return planetData.get();
        }
        throw new PlanetException("Planet does not exist");
    }
}
