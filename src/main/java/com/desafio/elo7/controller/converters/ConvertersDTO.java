package com.desafio.elo7.controller.converters;

import com.desafio.elo7.controller.dto.PlanetRequest;
import com.desafio.elo7.controller.dto.PlanetResponse;
import com.desafio.elo7.controller.dto.SpaceProbeRequest;
import com.desafio.elo7.controller.dto.SpaceProbeResponse;
import com.desafio.elo7.entities.Planet;
import com.desafio.elo7.entities.SpaceProbe;

import java.util.ArrayList;
import java.util.List;

public class ConvertersDTO {

    public static Planet convertToPlanet(final PlanetRequest planetRequest){
        return Planet.builder()
                .name(planetRequest.getName())
                .maxX(planetRequest.getMaxX())
                .maxY(planetRequest.getMaxY())
                .probes(new ArrayList<SpaceProbe>())
                .build();
    }

    public static PlanetResponse convertToPlanetResponse(final Planet planet){
        List<SpaceProbeResponse> spaceProbesResponse = planet.getProbes().stream().map(ConvertersDTO::convertToSpaceProbeResponse).toList();
        return PlanetResponse.builder()
                .id(planet.getId())
                .name(planet.getName())
                .maxX(planet.getMaxX())
                .maxY(planet.getMaxY())
                .probes(spaceProbesResponse)
                .build();
    }

    public static SpaceProbeResponse convertToSpaceProbeResponse(final SpaceProbe spaceProbe){
        return SpaceProbeResponse.builder()
                .id(spaceProbe.getId())
                .name(spaceProbe.getName())
                .positionX(spaceProbe.getPositionX())
                .positionY(spaceProbe.getPositionY())
                .direction(spaceProbe.getDirection())
                .planet(spaceProbe.getPlanet())
                .build();
    }

    public static SpaceProbe convertToSpaceProbeResponse(final SpaceProbeRequest spaceProbeRequest){
        return SpaceProbe.builder()
                .name(spaceProbeRequest.getName())
                .positionX(spaceProbeRequest.getPositionX())
                .positionY(spaceProbeRequest.getPositionY())
                .direction(spaceProbeRequest.getDirection())
                .build();
    }
}
