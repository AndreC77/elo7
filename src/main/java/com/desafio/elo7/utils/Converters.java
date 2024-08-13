package com.desafio.elo7.utils;

import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.database.domain.SpaceProbeData;
import com.desafio.elo7.entities.Planet;
import com.desafio.elo7.entities.SpaceProbe;

import java.util.ArrayList;
import java.util.List;

public class Converters {

    public static PlanetData convertToPlanetData(final Planet planet){
        return PlanetData.builder()
                .name(planet.getName())
                .build();
    }

    public static Planet convertToPlanet(final PlanetData planetData){
        return Planet.builder()
                .id(planetData.getId())
                .name(planetData.getName())
                .probes(getListProbes(planetData.getProbes()))
                .build();
    }

    public static List<SpaceProbe> getListProbes(List<SpaceProbeData> probeDataList){
        List<SpaceProbe> probes = new ArrayList<>();
        for(SpaceProbeData probe : probeDataList){
            SpaceProbe spaceProbe = SpaceProbe.builder()
                    .id(probe.getId())
                    .name(probe.getName())
                    .direction(probe.getDirection())
                    .positionX(probe.getPositionX())
                    .positionY(probe.getPositionY())
                    .planet(convertToPlanet(probe.getPlanetData()))
                    .build();
            probes.add(spaceProbe);
        }
        return probes;
    }

    public static SpaceProbe convertToSpaceProbe(final SpaceProbeData spaceProbeData){
        return SpaceProbe.builder()
                .id(spaceProbeData.getId())
                .name(spaceProbeData.getName())
                .positionX(spaceProbeData.getPositionX())
                .positionY(spaceProbeData.getPositionY())
                .direction(spaceProbeData.getDirection())
                .planet(convertToPlanet(spaceProbeData.getPlanetData()))
                .build();
    }

    public static SpaceProbeData convertToSpaceProbeData(final SpaceProbe spaceProbe){
        return SpaceProbeData.builder()
                .name(spaceProbe.getName())
                .positionX(spaceProbe.getPositionX())
                .positionY(spaceProbe.getPositionY())
                .direction(spaceProbe.getDirection())
                .planetData(convertToPlanetData(spaceProbe.getPlanet()))
                .build();
    }

}
