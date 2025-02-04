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
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "procedure_id")
    private Procedure procedure;

    @Temporal(TemporalType.TIME)
    @Column(name = "start_time")
    private java.util.Date startTime;


    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private java.util.Date date;


//    @Column(name = "duration", nullable = false)
//    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;


    public void update(Assignment assignment) {
        if (assignment.ticket != null) this.ticket = assignment.ticket;
        if (assignment.procedure != null) this.procedure = assignment.procedure;
        if (assignment.startTime != null) this.startTime = assignment.startTime;
//        if (assignment.duration != null) this.duration = assignment.duration;
        if (assignment.staff != null) this.staff = assignment.staff;
        if (assignment.office != null) this.office = assignment.office;
    }
}

