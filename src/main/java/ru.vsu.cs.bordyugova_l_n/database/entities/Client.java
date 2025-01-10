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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    @JoinColumn(name = "roomnumber")
    private Room room;

    @Column(name = "resortcard")
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

//    public Long getId() {
//        return id;
//    }
//
//
//    public String getResortCard() {
//        return resortCard;
//    }
//
//    public Room getRoom() {
//        return room;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public String getMiddleName() {
//        return middleName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public void setMiddleName(String middleName) {
//        this.middleName = middleName;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setResortCard(String resortCard) {
//        this.resortCard = resortCard;
//    }
//
//    public void setRoom(Room room) {
//        this.room = room;
//    }
}