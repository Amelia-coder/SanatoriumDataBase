package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Office")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Office {

    @Id
    @Column(name = "Number")
    private Integer number;

    @Column(name = "Floor", nullable = false)
    private Integer floor;

    @Column(name = "Building", nullable = false)
    private String building;

    public void update(Office office) {
        this.floor = office.floor;
        this.building = office.building;
    }

}
