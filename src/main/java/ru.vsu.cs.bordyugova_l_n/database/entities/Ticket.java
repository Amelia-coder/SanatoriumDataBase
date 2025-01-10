package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "clientid", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "roomnumber", nullable = false)
    private Room room;

    @Column(name = "doctor")
    private String doctor;

    @Column(name = "assignmentid")
    private Integer assignmentId;

    @Temporal(TemporalType.DATE)
    @Column(name = "checkindate", nullable = false)
    private java.util.Date checkInDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "checkoutdate", nullable = false)
    private java.util.Date checkOutDate;

    public void update(Ticket ticket) {
        if (ticket.client != null) this.client = ticket.client;
        if (ticket.room != null) this.room = ticket.room;
        if (ticket.doctor != null) this.doctor = ticket.doctor;
        if (ticket.assignmentId != null) this.assignmentId = ticket.assignmentId;
        if (ticket.checkInDate != null) this.checkInDate = ticket.checkInDate;
        if (ticket.checkOutDate != null) this.checkOutDate = ticket.checkOutDate;
    }
}



