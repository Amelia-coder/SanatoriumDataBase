package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "middlename")
    private String middleName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "position", nullable = false)
    private String position;

    @OneToMany(mappedBy = "staff")
    private List<Assignment> assignments;

    public void update(Staff staff) {
        if (staff.firstName != null) this.firstName = staff.firstName;
        if (staff.lastName != null) this.lastName = staff.lastName;
        if (staff.middleName != null) this.middleName = staff.middleName;
        if (staff.phone != null) this.phone = staff.phone;
        if (staff.email != null) this.email = staff.email;
        if (staff.position != null) this.position = staff.position;
    }
}

