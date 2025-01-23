package ru.vsu.cs.bordyugova_l_n.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vsu.cs.bordyugova_l_n.database.entities.Procedure;
import ru.vsu.cs.bordyugova_l_n.database.repositories.ProcedureRepository;
import ru.vsu.cs.bordyugova_l_n.exceptions.InvalidDataException;

import java.util.List;

@Service
public class ProcedureService {
    private final ProcedureRepository repository;

    public ProcedureService(ProcedureRepository repository) {
        this.repository = repository;
    }

    public List<Procedure> getAllProcedures() {
        return repository.findAll();
    }

    public Procedure getProcedureById(Integer id) {
        return repository.findById(Long.valueOf(id)).orElseThrow(() -> new InvalidDataException("Procedure not found with ID: " + id));
    }

    public void addProcedure(Procedure procedure) {
        repository.save(procedure);
    }

    public void updateProcedure(Integer id, Procedure updatedProcedure) {
        Procedure existingProcedure = getProcedureById(id);
        existingProcedure.update(updatedProcedure);
        repository.save(existingProcedure);
    }

    public void deleteProcedure(Integer id) {
        if (!repository.existsById(Long.valueOf(id))) {
            throw new InvalidDataException("Procedure not found with ID: " + id);
        }
        repository.deleteById(Long.valueOf(id));
    }

    public Page<Procedure> searchProcedures(String keyword, Pageable pageable) {
        return repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable);
    }

    public Page<Procedure> getProcedures(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Procedure> getProceduresOrderByName(){
        return repository.findAllByOrderByNameAsc();
    }
}
