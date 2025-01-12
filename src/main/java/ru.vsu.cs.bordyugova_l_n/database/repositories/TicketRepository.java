package ru.vsu.cs.bordyugova_l_n.database.repositories;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.bordyugova_l_n.database.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Page<Ticket> findAll(Pageable pageable);
}
