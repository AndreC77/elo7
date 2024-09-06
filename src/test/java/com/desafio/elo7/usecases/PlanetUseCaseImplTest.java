package com.desafio.elo7.usecases;

import com.desafio.elo7.controller.dto.PlanetRequest;
import com.desafio.elo7.controller.dto.PlanetResponse;
import com.desafio.elo7.database.PlanetDatabase;
import com.desafio.elo7.database.domain.PlanetData;
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
public class PlanetUseCaseImplTest {

    @InjectMocks
    PlanetUseCaseImpl planetUseCaseImpl;

    @Mock
    PlanetDatabase planetDatabase;

    @Test
    void shouldCreatePlanetSuccessfully() throws PlanetException {
        when(planetDatabase.save(any())).thenReturn(planetDataBuild());
        PlanetResponse planet = planetUseCaseImpl.createPlanet(new PlanetRequest("Netuno",5, 5));

        verify(planetDatabase, times(1)).save(any());
        assertEquals("Netuno", planet.getName());
        assertEquals(5, planet.getMaxX());
        assertEquals(5, planet.getMaxY());
    }

    @Test
    void shouldThrowInvalidPlanetArea() {
        assertThatExceptionOfType(PlanetException.class).isThrownBy(() ->  planetUseCaseImpl.createPlanet(new PlanetRequest("Netuno",0, 0)));
        verify(planetDatabase, times(0)).save(any());

    }

    @Test
    void shouldReturnPlanetByIdSuccessfully() throws PlanetException {
        when(planetDatabase.findById(any())).thenReturn(Optional.ofNullable(planetDataBuild()));
        PlanetResponse planet = planetUseCaseImpl.findPlanetById(1l);

        verify(planetDatabase, times(1)).findById(any());
        assertEquals("Netuno", planet.getName());
        assertEquals(5, planet.getMaxX());
        assertEquals(5, planet.getMaxY());
    }

    @Test
    void shouldThrowPlanetDoesNotExist(){
        when(planetDatabase.findById(any())).thenReturn(Optional.empty());
        assertThatExceptionOfType(PlanetException.class).isThrownBy(() ->  planetUseCaseImpl.findPlanetById(1l));
        verify(planetDatabase, times(1)).findById(any());
    }

    @Test
    void shouldReturnAPlanetList() {
        when(planetDatabase.findAll()).thenReturn(List.of(planetDataBuild()));
        List<PlanetResponse> planets = planetUseCaseImpl.findAllPlanets();

        verify(planetDatabase, times(1)).findAll();
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
