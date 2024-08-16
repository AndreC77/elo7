package com.desafio.elo7.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError {
    private int httpStatus;
    private String message;
}
