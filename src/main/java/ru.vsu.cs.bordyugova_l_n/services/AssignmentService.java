package ru.vsu.cs.bordyugova_l_n.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vsu.cs.bordyugova_l_n.database.entities.Assignment;
import ru.vsu.cs.bordyugova_l_n.database.repositories.AssignmentRepository;
import ru.vsu.cs.bordyugova_l_n.exceptions.InvalidDataException;

import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository repository;

    public AssignmentService(AssignmentRepository repository) {
        this.repository = repository;
    }

    public List<Assignment> getAllAssignments() {
        return repository.findAll();
    }

    public Assignment getAssignmentById(Integer id) {
        return repository.findById(Long.valueOf(id)).orElseThrow(() -> new InvalidDataException("Assignment not found with ID: " + id));
    }

    public void addAssignment(Assignment assignment) {
        repository.save(assignment);
    }

    public void updateAssignment(Integer id, Assignment updatedAssignment) {
        Assignment existingAssignment = getAssignmentById(id);
        existingAssignment.update(updatedAssignment);
        repository.save(existingAssignment);
    }

    public void deleteAssignment(Integer id) {
        if (!repository.existsById(Long.valueOf(id))) {
            throw new InvalidDataException("Assignment not found with ID: " + id);
        }
        repository.deleteById(Long.valueOf(id));
    }

    public Page<Assignment> getAssignments(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
