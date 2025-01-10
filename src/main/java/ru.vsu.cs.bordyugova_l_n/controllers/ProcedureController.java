package ru.vsu.cs.bordyugova_l_n.controllers;

import org.springframework.web.bind.annotation.*;

import ru.vsu.cs.bordyugova_l_n.database.entities.Procedure;
import ru.vsu.cs.bordyugova_l_n.services.ProcedureService;


import java.util.List;


@RestController
@RequestMapping("/api/procedure")
public class ProcedureController {
    private final ProcedureService service;

    public ProcedureController(ProcedureService service) {
        this.service = service;
    }

    @GetMapping
    public List<Procedure> getAllProcedures() {
        return service.getAllProcedures();
    }

    @GetMapping("/{id}")
    public Procedure getProcedureById(@PathVariable Integer id) {
        return service.getProcedureById(id);
    }

    @PostMapping
    public void addProcedure(@RequestBody Procedure procedure) {
        service.addProcedure(procedure);
    }

    @PutMapping("/{id}")
    public void updateProcedure(@PathVariable Integer id, @RequestBody Procedure updatedProcedure) {
        service.updateProcedure(id, updatedProcedure);
    }

    @DeleteMapping("/{id}")
    public void deleteProcedure(@PathVariable Integer id) {
        service.deleteProcedure(id);
    }
}
