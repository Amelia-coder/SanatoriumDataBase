package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "procedure")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "procedure")
    private List<Assignment> assignments;

    public void update(Procedure procedure) {
        if (procedure.name != null) this.name = procedure.name;
        if (procedure.description != null) this.description = procedure.description;
    }
}

