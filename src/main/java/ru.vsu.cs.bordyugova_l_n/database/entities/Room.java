package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @Column(name = "number")
    private Integer number;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "building", nullable = false)
    private String building;

    @Column(name = "bed_count", nullable = false)
    private Integer bedCount;

    @OneToMany(mappedBy = "room")
    private List<Client> clients;

    public void update(Room room) {
        if (room.floor != null) this.floor = room.floor;
        if (room.building != null) this.building = room.building;
        if (room.bedCount != null) this.bedCount = room.bedCount;
    }
}

