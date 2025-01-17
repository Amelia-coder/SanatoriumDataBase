package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


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
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "ticket")
    private List<Assignment> assignments;

//    @ManyToOne
//    @JoinColumn(name = "roomnumber", nullable = false)
//    private Room room;

//    @ManyToOne
//    @JoinColumn(name = "doctor", nullable = false)
//    private Staff doctor;

//    @Column(name = "assignment_id")
//    private Integer assignmentId;

    @Temporal(TemporalType.DATE)
    @Column(name = "check_in_date", nullable = false)
    private java.util.Date checkInDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "check_out_date", nullable = false)
    private java.util.Date checkOutDate;



    public void update(Ticket ticket) {
        if (ticket.client != null) this.client = ticket.client;
//        if (ticket.room != null) this.room = ticket.room;
//        if (ticket.doctor != null) this.doctor = ticket.doctor;
//        if (ticket.assignmentId != null) this.assignmentId = ticket.assignmentId;
        if (ticket.checkInDate != null) this.checkInDate = ticket.checkInDate;
        if (ticket.checkOutDate != null) this.checkOutDate = ticket.checkOutDate;
    }
}



