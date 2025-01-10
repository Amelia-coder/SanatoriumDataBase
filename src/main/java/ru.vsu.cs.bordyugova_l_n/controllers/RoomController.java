package ru.vsu.cs.bordyugova_l_n.controllers;

import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.bordyugova_l_n.database.entities.Room;
import ru.vsu.cs.bordyugova_l_n.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return service.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Integer id) {
        return service.getRoomById(Long.valueOf(id));
    }

    @PostMapping
    public void addRoom(@RequestBody Room room) {
        service.addRoom(room);
    }

    @PutMapping("/{id}")
    public void updateRoom(@PathVariable Integer id, @RequestBody Room updatedRoom) {
        service.updateRoom(Long.valueOf(id), updatedRoom);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Integer id) {
        service.deleteRoom(Long.valueOf(id));
    }
}

