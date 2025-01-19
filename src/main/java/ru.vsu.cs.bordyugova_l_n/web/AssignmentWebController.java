package ru.vsu.cs.bordyugova_l_n.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.DTO.AssignmentDTO;
import ru.vsu.cs.bordyugova_l_n.database.entities.Assignment;
import ru.vsu.cs.bordyugova_l_n.database.entities.Office;
import ru.vsu.cs.bordyugova_l_n.database.entities.Procedure;
import ru.vsu.cs.bordyugova_l_n.database.entities.Staff;
import ru.vsu.cs.bordyugova_l_n.database.entities.Ticket;
import ru.vsu.cs.bordyugova_l_n.services.AssignmentService;
import ru.vsu.cs.bordyugova_l_n.services.OfficeService;
import ru.vsu.cs.bordyugova_l_n.services.ProcedureService;
import ru.vsu.cs.bordyugova_l_n.services.StaffService;
import ru.vsu.cs.bordyugova_l_n.services.TicketService;


@Controller
@RequestMapping("/assignments")
public class AssignmentWebController {
    private final TicketService ticketService;
    private final AssignmentService assignmentService;
    private final ProcedureService procedureService;
    private final StaffService staffService;
    private final OfficeService officeService;

    public AssignmentWebController(TicketService ticketService, AssignmentService assignmentService, ProcedureService procedureService, StaffService staffService, OfficeService officeService) {
        this.ticketService = ticketService;
        this.assignmentService = assignmentService;
        this.procedureService = procedureService;
        this.staffService = staffService;
        this.officeService = officeService;
    }


    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("assignment", new Assignment());
        return "add-assignment";
    }

    @PostMapping
    public String addClient(@RequestBody AssignmentDTO assignmentDTO) {
        Assignment assignment = new Assignment();
        Ticket ticket = new Ticket();

        if(assignmentDTO.getTicket() != null) {
           ticket = ticketService.getTicketById(assignmentDTO.getTicket());
            assignment.setTicket(ticket);
        }

        if(assignmentDTO.getProcedure() != null) {
            Procedure procedure = procedureService.getProcedureById(assignmentDTO.getProcedure());
            assignment.setProcedure(procedure);
        }

        if(assignmentDTO.getStaff() != null) {
            Staff staff = staffService.getStaffById(Long.valueOf(assignmentDTO.getStaff()));
            assignment.setStaff(staff);
        }

        if(assignmentDTO.getOffice() != null) {
            Office office = officeService.getOfficeById(Long.valueOf(assignmentDTO.getOffice()));
            assignment.setOffice(office);
        }

        assignment.setStartTime(assignmentDTO.getStartTime());

        if((assignmentDTO.getDate().compareTo(ticket.getCheckInDate()) >= 0) && (assignmentDTO.getDate().compareTo(ticket.getCheckOutDate()) <= 0)) {
            assignment.setDate(assignmentDTO.getDate());
        } else {
            throw new RuntimeException("Date is not valid");
        }



        assignmentService.addAssignment(assignment);
        return "redirect:/database";
    }
}
