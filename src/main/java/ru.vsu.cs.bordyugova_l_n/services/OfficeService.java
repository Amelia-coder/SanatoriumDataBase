package ru.vsu.cs.bordyugova_l_n.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vsu.cs.bordyugova_l_n.database.entities.Office;
import ru.vsu.cs.bordyugova_l_n.database.repositories.OfficeRepository;
import ru.vsu.cs.bordyugova_l_n.exceptions.InvalidDataException;

import java.util.List;

@Service
public class OfficeService {
    private final OfficeRepository repository;

    public OfficeService(OfficeRepository repository) {
        this.repository = repository;
    }

    public List<Office> getAllOffices() {
        return repository.findAll();
    }

    public Office getOfficeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new InvalidDataException("Office not found with ID: " + id));
    }

    public void addOffice(Office office) {
        repository.save(office);
    }

    public void updateOffice(Long id, Office updatedOffice) {
        Office existingOffice = getOfficeById(id);
        existingOffice.update(updatedOffice);
        repository.save(existingOffice);
    }

    public void deleteOffice(Long id) {
        if (!repository.existsById(id)) {
            throw new InvalidDataException("Office not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    public Page<Office> getOffices(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Office> searchRooms(String query, PageRequest pageRequest) {
        return repository.findByNumberContainingIgnoreCase(query, pageRequest);
    }
}
