package ru.vsu.cs.bordyugova_l_n.database.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.bordyugova_l_n.database.entities.Procedure;

import java.util.List;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
    Page<Procedure> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);
    List<Procedure> findAllByOrderByNameAsc();
}
