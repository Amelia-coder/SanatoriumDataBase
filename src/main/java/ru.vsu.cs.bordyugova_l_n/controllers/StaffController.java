package ru.vsu.cs.bordyugova_l_n.controllers;

import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.entities.Staff;
import ru.vsu.cs.bordyugova_l_n.services.StaffService;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final StaffService service;

    public StaffController(StaffService service) {
        this.service = service;
    }

    @GetMapping
    public List<Staff> getAllStaff() {
        return service.getAllStaff();
    }

    @GetMapping("/{id}")
    public Staff getStaffById(@PathVariable Integer id) {
        return service.getStaffById(Long.valueOf(id));
    }

    @PostMapping
    public void addStaff(@RequestBody Staff staff) {
        service.addStaff(staff);
    }

    @PutMapping("/{id}")
    public void updateStaff(@PathVariable Integer id, @RequestBody Staff updatedStaff) {
        service.updateStaff(Long.valueOf(id), updatedStaff);
    }

    @DeleteMapping("/{id}")
    public void deleteStaff(@PathVariable Integer id) {
        service.deleteStaff(Long.valueOf(id));
    }
}
