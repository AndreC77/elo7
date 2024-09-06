package com.desafio.elo7.database;

import com.desafio.elo7.database.domain.PlanetData;

import java.util.List;
import java.util.Optional;

public interface PlanetDatabase {
    PlanetData save(final PlanetData planet);
    Optional<PlanetData> findById(final Long id);
    List<PlanetData> findAll();
}
