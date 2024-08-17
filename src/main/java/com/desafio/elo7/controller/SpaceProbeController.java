package com.desafio.elo7.controller;

import com.desafio.elo7.controller.converters.ConvertersDTO;
import com.desafio.elo7.controller.dto.MovementCommandsRequest;
import com.desafio.elo7.controller.dto.SpaceProbeRequest;
import com.desafio.elo7.controller.dto.SpaceProbeResponse;
import com.desafio.elo7.entities.MovementCommands;
import com.desafio.elo7.entities.SpaceProbe;
import com.desafio.elo7.exception.CommandsException;
import com.desafio.elo7.exception.PlanetException;
import com.desafio.elo7.exception.SpaceProbeException;
import com.desafio.elo7.usecases.MoveProbeUseCase;
import com.desafio.elo7.usecases.SpaceProbeUseCase;
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
@RequestMapping("/api/v1/probe")
public class SpaceProbeController {

    private final SpaceProbeUseCase spaceProbeUseCase;
    private final MoveProbeUseCase moveProbeUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SpaceProbeResponse> landSpaceProbe(@RequestBody  @Valid SpaceProbeRequest spaceProbeRequest) throws PlanetException, SpaceProbeException {
        SpaceProbe response = spaceProbeUseCase.createSpaceProbe(ConvertersDTO.convertToSpaceProbeResponse(spaceProbeRequest), spaceProbeRequest.getPlanetId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ConvertersDTO.convertToSpaceProbeResponse(response));
    }

    @GetMapping("/{probeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SpaceProbeResponse> getSpaceProbeById(@PathVariable Long probeId) throws SpaceProbeException {
        SpaceProbe response = spaceProbeUseCase.findById(probeId);
        return ResponseEntity.status(HttpStatus.OK).body(ConvertersDTO.convertToSpaceProbeResponse(response));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<SpaceProbeResponse>> getSpaceProbes() {
        List<SpaceProbe> probes = spaceProbeUseCase.findAllSpacesProbe();
        List<SpaceProbeResponse> response = probes.stream().map(ConvertersDTO::convertToSpaceProbeResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/move")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SpaceProbeResponse> moveSpaceProbe(@RequestBody @Valid MovementCommandsRequest movementCommandsRequest) throws CommandsException, SpaceProbeException, PlanetException {
        MovementCommands movementCommands = MovementCommands.builder()
                .commands(movementCommandsRequest.getCommands())
                .build();
        SpaceProbe response = moveProbeUseCase.execute(movementCommands, movementCommandsRequest.getSpaceProbeId());
        return ResponseEntity.status(HttpStatus.OK).body(ConvertersDTO.convertToSpaceProbeResponse(response));
    }

    @DeleteMapping("/disableProbe")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> disableProbe(@RequestParam Long spaceProbeId) throws CommandsException, SpaceProbeException, PlanetException {
        spaceProbeUseCase.deleteById(spaceProbeId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
