package com.desafio.elo7.usecases;

import com.desafio.elo7.controller.dto.SpaceProbeRequest;
import com.desafio.elo7.controller.dto.SpaceProbeResponse;
import com.desafio.elo7.exception.PlanetException;
import com.desafio.elo7.exception.SpaceProbeException;

import java.util.List;

public interface SpaceProbeUseCase {
    SpaceProbeResponse createSpaceProbe(final SpaceProbeRequest spaceProbe, final Long planetId) throws PlanetException, SpaceProbeException;
    SpaceProbeResponse findSpaceProbeById(final Long id) throws SpaceProbeException;
    void deleteSpaceProbeById(final Long id);
    List<SpaceProbeResponse> findAllSpacesProbe();
}
