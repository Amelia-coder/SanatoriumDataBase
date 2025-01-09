package ru.vsu.cs.bordyugova_l_n.controllers;

import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.entities.Office;
import ru.vsu.cs.bordyugova_l_n.services.OfficeService;

import java.util.List;


@RestController
@RequestMapping("/api/offices")
public class OfficeController {
    private final OfficeService service;

    public OfficeController(OfficeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Office> getAllOffices() {
        return service.getAllOffices();
    }

    @GetMapping("/{id}")
    public Office getOfficeById(@PathVariable Integer id) {
        return service.getOfficeById(Long.valueOf(id));
    }

    @PostMapping
    public void addOffice(@RequestBody Office office) {
        service.addOffice(office);
    }

    @PutMapping("/{id}")
    public void updateOffice(@PathVariable Integer id, @RequestBody Office updatedOffice) {
        service.updateOffice(Long.valueOf(id), updatedOffice);
    }

    @DeleteMapping("/{id}")
    public void deleteOffice(@PathVariable Integer id) {
        service.deleteOffice(Long.valueOf(id));
    }
}
