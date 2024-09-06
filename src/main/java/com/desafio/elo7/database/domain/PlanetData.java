package com.desafio.elo7.database.domain;

import com.desafio.elo7.exception.PlanetException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public Integer getMaxX() {
        return maxX;
    }


    public Integer getMaxY() {
        return maxY;
    }

    public List<SpaceProbeData> getProbes() {
        return probes;
    }

    public void setProbes(List<SpaceProbeData> probes) {
        this.probes = probes;
    }

    public void validateProbeLanding(SpaceProbeData spaceProbeData) throws PlanetException {
        for(SpaceProbeData probe : this.probes){
            if(spaceProbeData.getPositionX() == probe.getPositionX()
                    && spaceProbeData.getPositionY() == probe.getPositionY()){
                throw new PlanetException("Landing area blocked");
            }
        }
    }

}
