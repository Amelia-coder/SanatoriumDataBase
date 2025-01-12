package ru.vsu.cs.bordyugova_l_n.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.entities.Client;
import ru.vsu.cs.bordyugova_l_n.services.ClientService;

@Controller
@RequestMapping("/")
public class WebController {
    private final ClientService clientService;

    public WebController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/database";
    }

    @GetMapping("/database")
    public String database(Model model) {
        return "database";
    }

    @GetMapping("/database/clients")
    public String getClientsPage(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {
        Page<Client> clientsPage = clientService.getClients(PageRequest.of(page, size));
        model.addAttribute("clientsPage", clientsPage);
        return "fragments/clientTable :: clientRows";
    }
}
