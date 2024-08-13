package com.desafio.elo7.database.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "planet_area")
public class PlanetAreaData {

    @EmbeddedId
    private PlanetAreaId id;
    @OneToOne
    private SpaceProbeData spaceProbeData;
    @ManyToOne
    @JoinColumn(name = "planet_id", referencedColumnName = "id", nullable = false)
    private PlanetData planetData;
}
