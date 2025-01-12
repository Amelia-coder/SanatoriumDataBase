package ru.vsu.cs.bordyugova_l_n.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vsu.cs.bordyugova_l_n.database.entities.Room;
import ru.vsu.cs.bordyugova_l_n.database.repositories.RoomRepository;
import ru.vsu.cs.bordyugova_l_n.exceptions.InvalidDataException;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> getAllRooms() {
        return repository.findAll();
    }

    public Room getRoomById(Long id) {
        return repository.findById(id).orElseThrow(() -> new InvalidDataException("Room not found with ID: " + id));
    }

    public void addRoom(Room room) {
        repository.save(room);
    }

    public void updateRoom(Long id, Room updatedRoom) {
        Room existingRoom = getRoomById(id);
        existingRoom.update(updatedRoom);
        repository.save(existingRoom);
    }

    public void deleteRoom(Long id) {
        if (!repository.existsById(id)) {
            throw new InvalidDataException("Room not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    public Page<Room> getRooms (Pageable pageable) {
        return repository.findAll(pageable);
    }
}

