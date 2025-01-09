package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ClientID", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "RoomNumber", nullable = false)
    private Room room;

    @Column(name = "AssignmentID")
    private Integer assignmentId;

    @Column(name = "Doctor")
    private String doctor;

    @Column(name = "CheckInDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private java.util.Date checkInDate;

    @Column(name = "CheckOutDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private java.util.Date checkOutDate;

    public void update(Ticket ticket) {
        this.client = ticket.client;
        this.room = ticket.room;
        this.assignmentId = ticket.assignmentId;
        this.doctor = ticket.doctor;
        this.checkInDate = ticket.checkInDate;
        this.checkOutDate = ticket.checkOutDate;
    }

}

