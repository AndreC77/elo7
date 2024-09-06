package com.desafio.elo7.usecases;

import com.desafio.elo7.controller.dto.PlanetRequest;
import com.desafio.elo7.controller.dto.PlanetResponse;
import com.desafio.elo7.database.PlanetDatabase;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.exception.PlanetException;
import com.desafio.elo7.utils.Converters;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PlanetUseCaseImpl implements PlanetUseCase {

    final PlanetDatabase planetDatabase;

    @Override
    public PlanetResponse createPlanet(final PlanetRequest planet) throws PlanetException {
        log.info("[PlanetUseCase] === Creating new planet ===");
        if(planet.getMaxX() < 1 || planet.getMaxY() < 1){
            throw new PlanetException("Invalid Planet Area");
        }

        PlanetData planetData = planetDatabase.save(Converters.convertToPlanetData(planet));

        log.info("[PlanetUseCase] === Plannet created successfully ===");
        return Converters.convertToPlanet(planetData);
    }

    @Override
    public PlanetResponse findPlanetById(final Long id) throws PlanetException {
        log.info("[PlanetUseCase] === Searching Planet by ID {} ===", id);
        Optional<PlanetData> planetData =  planetDatabase.findById(id);
        if(planetData.isPresent()){
            log.info("[PlanetUseCase] === Planet successfully found ===");
            return Converters.convertToPlanet(planetData.get());
        }
       throw new PlanetException("Planet does not exist");
    }

    @Override
    public List<PlanetResponse> findAllPlanets(){
        log.info("[PlanetUseCase] === Searching all registered planets ===");
        List<PlanetData> listPlanet = planetDatabase.findAll();
        return listPlanet.stream().map(Converters::convertToPlanet).toList();
    }
}
