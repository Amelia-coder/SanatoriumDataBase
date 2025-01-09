package ru.vsu.cs.bordyugova_l_n.database.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Assignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    @Id
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "TicketID", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "ProcedureID", nullable = false)
    private Procedure procedure;

    @Column(name = "StartTime", nullable = false)
    @Temporal(TemporalType.TIME)
    private java.util.Date startTime;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "StaffID", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "OfficeNumber", nullable = false)
    private Office office;

    public void update(Assignment assignment) {
        this.ticket = assignment.ticket;
        this.procedure = assignment.procedure;
        this.startTime = assignment.startTime;
        this.quantity = assignment.quantity;
        this.staff = assignment.staff;
        this.office = assignment.office;
    }

}
