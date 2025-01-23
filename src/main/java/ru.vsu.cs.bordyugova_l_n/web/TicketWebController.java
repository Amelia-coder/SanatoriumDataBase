package ru.vsu.cs.bordyugova_l_n.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.DTO.ClientDTO;
import ru.vsu.cs.bordyugova_l_n.database.DTO.TicketDTO;
import ru.vsu.cs.bordyugova_l_n.database.entities.Client;
import ru.vsu.cs.bordyugova_l_n.database.entities.Ticket;
import ru.vsu.cs.bordyugova_l_n.services.ClientService;
import ru.vsu.cs.bordyugova_l_n.services.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketWebController {
    private final TicketService ticketService;
    private final ClientService clientService;

    public TicketWebController(ClientService clientService, TicketService ticketService) {
        this.clientService = clientService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public String listTickets(Model model) {
        model.addAttribute("tickets", clientService.getAllClients());
        return "tickets";
    }

//    @GetMapping("/clients")
//    public Page<Client> getClientsPage(@RequestParam(defaultValue = "0") int page,
//                                       @RequestParam(defaultValue = "10") int size) {
//        return clientService.getClients(PageRequest.of(page, size));
//    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("clients", clientService.getAllClients());
        return "add-ticket";
    }

    @PostMapping
    public String addTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setCheckInDate(ticketDTO.getCheckInDate());
        ticket.setCheckOutDate(ticketDTO.getCheckOutDate());

        if (ticketDTO.getClient() != null) {
            Client client = clientService.getClientById((ticketDTO.getClient()));
            ticket.setClient(client);
        }

        ticketService.addTicket(ticket);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        return "edit-ticket";
    }

    @PostMapping("/update/{id}")
    public String updateTicket(@PathVariable Integer id, @ModelAttribute Ticket updatedTicket) {
        ticketService.updateTicket(id, updatedTicket);
        return "redirect:/tickets";
    }

    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
        return "redirect:/tickets";
    }

}
