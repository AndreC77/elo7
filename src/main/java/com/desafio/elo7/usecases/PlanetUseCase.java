package com.desafio.elo7.usecases;

import com.desafio.elo7.controller.dto.PlanetRequest;
import com.desafio.elo7.controller.dto.PlanetResponse;
import com.desafio.elo7.exception.PlanetException;

import java.util.List;

public interface PlanetUseCase {
    PlanetResponse createPlanet(final PlanetRequest planet) throws PlanetException;
    PlanetResponse findPlanetById(final Long id) throws PlanetException;
    List<PlanetResponse> findAllPlanets();
}
