package ru.vsu.cs.bordyugova_l_n.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.DTO.ClientDTO;
import ru.vsu.cs.bordyugova_l_n.database.entities.Client;
import ru.vsu.cs.bordyugova_l_n.database.entities.Room;
import ru.vsu.cs.bordyugova_l_n.services.ClientService;
import ru.vsu.cs.bordyugova_l_n.services.RoomService;

@Controller
@RequestMapping("/clients")
public class ClientWebController {
    private final ClientService clientService;
    private final RoomService roomService;

    public ClientWebController(ClientService clientService, RoomService roomService) {
        this.clientService = clientService;
        this.roomService = roomService;
    }

    @GetMapping
    public String listClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "clients";
    }

//    @GetMapping("/clients")
//    public Page<Client> getClientsPage(@RequestParam(defaultValue = "0") int page,
//                                       @RequestParam(defaultValue = "10") int size) {
//        return clientService.getClients(PageRequest.of(page, size));
//    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "add-client";
    }

    @PostMapping
    public String addClient(@RequestBody ClientDTO clientDTO) {
        Client client = new Client();
        client.setFirstName(clientDTO.getFirstName());
        client.setMiddleName(clientDTO.getMiddleName());
        client.setLastName(clientDTO.getLastName());
        client.setPhone(clientDTO.getPhone());
        client.setEmail(clientDTO.getEmail());
        client.setResortCard(clientDTO.getResortCard());

        // Fetch the Room entity by ID
        if (clientDTO.getRoom() != null) {
            Room room = roomService.getRoomById(Long.valueOf(clientDTO.getRoom()));
            client.setRoom(room);
        }

        clientService.addClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Client client = clientService.getClientById(id);
//        ClientDTO clientDTO = new ClientDTO(client); // Map entity to DTO
        model.addAttribute("client", client);
        return "edit-client";
    }

    @PostMapping("/update")
    public String updateClient(@RequestBody ClientDTO clientDTO) {
        Client updatedClient = new Client();
        updatedClient.setId((clientDTO.getId()));
        updatedClient.setFirstName(clientDTO.getFirstName());
        updatedClient.setMiddleName(clientDTO.getMiddleName());
        updatedClient.setLastName(clientDTO.getLastName());
        updatedClient.setPhone(clientDTO.getPhone());
        updatedClient.setEmail(clientDTO.getEmail());
        updatedClient.setResortCard(clientDTO.getResortCard());
        if (clientDTO.getRoom() != null) {
            Room room = roomService.getRoomById(Long.valueOf(clientDTO.getRoom()));
            updatedClient.setRoom(room);
        }
        clientService.updateClient(clientDTO.getId(), updatedClient);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }



}
