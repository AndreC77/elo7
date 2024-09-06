package com.desafio.elo7.database;

import com.desafio.elo7.database.domain.SpaceProbeData;

import java.util.List;
import java.util.Optional;

public interface SpaceProbeDatabase {
    SpaceProbeData save(final SpaceProbeData spaceProbe);
    Optional<SpaceProbeData> findById(final Long id);
    void deleteById(final Long id);
    List<SpaceProbeData> findAll();
}
