package com.desafio.elo7.usecases;

import com.desafio.elo7.database.PlanetRepository;
import com.desafio.elo7.database.SpaceProbeRepository;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.database.domain.SpaceProbeData;
import com.desafio.elo7.entities.SpaceProbe;
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
public class SpaceProbeUseCase {
    final SpaceProbeRepository spaceProbeRepository;
    final PlanetRepository planetRepository;


    public SpaceProbe createSpaceProbe(final SpaceProbe spaceProbe, final Long planetId) throws PlanetException, SpaceProbeException {
        log.info("[SpaceProbeUseCase] === Landing a new space probe ===");
        PlanetData planet = getPlanet(planetId);
        SpaceProbeData spaceProbeData = Converters.convertToSpaceProbeData(spaceProbe);
        spaceProbeData.setPlanetData(planet);

        if(spaceProbe.getPositionX() > planet.getMaxY() || spaceProbe.getPositionY() > planet.getMaxY()){
            throw new SpaceProbeException("Invalid landing area");
        }
        validateProbeLanding(spaceProbeData, planet);

        log.info("[SpaceProbeUseCase] === Space probe landing successfully ===");
        return Converters.convertToSpaceProbe(spaceProbeRepository.save(spaceProbeData));
    }

    public SpaceProbe findById(final Long id) throws SpaceProbeException {
        log.info("[SpaceProbeUseCase] === Searching Space probe by ID {} ===", id);
        Optional<SpaceProbeData> spaceProbeData = spaceProbeRepository.findById(id);
        if (spaceProbeData.isPresent()){
            log.info("[SpaceProbeUseCase] === Space probe successfully found ===");
            return Converters.convertToSpaceProbe(spaceProbeData.get());
        }
        throw new SpaceProbeException("Space probe does not exist");
    }

    public void deleteById(final Long id){
        log.info("[SpaceProbeUseCase] === Deactivating space probe ID {}===", id);
        spaceProbeRepository.deleteById(id);
    }

    public List<SpaceProbe> findAllSpacesProbe(){
        log.info("[PlanetUseCase] === Searching all registered Space probes ===");
        List<SpaceProbeData> spacesProbe = spaceProbeRepository.findAll();
        return spacesProbe.stream().map(Converters::convertToSpaceProbe).toList();
    }

    private PlanetData getPlanet(final Long planetId) throws PlanetException {
        log.info("[SpaceProbeUseCase] === Searching Planet by ID {} ===", planetId);
        Optional<PlanetData> planetData = planetRepository.findById(planetId);
        if(planetData.isPresent()){
            return planetData.get();
        }
        throw new PlanetException("Planet does not exist");
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
