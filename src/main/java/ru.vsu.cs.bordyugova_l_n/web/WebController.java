package ru.vsu.cs.bordyugova_l_n.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.entities.Client;
import ru.vsu.cs.bordyugova_l_n.database.entities.Room;
import ru.vsu.cs.bordyugova_l_n.services.ClientService;
import ru.vsu.cs.bordyugova_l_n.services.RoomService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Controller
@RequestMapping("/")
public class WebController {
    private final ClientService clientService;
    private final RoomService roomService;

    public WebController(ClientService clientService, RoomService roomService) {
        this.clientService = clientService;
        this.roomService = roomService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/database";
    }

    @GetMapping("/database")
    public String database(Model model) {
        return "database";
    }

    @GetMapping("/database/{table}")
    @ResponseBody
    public Map<String, Object> getTablePage(@PathVariable String table,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "") String search) {

//        PageRequest pageRequest = PageRequest.of(page, size);

        switch (table.toLowerCase()) {
            case "clients": {
                Page<Client> clientsPage = search.isEmpty()
                        ? clientService.getClients(PageRequest.of(page, size))
                        : clientService.searchClients(search, PageRequest.of(page, size));

                return generateResponse(clientsPage, this::generateClientRowsHtml);
            }
            case "rooms": {
                Page<Room> roomsPage = search.isEmpty()
                        ? roomService.getRooms(PageRequest.of(page, size))
                        : roomService.searchRooms(search, PageRequest.of(page, size));

                return generateResponse(roomsPage, this::generateRoomRowsHtml);
            }
            default:
                throw new IllegalArgumentException("Unknown table: " + table);
        }

    }


    private <T> Map<String, Object> generateResponse(Page<T> page, Function<Page<T>, String> htmlGenerator) {
        Map<String, Object> response = new HashMap<>();
        response.put("contentHtml", htmlGenerator.apply(page));
        response.put("number", page.getNumber());
        response.put("totalPages", page.getTotalPages());
        response.put("first", page.isFirst());
        response.put("last", page.isLast());
        return response;
    }

    private String generateClientRowsHtml(Page<Client> tablePage) {
        StringBuilder sb = new StringBuilder();
        for (Client client : tablePage.getContent()) {
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

    private String generateRoomRowsHtml(Page<Room> tablePage) {
        StringBuilder sb = new StringBuilder();
        for (Room room : tablePage.getContent()) {
            sb.append("<tr>")
                    .append("<td>").append(room.getNumber()).append("</td>")
                    .append("<td>").append(room.getBuilding()).append("</td>")
                    .append("<td>").append(room.getFloor()).append("</td>")
                    .append("<td>")
                    .append("<button class='btn btn-warning btn-sm'>Edit</button>")
                    .append("<button class='btn btn-danger btn-sm'>Delete</button>")
                    .append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }



//
//    @GetMapping("/database/clients/search")
//    @ResponseBody
//    public List<Map<String, Object>> searchClients(@RequestParam String query) {
//        List<Client> clients = clientService.searchClients(query);
//        return clients.stream().map(client -> {
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", client.getId());
//            map.put("firstName", client.getFirstName());
//            map.put("lastName", client.getLastName());
//            return map;
//        }).toList();
//    }

}
