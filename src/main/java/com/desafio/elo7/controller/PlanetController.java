package com.desafio.elo7.controller;

import com.desafio.elo7.controller.dto.PlanetRequest;
import com.desafio.elo7.controller.dto.PlanetResponse;
import com.desafio.elo7.exception.PlanetException;
import com.desafio.elo7.usecases.PlanetUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/planet")
public class PlanetController {

    private final PlanetUseCase planetUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlanetResponse> createPlanet(@RequestBody @Valid PlanetRequest planetRequest) throws PlanetException {
        PlanetResponse response = planetUseCase.createPlanet(planetRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{planetId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PlanetResponse> getPlanetById(@PathVariable Long planetId) throws PlanetException {
        PlanetResponse response = planetUseCase.findPlanetById(planetId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PlanetResponse>> getPlanets() {
        List<PlanetResponse> planets = planetUseCase.findAllPlanets();
        return ResponseEntity.status(HttpStatus.OK).body(planets);
    }
}
