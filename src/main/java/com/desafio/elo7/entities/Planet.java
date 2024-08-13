package com.desafio.elo7.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Planet {
    private Long id;
    private String name;
    private List<SpaceProbe> probes;
}
