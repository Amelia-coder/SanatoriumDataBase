package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "middleName")
    private String middleName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "roomnumber")
    private Integer roomNumber;

    @Column(name = "resortcard")
    private String resortCard;

    public void update(Client client) {
        this.firstName = client.firstName;
        this.lastName = client.lastName;
        this.middleName = client.middleName;
        this.phone = client.phone;
        this.email = client.email;
        this.roomNumber = client.roomNumber;
        this.resortCard = client.resortCard;
    }
}
