package ru.vsu.cs.bordyugova_l_n.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.entities.Client;
import ru.vsu.cs.bordyugova_l_n.services.ClientService;

import java.util.List;


@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return service.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Integer id) {
        return service.getClientById((id));
    }

    @PostMapping
    public void addClient(@RequestBody Client client) {
        service.addClient(client);
    }

    @PutMapping("/{id}")
    public void updateClient(@PathVariable Integer id, @RequestBody Client updatedClient) {
        service.updateClient((id), updatedClient);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Integer id) {
        service.deleteClient(id);
    }



}
