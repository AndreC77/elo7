package com.desafio.elo7.database;


import com.desafio.elo7.database.domain.PlanetData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<PlanetData, Long> {
}
