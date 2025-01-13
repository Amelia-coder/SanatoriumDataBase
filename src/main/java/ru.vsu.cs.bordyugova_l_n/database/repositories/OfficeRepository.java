package ru.vsu.cs.bordyugova_l_n.database.repositories;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.bordyugova_l_n.database.entities.Office;


@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
    Page<Office> findAll(Pageable pageable);
    Page<Office> findByNumberContainingIgnoreCase(String number, Pageable pageable);
}
