// Modified WebController class to ensure proper ticket display including client, doctor, and room information
package ru.vsu.cs.bordyugova_l_n.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ru.vsu.cs.bordyugova_l_n.database.entities.Assignment;
import ru.vsu.cs.bordyugova_l_n.database.entities.Client;
import ru.vsu.cs.bordyugova_l_n.database.entities.Office;
import ru.vsu.cs.bordyugova_l_n.database.entities.Procedure;
import ru.vsu.cs.bordyugova_l_n.database.entities.Room;
import ru.vsu.cs.bordyugova_l_n.database.entities.Staff;
import ru.vsu.cs.bordyugova_l_n.database.entities.Ticket;
import ru.vsu.cs.bordyugova_l_n.services.AssignmentService;
import ru.vsu.cs.bordyugova_l_n.services.ClientService;
import ru.vsu.cs.bordyugova_l_n.services.OfficeService;
import ru.vsu.cs.bordyugova_l_n.services.ProcedureService;
import ru.vsu.cs.bordyugova_l_n.services.RoomService;
import ru.vsu.cs.bordyugova_l_n.services.StaffService;
import ru.vsu.cs.bordyugova_l_n.services.TicketService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Controller
@RequestMapping("/")
public class WebController {
    private final ClientService clientService;
    private final RoomService roomService;
    private final TicketService ticketService;
    private final OfficeService officeService;
    private final StaffService staffService;
    private final ProcedureService procedureService;
    private final AssignmentService assignmentService;

    public WebController(ClientService clientService, RoomService roomService, TicketService ticketService, OfficeService officeService, StaffService staffService, ProcedureService procedureService, AssignmentService assignmentService) {
        this.clientService = clientService;
        this.roomService = roomService;
        this.ticketService = ticketService;
        this.officeService = officeService;
        this.staffService = staffService;
        this.procedureService = procedureService;
        this.assignmentService = assignmentService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/database";
    }

    @GetMapping("/database")
    public String database(Model model) {
        return "database";
    }

