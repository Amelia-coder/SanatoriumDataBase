package ru.vsu.cs.bordyugova_l_n.services;

import org.springframework.stereotype.Service;
import ru.vsu.cs.bordyugova_l_n.database.entities.Client;
import ru.vsu.cs.bordyugova_l_n.database.repositories.InterfacesRepository.ClientRepository;
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


    public Client getClientById(Long id) {
        return repository.findById(id).orElseThrow(() -> new InvalidDataException("Client not found with ID: " + id));
    }

    public void addClient(Client client) {
        repository.save(client);
    }

    public void updateClient(Long id, Client updatedClient) {
        Client existingClient = getClientById(id);
        existingClient.update(updatedClient);
        repository.save(existingClient);
    }

    public void deleteClient(Long id) {
        if (!repository.existsById(id)) {
            throw new InvalidDataException("Client not found with ID: " + id);
        }
        repository.deleteById(id);
    }

}
