package com.desafio.elo7.usecases;

import com.desafio.elo7.controller.dto.SpaceProbeRequest;
import com.desafio.elo7.controller.dto.SpaceProbeResponse;
import com.desafio.elo7.database.PlanetDatabase;
import com.desafio.elo7.database.SpaceProbeDatabase;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.database.domain.SpaceProbeData;
import com.desafio.elo7.entities.enums.Direction;
import com.desafio.elo7.exception.PlanetException;
import com.desafio.elo7.exception.SpaceProbeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpaceProbeUseCaseImplTest {

    @InjectMocks
    SpaceProbeUseCaseImpl spaceProbeUseCaseImpl;

    @Mock
    SpaceProbeDatabase spaceProbeDatabase;

    @Mock
    PlanetDatabase planetDatabase;

    @Test
    void shouldCreatePlanetSuccessfuly() throws PlanetException, SpaceProbeException {
        when(planetDatabase.findById(any())).thenReturn(Optional.ofNullable(planetDataBuild()));
        when(spaceProbeDatabase.save(any())).thenReturn(spaceProbeDataBuild());

        SpaceProbeResponse spaceProbe = spaceProbeUseCaseImpl.createSpaceProbe(SpaceProbeRequest.builder()
                        .name("RTX45")
                        .positionX(1)
                        .positionY(2)
                        .direction(Direction.NORTH)
                .build(), 1l);

        verify(spaceProbeDatabase, times(1)).save(any());
        verify(planetDatabase, times(1)).findById(any());
        assertEquals(1l, spaceProbe.getId());
        assertEquals("RTX45", spaceProbe.getName());
        assertEquals(1, spaceProbe.getPositionX());
        assertEquals(2, spaceProbe.getPositionY());
        assertEquals(Direction.NORTH, spaceProbe.getDirection());

    }

    @Test
    void shouldThrowInvalidLandingArea() {
        when(planetDatabase.findById(any())).thenReturn(Optional.ofNullable(planetDataBuild()));

        assertThatExceptionOfType(SpaceProbeException.class).isThrownBy(() ->  spaceProbeUseCaseImpl.createSpaceProbe(SpaceProbeRequest.builder()
                .name("RTX45")
                .positionX(6)
                .positionY(8)
                .direction(Direction.NORTH)
                .build(), 1l));
        verify(spaceProbeDatabase, times(0)).save(any());
        verify(planetDatabase, times(1)).findById(any());
    }

    @Test
    void shouldThrowPlanetDoesNotExist() {
        when(planetDatabase.findById(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(PlanetException.class).isThrownBy(() ->  spaceProbeUseCaseImpl.createSpaceProbe(SpaceProbeRequest.builder()
                .name("RTX45")
                .positionX(1)
                .positionY(2)
                .direction(Direction.NORTH)
                .build(), 1l));
        verify(spaceProbeDatabase, times(0)).save(any());
        verify(planetDatabase, times(1)).findById(any());
    }

    @Test
    void shouldThrowLandingAreaBlocked() {
        PlanetData planet = planetDataBuild();
        planet.setProbes(List.of(spaceProbeDataBuild()));
        planetDataBuild().setProbes(List.of(spaceProbeDataBuild()));
        when(planetDatabase.findById(any())).thenReturn(Optional.ofNullable(planet));

        assertThatExceptionOfType(PlanetException.class).isThrownBy(() ->  spaceProbeUseCaseImpl.createSpaceProbe(SpaceProbeRequest.builder()
                .name("RTX45")
                .positionX(1)
                .positionY(2)
                .direction(Direction.NORTH)
                .build(), 1l));
        verify(spaceProbeDatabase, times(0)).save(any());
        verify(planetDatabase, times(1)).findById(any());
    }

    @Test
    void shouldReturnAProbeList(){
        when(spaceProbeDatabase.findAll()).thenReturn(List.of(spaceProbeDataBuild()));
        List<SpaceProbeResponse> probes = spaceProbeUseCaseImpl.findAllSpacesProbe();

        verify(spaceProbeDatabase, times(1)).findAll();
        assertFalse(probes.isEmpty());
        assertEquals(1l, probes.get(0).getId());
        assertEquals("RTX45", probes.get(0).getName());
        assertEquals(1, probes.get(0).getPositionX());
        assertEquals(2, probes.get(0).getPositionY());
        assertEquals(Direction.NORTH, probes.get(0).getDirection());
    }

    @Test
    void shouldReturnProbeByIdSuccessfully() throws SpaceProbeException {
        when(spaceProbeDatabase.findById(any())).thenReturn(Optional.ofNullable(spaceProbeDataBuild()));
        SpaceProbeResponse spaceProbe = spaceProbeUseCaseImpl.findSpaceProbeById(1l);

        verify(spaceProbeDatabase, times(1)).findById(any());
        assertEquals(1l, spaceProbe.getId());
        assertEquals("RTX45", spaceProbe.getName());
        assertEquals(1, spaceProbe.getPositionX());
        assertEquals(2, spaceProbe.getPositionY());
        assertEquals(Direction.NORTH, spaceProbe.getDirection());
    }

    @Test
    void shouldThrowProbeDoesNotExist(){
        when(spaceProbeDatabase.findById(any())).thenReturn(Optional.empty());
        assertThatExceptionOfType(SpaceProbeException.class).isThrownBy(() ->  spaceProbeUseCaseImpl.findSpaceProbeById(1l));
        verify(spaceProbeDatabase, times(1)).findById(any());
    }



    private PlanetData planetDataBuild(){
        return PlanetData.builder()
                .id(1l)
                .name("Netuno")
                .maxY(5)
                .maxX(5)
                .probes(new ArrayList<>())
                .build();
    }

    private SpaceProbeData spaceProbeDataBuild(){
        return SpaceProbeData.builder()
                .id(1l)
                .name("RTX45")
                .positionX(1)
                .positionY(2)
                .direction(Direction.NORTH)
                .planetData(planetDataBuild())
                .build();
    }
}
