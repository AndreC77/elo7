package com.desafio.elo7.usecases;

import com.desafio.elo7.database.PlanetRepository;
import com.desafio.elo7.database.domain.PlanetData;
import com.desafio.elo7.entities.Planet;
import com.desafio.elo7.exception.PlanetException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanetUseCaseTest {

    @InjectMocks
    PlanetUseCase planetUseCase;

    @Mock
    PlanetRepository planetRepository;

    @Test
    void shouldCreatePlanetSuccessfully() throws PlanetException {
        when(planetRepository.save(any())).thenReturn(planetDataBuild());
        Planet planet = planetUseCase.createPlanet(Planet.builder()
                        .name("Netuno")
                        .maxX(5)
                        .maxY(5)
                .build());

        verify(planetRepository, times(1)).save(any());
        assertEquals("Netuno", planet.getName());
        assertEquals(5, planet.getMaxX());
        assertEquals(5, planet.getMaxY());
    }

    @Test
    void shouldThrowInvalidPlanetArea() {
        assertThatExceptionOfType(PlanetException.class).isThrownBy(() ->  planetUseCase.createPlanet(Planet.builder()
                .name("Netuno")
                .maxX(0)
                .maxY(0)
                .build()));
        verify(planetRepository, times(0)).save(any());

    }

    @Test
    void shouldReturnPlanetByIdSuccessfully() throws PlanetException {
        when(planetRepository.findById(any())).thenReturn(Optional.ofNullable(planetDataBuild()));
        Planet planet = planetUseCase.findById(1l);

        verify(planetRepository, times(1)).findById(any());
        assertEquals("Netuno", planet.getName());
        assertEquals(5, planet.getMaxX());
        assertEquals(5, planet.getMaxY());
    }

    @Test
    void shouldThrowPlanetDoesNotExist(){
        when(planetRepository.findById(any())).thenReturn(Optional.empty());
        assertThatExceptionOfType(PlanetException.class).isThrownBy(() ->  planetUseCase.findById(1l));
        verify(planetRepository, times(1)).findById(any());
    }

    @Test
    void shouldReturnAPlanetList() {
        when(planetRepository.findAll()).thenReturn(List.of(planetDataBuild()));
        List<Planet> planets = planetUseCase.findAllPlanets();

        verify(planetRepository, times(1)).findAll();
        assertFalse(planets.isEmpty());
        assertEquals("Netuno", planets.get(0).getName());
        assertEquals(5, planets.get(0).getMaxX());
        assertEquals(5, planets.get(0).getMaxY());
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
}
