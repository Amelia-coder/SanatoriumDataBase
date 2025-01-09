package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @Column(name = "Number")
    private Integer number;

    @Column(name = "Floor", nullable = false)
    private Integer floor;

    @Column(name = "Building", nullable = false)
    private String building;

    @Column(name = "BedCount", nullable = false)
    private Integer bedCount;

    public void update(Room room) {
        this.floor = room.floor;
        this.building = room.building;
        this.bedCount = room.bedCount;
    }
}
