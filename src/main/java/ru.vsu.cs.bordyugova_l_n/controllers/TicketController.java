package ru.vsu.cs.bordyugova_l_n.controllers;

import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.entities.Ticket;
import ru.vsu.cs.bordyugova_l_n.services.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return service.getAllTickets();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Integer id) {
        return service.getTicketById(id);
    }

    @PostMapping
    public void addTicket(@RequestBody Ticket ticket) {
        service.addTicket(ticket);
    }

    @PutMapping("/{id}")
    public void updateTicket(@PathVariable Integer id, @RequestBody Ticket updatedTicket) {
        service.updateTicket((id), updatedTicket);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Integer id) {
        service.deleteTicket(id);
    }
}
