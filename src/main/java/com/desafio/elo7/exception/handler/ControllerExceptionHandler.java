package com.desafio.elo7.exception.handler;

import com.desafio.elo7.exception.CommandsException;
import com.desafio.elo7.exception.PlanetException;
import com.desafio.elo7.exception.SpaceProbeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(PlanetException.class)
    public ResponseEntity<Object> handlerPlanetException(PlanetException ex,
                                                                 HttpServletRequest request){
        ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(SpaceProbeException.class)
    public ResponseEntity<Object> handlerSpaceProbeException(SpaceProbeException ex,
                                                                 HttpServletRequest request){
        ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(CommandsException.class)
    public ResponseEntity<Object> handlerCommandsException(CommandsException ex,
                                                                 HttpServletRequest request){
        ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
