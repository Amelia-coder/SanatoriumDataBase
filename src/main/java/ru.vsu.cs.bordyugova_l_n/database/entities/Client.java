package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматическая генерация ID
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "resort_card")
    private String resortCard;

    public void update(Client client) {
        if (client.firstName != null) this.firstName = client.firstName;
        if (client.lastName != null) this.lastName = client.lastName;
        if (client.middleName != null) this.middleName = client.middleName;
        if (client.phone != null) this.phone = client.phone;
        if (client.email != null) this.email = client.email;
        if (client.room != null) this.room = client.room;
        if (client.resortCard != null) this.resortCard = client.resortCard;
    }
}