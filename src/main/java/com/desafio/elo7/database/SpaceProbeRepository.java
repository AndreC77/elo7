package com.desafio.elo7.database;


import com.desafio.elo7.database.domain.SpaceProbeData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceProbeRepository extends JpaRepository<SpaceProbeData, Long> {
}
