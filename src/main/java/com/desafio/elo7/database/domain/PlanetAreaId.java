package com.desafio.elo7.database.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Embeddable
@Data
public class PlanetAreaId {
    @ManyToOne
    @JoinColumn(name = "planet", referencedColumnName = "id", nullable = false)
    private PlanetData planetData;
    @Column(name = "position_x")
    private Integer positionX;
    @Column(name = "position_y")
    private Integer positionY;
}
