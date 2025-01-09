package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Procedure")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Procedure {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description")
    private String description;

    public void update(Procedure procedure) {
        this.name = procedure.name;
        this.description = procedure.description;
    }

}
