package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "assignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ticketid", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "procedureid", nullable = false)
    private Procedure procedure;

    @Temporal(TemporalType.TIME)
    @Column(name = "starttime", nullable = false)
    private java.util.Date startTime;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "staffid", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "officenumber", nullable = false)
    private Office office;

    public void update(Assignment assignment) {
        if (assignment.ticket != null) this.ticket = assignment.ticket;
        if (assignment.procedure != null) this.procedure = assignment.procedure;
        if (assignment.startTime != null) this.startTime = assignment.startTime;
        if (assignment.quantity != null) this.quantity = assignment.quantity;
        if (assignment.staff != null) this.staff = assignment.staff;
        if (assignment.office != null) this.office = assignment.office;
    }
}

