package com.desafio.elo7.database.providers;

import com.desafio.elo7.database.PlanetRepository;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.entities.Planet;
import com.desafio.elo7.utils.Converters;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PlanetProvider {

    final PlanetRepository planetRepository;

    public Planet createPlanet(final Planet planet){
        PlanetData planetData = planetRepository.save(Converters.convertToPlanetData(planet));
        return Converters.convertToPlanet(planetData);
    }

    public Planet findById(final Long id){
        Optional<PlanetData> planetData =  planetRepository.findById(id);
        if(planetData.isPresent()){
            return Converters.convertToPlanet(planetData.get());
        }
        return null;
    }

    public Planet update(final Planet planet){
        PlanetData planetData = planetRepository.save(Converters.convertToPlanetData(planet));
        return Converters.convertToPlanet(planetData);
    }

}
