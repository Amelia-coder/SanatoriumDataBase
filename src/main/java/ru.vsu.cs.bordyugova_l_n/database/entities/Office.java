package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "office")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Office {

    @Id
    @Column(name = "number")
    private Integer number;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "building", nullable = false)
    private String building;

    @OneToMany(mappedBy = "office")
    private List<Assignment> assignments;

    public void update(Office office) {
        if (office.floor != null) this.floor = office.floor;
        if (office.building != null) this.building = office.building;
    }
}

