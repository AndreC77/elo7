package com.desafio.elo7.database;


import com.desafio.elo7.database.domain.PlanetData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PlanetDatabaseImpl implements PlanetDatabase{

    final PlanetRepository planetRepository;

    @Override
    public PlanetData save(PlanetData planet) {
        return planetRepository.save(planet);
    }

    @Override
    public Optional<PlanetData> findById(Long id) {
        return planetRepository.findById(id);
    }

    @Override
    public List<PlanetData> findAll() {
        return planetRepository.findAll();
    }
}
