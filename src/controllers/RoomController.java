package controllers;

import controllers.interfaces.IRoomController;
import models.Room;
import repositories.interfaces.IRoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomController implements IRoomController {
    private final IRoomRepository repo;

    public RoomController(IRoomRepository repo) {
        this.repo = repo;
    }

    @Override
    public String addRoom(int roomNumber, String roomType, double price) {
        return repo.createRoom(new Room(roomNumber, roomType, price))
                ? "Room was added successfully."
                : "Failed to add the room.";
    }

    @Override
    public String getRoomById(int id) {
        return Optional.ofNullable(repo.getRoomById(id))
                .map(Room::toString)
                .orElse("Room not found.");
    }

    @Override
    public String getAllRooms() {
        return Optional.ofNullable(repo.getAllRooms())
                .filter(rooms -> !rooms.isEmpty())
                .map(rooms -> rooms.stream()
                        .map(room -> "Room ID: " + room.getId() + ", Room Type: " + room.getRoomType() + ", Price: " + room.getPrice() + " ₸")
                        .collect(Collectors.joining("\n", "Available rooms:\n", "")))
                .orElse("No rooms available.");
    }

    @Override
    public List<String> getRoomTypes() {
        return repo.getRoomTypes();
    }

    @Override
    public List<Room> getAvailableRoomsByType(String roomType) {
        return repo.getAvailableRoomsByType(roomType);
    }

    @Override
    public String deleteAllRooms() {
        return repo.deleteAllRooms()
                ? "All rooms were deleted successfully."
                : "Failed to delete all rooms.";
    }
}
