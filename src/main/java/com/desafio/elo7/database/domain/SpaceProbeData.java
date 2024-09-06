package com.desafio.elo7.database.domain;

import com.desafio.elo7.entities.enums.Direction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Direction getDirection() {
        return direction;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public PlanetData getPlanetData() {
        return planetData;
    }

    public void setPlanetData(PlanetData planetData) {
        this.planetData = planetData;
    }

    public void move(PlanetData planetData){
        if (this.direction == Direction.NORTH) {
            int positionY = this.positionY + 1;
            positionY = adjustPositionY(positionY, planetData);
            this.positionY= positionY;
        } else if (this.direction == Direction.SOUTH) {
            int positionY = this.positionY - 1;
            positionY = adjustPositionY(positionY, planetData);
            this.positionY= positionY;
        } else if (this.direction == Direction.EAST) {
            int positionX = this.positionX + 1;
            positionX = adjustPositionX(positionX, planetData);
            this.positionX = positionX;
        } else if (this.direction == Direction.WEST) {
            int positionX = this.positionX - 1;
            positionX = adjustPositionX(positionX, planetData);
            this.positionX = positionX;
        }
    }

    public void turnLeft() {
        if (this.direction == Direction.NORTH) {
            this.direction = Direction.WEST;
        } else if (this.direction == Direction.WEST) {
            this.direction = Direction.SOUTH;
        } else if (this.direction == Direction.SOUTH) {
            this.direction = Direction.EAST;
        } else if (this.direction == Direction.EAST) {
            this.direction = Direction.NORTH;
        }
    }

    public void turnRight() {
        if (this.direction == Direction.NORTH) {
            this.direction = Direction.EAST;
        } else if (this.direction == Direction.EAST) {
            this.direction = Direction.SOUTH;
        } else if (this.direction == Direction.SOUTH) {
            this.direction = Direction.WEST;
        } else if (this.direction == Direction.WEST) {
            this.direction = Direction.NORTH;
        }
    }

    private int adjustPositionY(int positionY, PlanetData planetData){
        if(positionY > planetData.getMaxY()) positionY = 1;
        if(positionY < 1) positionY = planetData.getMaxY();
        return positionY;
    }

    private int adjustPositionX(int positionX, PlanetData planetData){
        if(positionX > planetData.getMaxX()) positionX = 1;
        if(positionX < 1) positionX = planetData.getMaxX();
        return positionX;
    }

//    @OneToOne
//    private PlanetAreaData planetAreaData;

}
