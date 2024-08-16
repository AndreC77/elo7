package com.desafio.elo7.usecases;

import com.desafio.elo7.database.PlanetRepository;
import com.desafio.elo7.database.SpaceProbeRepository;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.database.domain.SpaceProbeData;
import com.desafio.elo7.entities.MovementCommands;
import com.desafio.elo7.entities.SpaceProbe;
import com.desafio.elo7.entities.enums.Direction;
import com.desafio.elo7.exception.CommandsException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoveProbeUseCaseTest {

    @InjectMocks
    MoveProbeUseCase moveProbeUseCase;

    @Mock
    SpaceProbeRepository spaceProbeRepository;

    @Mock
    PlanetRepository planetRepository;

    @Test
    void shouldMoveProbesSuccessfully() throws PlanetException, CommandsException, SpaceProbeException {

        when(spaceProbeRepository.findById(any())).thenReturn(Optional.ofNullable(spaceProbeDataBuild()));
        when(planetRepository.findById(any())).thenReturn(Optional.ofNullable(planetDataBuild()));
        when(spaceProbeRepository.save(any())).thenReturn(spaceProbeDataBuild());

        SpaceProbe spaceProbe = moveProbeUseCase.execute(MovementCommands.builder()
                        .commands("MMRMMRMRRML")
                .build(), 1l);

        verify(spaceProbeRepository, times(1)).save(any());
        verify(planetRepository, times(1)).findById(any());
        verify(spaceProbeRepository, times(1)).save(any());
        assertEquals(1l, spaceProbe.getId());
        assertEquals("RTX45", spaceProbe.getName());
        assertEquals(5, spaceProbe.getPositionX());
        assertEquals(1, spaceProbe.getPositionY());
        assertEquals(Direction.NORTH, spaceProbe.getDirection());
    }

    @Test
    void shouldThrowCommandIsInvalid() {
        assertThatExceptionOfType(CommandsException.class).isThrownBy(() ->  moveProbeUseCase.execute(MovementCommands.builder()
                .commands("MMRMMHRMRRML")
                .build(), 1l));
        verify(spaceProbeRepository, times(0)).findById(any());
        verify(spaceProbeRepository, times(0)).save(any());
        verify(planetRepository, times(0)).findById(any());
    }

    @Test
    void shouldThrowPlanetDoesNotExist() {
        when(spaceProbeRepository.findById(any())).thenReturn(Optional.ofNullable(spaceProbeDataBuild()));
        when(planetRepository.findById(any())).thenReturn(Optional.empty());


        assertThatExceptionOfType(PlanetException.class).isThrownBy(() ->  moveProbeUseCase.execute(MovementCommands.builder()
                .commands("MMRMMRMRRML")
                .build(), 1l));
        verify(spaceProbeRepository, times(1)).findById(any());
        verify(spaceProbeRepository, times(0)).save(any());
        verify(planetRepository, times(1)).findById(any());
    }

    @Test
    void shouldThrowInMoveProbePlanetDoesNotExist() {
        when(spaceProbeRepository.findById(any())).thenReturn(Optional.ofNullable(spaceProbeDataBuild()));
        when(planetRepository.findById(any())).thenReturn(Optional.empty());


        assertThatExceptionOfType(PlanetException.class).isThrownBy(() ->  moveProbeUseCase.execute(MovementCommands.builder()
                .commands("MMRMMRMRRML")
                .build(), 1l));
        verify(spaceProbeRepository, times(1)).findById(any());
        verify(spaceProbeRepository, times(0)).save(any());
        verify(planetRepository, times(1)).findById(any());
    }

    @Test
    void shouldThrowInMoveProbeProbeDoesNotExist() {
        when(spaceProbeRepository.findById(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(SpaceProbeException.class).isThrownBy(() ->  moveProbeUseCase.execute(MovementCommands.builder()
                .commands("MMRMMRMRRML")
                .build(), 2l));
        verify(spaceProbeRepository, times(1)).findById(any());
        verify(spaceProbeRepository, times(0)).save(any());
        verify(planetRepository, times(0)).findById(any());
    }

    @Test
    void shouldThrowInMoveProbeLandingAreaBlocked() {

        SpaceProbeData spaceProbeData = SpaceProbeData.builder()
                .id(2l)
                .name("RTX41")
                .positionX(5)
                .positionY(1)
                .direction(Direction.NORTH)
                .planetData(planetDataBuild())
                .build();

        PlanetData planetData = planetDataBuild();
        planetData.getProbes().add(spaceProbeData);
        planetData.getProbes().add(spaceProbeDataBuild());

        when(spaceProbeRepository.findById(any())).thenReturn(Optional.ofNullable(spaceProbeDataBuild()));
        when(planetRepository.findById(any())).thenReturn(Optional.ofNullable(planetData));


        assertThatExceptionOfType(PlanetException.class).isThrownBy(() ->  moveProbeUseCase.execute(MovementCommands.builder()
                .commands("MMRMMRMRRML")
                .build(), 1l));
        verify(spaceProbeRepository, times(1)).findById(any());
        verify(spaceProbeRepository, times(0)).save(any());
        verify(planetRepository, times(1)).findById(any());
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
                .positionX(3)
                .positionY(3)
                .direction(Direction.EAST)
                .planetData(planetDataBuild())
                .build();
    }

}
