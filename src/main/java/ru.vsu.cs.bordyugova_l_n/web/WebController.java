package ru.vsu.cs.bordyugova_l_n.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.entities.Client;
import ru.vsu.cs.bordyugova_l_n.services.ClientService;

import java.util.HashMap;
import java.util.Map;

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
    @ResponseBody
    public Map<String, Object> getClientsPage(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Page<Client> clientsPage = clientService.getClients(PageRequest.of(page, size));

        String contentHtml = generateClientRowsHtml(clientsPage);
        Map<String, Object> response = new HashMap<>();
        response.put("contentHtml", contentHtml);
        response.put("number", clientsPage.getNumber());
        response.put("totalPages", clientsPage.getTotalPages());
        response.put("first", clientsPage.isFirst());
        response.put("last", clientsPage.isLast());

        return response;
    }

    private String generateClientRowsHtml(Page<Client> clientsPage) {
        StringBuilder sb = new StringBuilder();
        for (Client client : clientsPage.getContent()) {
            sb.append("<tr>")
                    .append("<td>").append(client.getId()).append("</td>")
                    .append("<td>").append(client.getFirstName()).append("</td>")
                    .append("<td>").append(client.getLastName()).append("</td>")
                    .append("<td>")
                    .append("<button class='btn btn-warning btn-sm'>Edit</button>")
                    .append("<button class='btn btn-danger btn-sm'>Delete</button>")
                    .append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }

}
