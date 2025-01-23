package ru.vsu.cs.bordyugova_l_n.services;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.vsu.cs.bordyugova_l_n.database.entities.Client;
import ru.vsu.cs.bordyugova_l_n.database.repositories.ClientRepository;
import ru.vsu.cs.bordyugova_l_n.exceptions.InvalidDataException;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> getAllClients() {
        List<Client> clients = repository.findAll();
        clients.forEach(client -> System.out.println("Client ID: " + client.getId()));
        return clients;
    }


    public Client getClientById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new InvalidDataException("Client not found with ID: " + id));
    }

    public void addClient(Client client) {
        if (client.getId() != null && repository.existsById(client.getId())) {
            throw new InvalidDataException("Client with this ID already exists");
        }
        repository.save(client); // Выполняется только если ID не занят
    }


    public void updateClient(Integer id, Client updatedClient) {
        Client existingClient = getClientById(id);
        existingClient.update(updatedClient);
        repository.save(existingClient);
    }

    public void deleteClient(Integer id) {
        if (!repository.existsById(id)) {
            throw new InvalidDataException("Client not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    public Page<Client> getClients(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Client> searchClients(String query, PageRequest pageRequest) {
        return repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query, pageRequest);
    }

    public List<Client> getAllClientsSortedByName() {
        return repository.findAllByOrderByLastNameAscFirstNameAscMiddleNameAsc();
    }

}
