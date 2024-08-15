package com.desafio.elo7.database.domain;

import com.desafio.elo7.entities.enums.Direction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "space_probe")
public class SpaceProbeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "direction", nullable = false)
    @Enumerated(EnumType.STRING)
    private Direction direction;
    @Column(name = "position_x", nullable = false)
    private Integer positionX;
    @Column(name = "position_y", nullable = false)
    private Integer positionY;
    @ManyToOne
    @JoinColumn(name="planet_id", nullable=false)
    private PlanetData planetData;

//    @OneToOne
//    private PlanetAreaData planetAreaData;

}
