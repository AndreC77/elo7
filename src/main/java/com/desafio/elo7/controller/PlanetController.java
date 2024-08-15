package com.desafio.elo7.controller;

import com.desafio.elo7.controller.converters.ConvertersDTO;
import com.desafio.elo7.controller.dto.PlanetRequest;
import com.desafio.elo7.controller.dto.PlanetResponse;
import com.desafio.elo7.entities.Planet;
import com.desafio.elo7.exception.PlanetException;
import com.desafio.elo7.usecases.PlanetUseCase;
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
    public ResponseEntity<PlanetResponse> createPlanet(@RequestBody PlanetRequest planetRequest){
        Planet response = planetUseCase.createPlanet(ConvertersDTO.convertToPlanet(planetRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(ConvertersDTO.convertToPlanetResponse(response));
    }

    @GetMapping(path = "/{planetId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PlanetResponse> getPlanetById(@PathVariable Long planetId) throws PlanetException {
        Planet response = planetUseCase.findById(planetId);
        return ResponseEntity.status(HttpStatus.OK).body(ConvertersDTO.convertToPlanetResponse(response));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PlanetResponse>> getPlanets() {
        List<Planet> planets = planetUseCase.findAllPlanets();
        List<PlanetResponse> response = planets.stream().map(ConvertersDTO::convertToPlanetResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
