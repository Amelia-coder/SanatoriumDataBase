package ru.vsu.cs.bordyugova_l_n.database.repositories.InterfacesRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.bordyugova_l_n.database.entities.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
}
