package ru.vsu.cs.bordyugova_l_n.database.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.bordyugova_l_n.database.entities.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    Page<Assignment> getAssignmentsByTicket_Id(Pageable pageable, Integer ticketId);
}
