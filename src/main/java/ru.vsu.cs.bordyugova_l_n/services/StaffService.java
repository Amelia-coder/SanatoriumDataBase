package ru.vsu.cs.bordyugova_l_n.services;

import org.springframework.stereotype.Service;
import ru.vsu.cs.bordyugova_l_n.database.entities.Staff;
import ru.vsu.cs.bordyugova_l_n.database.repositories.InterfacesRepository.StaffRepository;
import ru.vsu.cs.bordyugova_l_n.exceptions.InvalidDataException;

import java.util.List;

@Service
public class StaffService {
    private final StaffRepository repository;

    public StaffService(StaffRepository repository) {
        this.repository = repository;
    }

    public List<Staff> getAllStaff() {
        return repository.findAll();
    }

    public Staff getStaffById(Long id) {
        return repository.findById(id).orElseThrow(() -> new InvalidDataException("Staff not found with ID: " + id));
    }

    public void addStaff(Staff staff) {
        repository.save(staff);
    }

    public void updateStaff(Long id, Staff updatedStaff) {
        Staff existingStaff = getStaffById(id);
        existingStaff.update(updatedStaff);
        repository.save(existingStaff);
    }

    public void deleteStaff(Long id) {
        if (!repository.existsById(id)) {
            throw new InvalidDataException("Staff not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}

