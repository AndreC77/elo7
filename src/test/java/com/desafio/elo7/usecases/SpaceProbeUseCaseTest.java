package com.desafio.elo7.usecases;

import com.desafio.elo7.database.PlanetRepository;
import com.desafio.elo7.database.SpaceProbeRepository;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.database.domain.SpaceProbeData;
import com.desafio.elo7.entities.SpaceProbe;
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
public class SpaceProbeUseCaseTest {

    @InjectMocks
    SpaceProbeUseCase spaceProbeUseCase;

    @Mock
    SpaceProbeRepository spaceProbeRepository;

    @Mock
    PlanetRepository planetRepository;

    @Test
    void shouldCreatePlanetSuccessfuly() throws PlanetException, SpaceProbeException {
        when(planetRepository.findById(any())).thenReturn(Optional.ofNullable(planetDataBuild()));
        when(spaceProbeRepository.save(any())).thenReturn(spaceProbeDataBuild());

        SpaceProbe spaceProbe = spaceProbeUseCase.createSpaceProbe(SpaceProbe.builder()
                        .name("RTX45")
                        .positionX(1)
                        .positionY(2)
                        .direction(Direction.NORTH)
                .build(), 1l);

        verify(spaceProbeRepository, times(1)).save(any());
        verify(planetRepository, times(1)).findById(any());
        assertEquals(1l, spaceProbe.getId());
        assertEquals("RTX45", spaceProbe.getName());
        assertEquals(1, spaceProbe.getPositionX());
        assertEquals(2, spaceProbe.getPositionY());
        assertEquals(Direction.NORTH, spaceProbe.getDirection());

    }

    @Test
    void shouldThrowInvalidLandingArea() {
        when(planetRepository.findById(any())).thenReturn(Optional.ofNullable(planetDataBuild()));

        assertThatExceptionOfType(SpaceProbeException.class).isThrownBy(() ->  spaceProbeUseCase.createSpaceProbe(SpaceProbe.builder()
                .name("RTX45")
                .positionX(6)
                .positionY(8)
                .direction(Direction.NORTH)
                .build(), 1l));
        verify(spaceProbeRepository, times(0)).save(any());
        verify(planetRepository, times(1)).findById(any());
    }

    @Test
    void shouldThrowPlanetDoesNotExist() {
        when(planetRepository.findById(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(PlanetException.class).isThrownBy(() ->  spaceProbeUseCase.createSpaceProbe(SpaceProbe.builder()
                .name("RTX45")
                .positionX(1)
                .positionY(2)
                .direction(Direction.NORTH)
                .build(), 1l));
        verify(spaceProbeRepository, times(0)).save(any());
        verify(planetRepository, times(1)).findById(any());
    }

    @Test
    void shouldThrowLandingAreaBlocked() {
        PlanetData planet = planetDataBuild();
        planet.setProbes(List.of(spaceProbeDataBuild()));
        planetDataBuild().setProbes(List.of(spaceProbeDataBuild()));
        when(planetRepository.findById(any())).thenReturn(Optional.ofNullable(planet));

        assertThatExceptionOfType(PlanetException.class).isThrownBy(() ->  spaceProbeUseCase.createSpaceProbe(SpaceProbe.builder()
                .name("RTX45")
                .positionX(1)
                .positionY(2)
                .direction(Direction.NORTH)
                .build(), 1l));
        verify(spaceProbeRepository, times(0)).save(any());
        verify(planetRepository, times(1)).findById(any());
    }

    @Test
    void shouldReturnAProbeList(){
        when(spaceProbeRepository.findAll()).thenReturn(List.of(spaceProbeDataBuild()));
        List<SpaceProbe> probes = spaceProbeUseCase.findAllSpacesProbe();

        verify(spaceProbeRepository, times(1)).findAll();
        assertFalse(probes.isEmpty());
        assertEquals(1l, probes.get(0).getId());
        assertEquals("RTX45", probes.get(0).getName());
        assertEquals(1, probes.get(0).getPositionX());
        assertEquals(2, probes.get(0).getPositionY());
        assertEquals(Direction.NORTH, probes.get(0).getDirection());
    }

    @Test
    void shouldReturnProbeByIdSuccessfully() throws SpaceProbeException {
        when(spaceProbeRepository.findById(any())).thenReturn(Optional.ofNullable(spaceProbeDataBuild()));
        SpaceProbe spaceProbe = spaceProbeUseCase.findById(1l);

        verify(spaceProbeRepository, times(1)).findById(any());
        assertEquals(1l, spaceProbe.getId());
        assertEquals("RTX45", spaceProbe.getName());
        assertEquals(1, spaceProbe.getPositionX());
        assertEquals(2, spaceProbe.getPositionY());
        assertEquals(Direction.NORTH, spaceProbe.getDirection());
    }

    @Test
    void shouldThrowProbeDoesNotExist(){
        when(spaceProbeRepository.findById(any())).thenReturn(Optional.empty());
        assertThatExceptionOfType(SpaceProbeException.class).isThrownBy(() ->  spaceProbeUseCase.findById(1l));
        verify(spaceProbeRepository, times(1)).findById(any());
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
