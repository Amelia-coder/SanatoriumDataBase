package ru.vsu.cs.bordyugova_l_n.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vsu.cs.bordyugova_l_n.database.entities.Ticket;
import ru.vsu.cs.bordyugova_l_n.database.repositories.TicketRepository;
import ru.vsu.cs.bordyugova_l_n.exceptions.InvalidDataException;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return repository.findById(Math.toIntExact(id)).orElseThrow(() -> new InvalidDataException("Ticket not found with ID: " + id));
    }

    public void addTicket(Ticket ticket) {
        repository.save(ticket);
    }

    public void updateTicket(Long id, Ticket updatedTicket) {
        Ticket existingTicket = getTicketById(id);
        existingTicket.update(updatedTicket);
        repository.save(existingTicket);
    }

    public void deleteTicket(Long id) {
        if (!repository.existsById(Math.toIntExact(id))) {
            throw new InvalidDataException("Ticket not found with ID: " + id);
        }
        repository.deleteById(Math.toIntExact(id));
    }

    public Page<Ticket> getTickets(Pageable pageable) {
        return repository.findAll(pageable);
    }


    public Page<Ticket> searchTickets(String search, Pageable pageable) {
        return repository.searchTickets(search, pageable);
    }


}
