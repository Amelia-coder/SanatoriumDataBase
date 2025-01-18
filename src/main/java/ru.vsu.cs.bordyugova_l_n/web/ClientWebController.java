package ru.vsu.cs.bordyugova_l_n.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.entities.Client;
import ru.vsu.cs.bordyugova_l_n.services.ClientService;

@Controller
@RequestMapping("/clients")
public class ClientWebController {
    private final ClientService clientService;

    public ClientWebController(ClientService clientService) {
        this.clientService = clientService;
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
        return "add-client";
    }

    @PostMapping
    public String addClient(@ModelAttribute Client client) {
        clientService.addClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Client client = clientService.getClientById(id);
        model.addAttribute("client", client);
        return "edit-client";
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable Integer id, @ModelAttribute Client updatedClient) {
        clientService.updateClient(id, updatedClient);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }



}
