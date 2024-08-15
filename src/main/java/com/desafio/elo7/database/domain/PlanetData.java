package com.desafio.elo7.database.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "planet")
public class PlanetData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "max_x", nullable = false)
    private Integer maxX;
    @Column(name = "max_y", nullable = false)
    private Integer maxY;
    @OneToMany(mappedBy = "planetData")
    private List<SpaceProbeData> probes;

}
