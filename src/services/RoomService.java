package services;

import models.Room;
import repositories.interfaces.IRoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomService {
    private final IRoomRepository repo;

    public RoomService(IRoomRepository repo) {
        this.repo = repo;
    }

    public String addRoom(int roomNumber, String roomType, double price) {
        if (price <= 0) {
            return "Invalid room price.";
        }
        Room room = new Room(roomNumber, roomType, price);
        return repo.createRoom(room) ? "Room was added successfully." : "Failed to add the room.";
    }

    public Optional<Room> getRoomById(int id) {
        return Optional.ofNullable(repo.getRoomById(id));
    }

    public String getAllRooms() {
        List<Room> rooms = repo.getAllRooms();
        return rooms.isEmpty()
                ? "No rooms available."
                : rooms.stream()
                .map(room -> "Room ID: " + room.getId() + ", Room Type: " + room.getRoomType() + ", Price: " + room.getPrice() + " ₸")
                .collect(Collectors.joining("\n", "Available rooms:\n", ""));
    }

    public List<String> getRoomTypes() {
        return repo.getRoomTypes();
    }

    public List<Room> getAvailableRoomsByType(String roomType) {
        return repo.getAvailableRoomsByType(roomType);
    }

    public boolean deleteAllRooms() {
        return repo.deleteAllRooms();
    }

    public boolean updateRoomDetails(int roomId, String roomType, double price) {
        return repo.updateRoomDetails(roomId, roomType, price);
    }

    public List<Room> getAvailableRoomsByTypeAndDate(String roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        return repo.getAvailableRoomsByTypeAndDate(roomType, checkInDate, checkOutDate);
    }
}

