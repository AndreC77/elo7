package com.desafio.elo7.usecases;

import com.desafio.elo7.database.PlanetRepository;
import com.desafio.elo7.database.SpaceProbeRepository;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.database.domain.SpaceProbeData;
import com.desafio.elo7.entities.Planet;
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


    public SpaceProbe createSpaceProbe(final SpaceProbe spaceProbe, final Long planetId) throws PlanetException {
        PlanetData planet = getPlanet(planetId);
        SpaceProbeData spaceProbeData = Converters.convertToSpaceProbeData(spaceProbe);
        spaceProbeData.setPlanetData(planet);
        return Converters.convertToSpaceProbe(spaceProbeRepository.save(spaceProbeData));
    }


    public SpaceProbe update(final SpaceProbe spaceProbe){
        SpaceProbeData spaceProbeData = spaceProbeRepository.save(Converters.convertToSpaceProbeData(spaceProbe));
        return Converters.convertToSpaceProbe(spaceProbeData);
    }

    public SpaceProbe findById(Long id) throws SpaceProbeException {
        Optional<SpaceProbeData> spaceProbeData = spaceProbeRepository.findById(id);
        if (spaceProbeData.isPresent()){
            return Converters.convertToSpaceProbe(spaceProbeData.get());
        }
        throw new SpaceProbeException("Space probe does not exist");
    }

    public List<SpaceProbe> findAllSpacesProbe(){
        List<SpaceProbeData> spacesProbe = spaceProbeRepository.findAll();
        return spacesProbe.stream().map(Converters::convertToSpaceProbe).toList();
    }

    private PlanetData getPlanet(Long planetId) throws PlanetException {
        Optional<PlanetData> planetData = planetRepository.findById(planetId);
        if(planetData.isPresent()){
            return planetData.get();
        }
        throw new PlanetException("Planet does not exist");
    }
}
