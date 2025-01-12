package ru.vsu.cs.bordyugova_l_n.database.repositories;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.bordyugova_l_n.database.entities.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Page<Staff> findAll(Pageable pageable);
}