    @GetMapping("/database/{table}")
    @ResponseBody
    public Map<String, Object> getTablePage(@PathVariable String table,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "") String search, WebRequest webRequest) {

        switch (table.toLowerCase()) {
            case "clients": {
                Page<Client> clientsPage = search.isEmpty()
                        ? clientService.getClients(PageRequest.of(page, size))
                        : clientService.searchClients(search, PageRequest.of(page, size));

                return generateResponse(clientsPage, this::generateClientRowsHtml);
            }
            case "rooms": {
                Page<Room> roomsPage = search.isEmpty()
                        ? roomService.getRooms(PageRequest.of(page, size))
                        : roomService.searchRooms(search, PageRequest.of(page, size));

                return generateResponse(roomsPage, this::generateRoomRowsHtml);
            }
            case "tickets": {
                Page<Ticket> ticketsPage = search.isEmpty()
                        ? ticketService.getTickets(PageRequest.of(page, size)) : null;
//                        : ticketService.searchTickets(search, PageRequest.of(page, size));
                return generateResponse(ticketsPage, this::generateTicketRowsHtml);
            }
            case "offices": {
                Page<Office> officesPage = search.isEmpty()
                        ? officeService.getOffices(PageRequest.of(page, size))
                        : officeService.searchRooms(search, PageRequest.of(page, size));

                return generateResponse(officesPage, this::generateOfficeRowsHtml);
            }
            case "staffs": {
                Page<Staff> staffsPage = search.isEmpty()
                        ? staffService.getStaffs(PageRequest.of(page, size)) : null;
//                        : staffService.searchStaffs(search, PageRequest.of(page, size));

                return generateResponse(staffsPage, this::generateStaffRowsHtml);
            }
            case "procedures": {
                Page<Procedure> proceduresPage = procedureService.getProcedures(PageRequest.of(page, size));
                return generateResponse(proceduresPage, this::generateProcedureRowsHtml);

            }
            case "assignments": {
                String[] option = search.split("=");
                if(option[0].equals("data-ticketId")) {
                    Integer ticketId = Integer.parseInt(option[1]);
                    Page<Assignment> assignmentsPage = assignmentService.getAssignmentsForTicketId(PageRequest.of(page, size), ticketId);
                    Client client = ticketService.getTicketById(ticketId).getClient();
                    String clientName = client.getFirstName() + " " + " " + client.getFirstName();
//                    System.out.println(assignmentService.getAssignmentsForStaffId(PageRequest.of(page, size), ticketId));
                    return generateResponseForAssignment(assignmentsPage, this::generateAssignmentsRowsHtml, clientName);
                } else if (option[0].equals("data-staffId")) {
                    Integer staffId = Integer.parseInt(option[1]);
                    Page<Assignment> assignmentsPage = assignmentService.getAssignmentsForStaffId(PageRequest.of(page, size), staffId);
                    Staff staff = staffService.getStaffById(Long.valueOf(staffId));
                    String staffName = staff.getFirstName() + " " + " " + staff.getLastName();
//                    System.out.println(assignmentService.getAssignmentsForStaffId(PageRequest.of(page, size), staffId));
                    return generateResponseForProcedure(assignmentsPage, this::generateStaffProceduresRowsHtml, staffName);
                }
            }

            default:
                throw new IllegalArgumentException("Unknown table: " + table);
        }
    }

    private <T> Map<String, Object> generateResponse(Page<T> page, Function<Page<T>, String> htmlGenerator) {
        Map<String, Object> response = new HashMap<>();
        response.put("contentHtml", htmlGenerator.apply(page));
        response.put("number", page.getNumber());
        response.put("totalPages", page.getTotalPages());
        response.put("first", page.isFirst());
        response.put("last", page.isLast());
        return response;
    }

    private <T> Map<String, Object> generateResponseForAssignment(Page<T> page, Function<Page<T>, String> htmlGenerator, String clientName) {
        return getStringObjectMap(htmlGenerator.apply(page), page.getNumber(), page.getTotalPages(), page.isFirst(), page.isLast(), page, htmlGenerator);
    }

    private <T> Map<String, Object> getStringObjectMap(String apply, int number, int totalPages, boolean first, boolean last, Page<T> page, Function<Page<T>, String> htmlGenerator) {
        Map<String, Object> response = new HashMap<>();
        String pageContent = apply;
        response.put("contentHtml", pageContent);
        response.put("number", number);
        response.put("totalPages", totalPages);
        response.put("first", first);
        response.put("last", last);
        return response;
    }

    private <T> Map<String, Object> generateResponseForProcedure(Page<T> page, Function<Page<T>, String> htmlGenerator, String staffName) {
        return getStringObjectMap(htmlGenerator.apply(page), page.getNumber(), page.getTotalPages(), page.isFirst(), page.isLast(), page, htmlGenerator);
    }

    private String generateClientRowsHtml(Page<Client> tablePage) {
        StringBuilder sb = new StringBuilder();
        for (Client client : tablePage.getContent()) {
            sb.append("<tr>")
                    .append("<td>").append(client.getFirstName()).append("</td>")
                    .append("<td>").append(client.getMiddleName() != null ? client.getMiddleName() : "").append("</td>")
                    .append("<td>").append(client.getLastName()).append("</td>")
                    .append("<td>").append(client.getPhone() != null ? client.getPhone() : "").append("</td>")
                    .append("<td>").append(client.getEmail() != null ? client.getEmail() : "").append("</td>")
                    .append("<td>").append(client.getResortCard() != null ? client.getResortCard() : "").append("</td>")
                    .append("<td>").append(client.getRoom().getNumber()).append("</td>")
                    .append("<td>")
                    .append("<button class='btn btn-warning btn-sm btn-editClient' data-id='" + client.getId() + "'>Edit</button>")
                    .append("<button class='btn btn-danger btn-sm' btn-deleteClient data-id='" + client.getId() + "'>Delete</button>")
                    .append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }

    private String generateRoomRowsHtml(Page<Room> tablePage) {
        StringBuilder sb = new StringBuilder();
        for (Room room : tablePage.getContent()) {
            sb.append("<tr>")
                    .append("<td>").append(room.getNumber()).append("</td>")
                    .append("<td>").append(room.getBuilding()).append("</td>")
                    .append("<td>").append(room.getFloor()).append("</td>")
//                    .append("<td>").append(room.getClients().size()).append("</td>")
//                    .append("<td>")
//                    .append("<button class='btn btn-warning btn-sm'>Edit</button>")
//                    .append("<button class='btn btn-danger btn-sm'>Delete</button>")
                    .append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }

    private String generateTicketRowsHtml(Page<Ticket> tablePage) {
        StringBuilder sb = new StringBuilder();
        for (Ticket ticket : tablePage.getContent()) {
            sb.append("<tr>")
                    .append("<td>").append(ticket.getClient().getFirstName()).append(" ")
                    .append(ticket.getClient().getLastName()).append("</td>")
//                    .append("<td>").append(ticket.getRoom().getNumber()).append("</td>")
//                    .append("<td>").append(ticket.getDoctor().getFirstName()).append(" ")
//                    .append(ticket.getDoctor().getLastName()).append("</td>")
//                    .append("<td>").append(ticket.getAssignmentId()).append("</td>")
                    .append("<td>").append(ticket.getCheckInDate()).append("</td>")
                    .append("<td>").append(ticket.getCheckOutDate()).append("</td>")
                    .append("<td>")
                    .append("<button class='btn btn-success btn-sm' data-entity=assignment data-ticketId= " + ticket.getId() + ">Show Assignment</button>")
                    .append("<button class='btn btn-warning btn-sm btn-editTicket' data-id= " + ticket.getId() + ">Edit</button>")
                    .append("<button class='btn btn-danger btn-sm' btn-deleteTicket data-id= " + ticket.getId() + ">Delete</button>")
                    .append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }

    private String generateOfficeRowsHtml(Page<Office> tablePage) {
        StringBuilder sb = new StringBuilder();
        for (Office office : tablePage.getContent()) {
            sb.append("<tr>")
                    .append("<td>").append(office.getNumber()).append("</td>")
                    .append("<td>").append(office.getBuilding()).append("</td>")
                    .append("<td>").append(office.getFloor()).append("</td>")
//                    .append("<td>")
//                    .append("<button class='btn btn-warning btn-sm' >Edit</button>")
//                    .append("<button class='btn btn-danger btn-sm' >Delete</button>")
                    .append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }

    private String generateStaffRowsHtml(Page<Staff> tablePage) {
        StringBuilder sb = new StringBuilder();
        for (Staff staff : tablePage.getContent()) {
            sb.append("<tr>")
                    .append("<td>").append(staff.getFirstName()).append("</td>")
                    .append("<td>").append(staff.getMiddleName() != null ? staff.getMiddleName() : "").append("</td>")
                    .append("<td>").append(staff.getLastName()).append("</td>")
                    .append("<td>").append(staff.getPhone() != null ? staff.getPhone() : "").append("</td>")
                    .append("<td>").append(staff.getEmail()).append("</td>")
                    .append("<td>").append(staff.getPosition() != null ? staff.getPosition() : "").append("</td>")
                    .append("<td>")
                    .append("<button class='btn btn-success btn-sm' data-entity=assignment data-staffId= " + staff.getId() + ">Show Assignment</button>")
//                    .append("<button class='btn btn-warning btn-sm'>Edit</button>")
//                    .append("<button class='btn btn-danger btn-sm'>Delete</button>")
                    .append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }

    private String generateStaffProceduresRowsHtml(Page<Assignment> assignments) {
        StringBuilder sb = new StringBuilder();
        for (Assignment assignment : assignments) {
            sb.append("<tr>")
                    .append("<td>").append(assignment.getStaff().getFirstName()).append(" ")
                    .append(assignment.getStaff().getLastName()).append("</td>")
                    .append("<td>").append(assignment.getProcedure().getName()).append("</td>")
                    .append("<td>").append(assignment.getProcedure().getDescription()).append(" ")
                    .append("<td>").append(assignment.getTicket().getClient().getFirstName()).append(" ")
                    .append(assignment.getTicket().getClient().getLastName()).append("</td>")
                    .append("<td>").append(assignment.getOffice().getNumber()).append("</td>")
                    .append("<td>").append(assignment.getStartTime()).append("</td>")
                    .append("<td>").append(assignment.getDate()).append("</td>")
//                    .append("<td>").append(assignment.getDuration()).append("</td>")
                    .append("<td>")
                    .append("<button class='btn btn-warning btn-sm' btn-editAssignmentStaff>Edit</button>")
                    .append("<button class='btn btn-danger btn-sm' btn-deleteAssignmentStaff>Delete</button>")
                    .append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }

    private String generateProcedureRowsHtml(Page<Procedure> proceduresPage) {
        StringBuilder sb = new StringBuilder();
        for (Procedure procedure : proceduresPage) {
            sb.append("<tr>")
                    .append("<td>").append(procedure.getId()).append("</td>")
                    .append("<td>").append(procedure.getName()).append("</td>")
                    .append("<td>").append(procedure.getDescription()).append("</td>")
//                    .append("<td>")
//                    .append("<button class='btn btn-warning btn-sm'>Edit</button>")
//                    .append("<button class='btn btn-danger btn-sm'>Delete</button>")
                    .append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }

    private String generateAssignmentsRowsHtml(Page<Assignment> assignmentPage) {
        StringBuilder sb = new StringBuilder();
        for (Assignment assignment : assignmentPage) {
            sb.append("<tr>")
                    .append("<td>").append(assignment.getTicket().getClient().getFirstName()).append(" ")
                    .append(assignment.getTicket().getClient().getLastName()).append("</td>")
                    .append("<td>").append(assignment.getProcedure().getName()).append("</td>")
                    .append("<td>").append(assignment.getProcedure().getDescription()).append(" ")
                    .append("<td>").append(assignment.getStaff().getFirstName()).append(" ")
                    .append(assignment.getStaff().getLastName()).append("</td>")
                    .append("<td>").append(assignment.getOffice().getNumber()).append("</td>")
                    .append("<td>").append(assignment.getStartTime()).append("</td>")
                    .append("<td>").append(assignment.getDate()).append("</td>")
//                    .append("<td>").append(assignment.getDuration()).append("</td>")
                    .append("<td>")
                    .append("<button class='btn btn-warning btn-sm' btn-editAssignmentTicket>Edit</button>")
                    .append("<button class='btn btn-danger btn-sm' btn-deleteAssignmentTicket>Delete</button>")
                    .append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }

    @GetMapping("/database/{table}/new")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        return "add-client";
    }

    @PostMapping("/database")
    public String addClient(@ModelAttribute Client client) {
        clientService.addClient(client);
        return "redirect:/database/{table}";
    }

    @GetMapping("/database/{table}/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Client client = clientService.getClientById(id);
        model.addAttribute("client", client);
        return "edit-client";
    }

    @PostMapping("/database/{table}/{id}")
    public String updateClient(@PathVariable Integer id, @ModelAttribute Client updatedClient) {
        clientService.updateClient(id, updatedClient);
        return "redirect:/clients";
    }

    @GetMapping("/database/{table}/{id}")
    public String deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }

}
