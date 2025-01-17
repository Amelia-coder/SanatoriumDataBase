package ru.vsu.cs.bordyugova_l_n.database.repositories;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.bordyugova_l_n.database.entities.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
//    @Query("""
//    SELECT t FROM Ticket t
//    JOIN t.client c
//    JOIN t.doctor s
//    WHERE (:search IS NULL OR LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
//    OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
//    OR LOWER(CONCAT(s.firstName, ' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%')))
//""")
//    Page<Ticket> searchTickets(String search, Pageable pageable);
    Page<Ticket> findAll(Pageable pageable);


}
