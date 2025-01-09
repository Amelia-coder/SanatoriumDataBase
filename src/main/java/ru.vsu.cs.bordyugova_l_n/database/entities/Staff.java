package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "LastName", nullable = false)
    private String lastName;

    @Column(name = "MiddleName")
    private String middleName;

    @Column(name = "Phone", unique = true)
    private String phone;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Position", nullable = false)
    private String position;

    public void update(Staff staff) {
        this.firstName = staff.firstName;
        this.lastName = staff.lastName;
        this.middleName = staff.middleName;
        this.phone = staff.phone;
        this.email = staff.email;
        this.position = staff.position;
    }


}
