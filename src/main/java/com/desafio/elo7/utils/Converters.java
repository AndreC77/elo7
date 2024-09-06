package com.desafio.elo7.utils;

import com.desafio.elo7.controller.dto.PlanetRequest;
import com.desafio.elo7.controller.dto.PlanetResponse;
import com.desafio.elo7.controller.dto.SpaceProbeRequest;
import com.desafio.elo7.controller.dto.SpaceProbeResponse;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.database.domain.SpaceProbeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Converters {

    public static PlanetData convertToPlanetData(final PlanetRequest planet){
        if (Objects.isNull(planet)){
            return null;
        }
        return PlanetData.builder()
                .name(planet.getName())
                .maxX(planet.getMaxX())
                .maxY(planet.getMaxX())
                .build();
    }

    public static PlanetResponse convertToPlanet(final PlanetData planetData){
        return PlanetResponse.builder()
                .id(planetData.getId())
                .name(planetData.getName())
                .maxX(planetData.getMaxX())
                .maxY(planetData.getMaxY())
                .probes(getListProbes(planetData.getProbes()))
                .build();
    }

    public static List<SpaceProbeResponse> getListProbes(final List<SpaceProbeData> probeDataList){
        List<SpaceProbeResponse> probes = new ArrayList<>();
        if (probeDataList != null && !probeDataList.isEmpty()){
            for(SpaceProbeData probe : probeDataList){
                SpaceProbeResponse spaceProbe = SpaceProbeResponse.builder()
                        .id(probe.getId())
                        .name(probe.getName())
                        .direction(probe.getDirection())
                        .positionX(probe.getPositionX())
                        .positionY(probe.getPositionY())
                        .planet(probe.getPlanetData().getId())
                        .build();
                probes.add(spaceProbe);
            }
        }
        return probes;
    }

    public static SpaceProbeResponse convertToSpaceProbe(final SpaceProbeData spaceProbeData){
        return SpaceProbeResponse.builder()
                .id(spaceProbeData.getId())
                .name(spaceProbeData.getName())
                .positionX(spaceProbeData.getPositionX())
                .positionY(spaceProbeData.getPositionY())
                .direction(spaceProbeData.getDirection())
                .planet(spaceProbeData.getPlanetData().getId())
                .build();
    }

    public static SpaceProbeData convertToSpaceProbeData(final SpaceProbeRequest spaceProbe){
        return SpaceProbeData.builder()
                .name(spaceProbe.getName())
                .positionX(spaceProbe.getPositionX())
                .positionY(spaceProbe.getPositionY())
                .direction(spaceProbe.getDirection())
                .build();
    }

}
