package ru.vsu.cs.bordyugova_l_n.controllers;

import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.entities.Assignment;
import ru.vsu.cs.bordyugova_l_n.services.AssignmentService;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return service.getAllAssignments();
    }

    @GetMapping("/{id}")
    public Assignment getAssignmentById(@PathVariable Integer id) {
        return service.getAssignmentById(id);
    }

    @PostMapping
    public void addAssignment(@RequestBody Assignment assignment) {
        service.addAssignment(assignment);
    }

    @PutMapping("/{id}")
    public void updateAssignment(@PathVariable Integer id, @RequestBody Assignment updatedAssignment) {
        service.updateAssignment(id, updatedAssignment);
    }

    @DeleteMapping("/{id}")
    public void deleteAssignment(@PathVariable Integer id) {
        service.deleteAssignment(id);
    }
}
