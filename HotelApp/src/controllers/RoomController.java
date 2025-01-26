package controllers;

import controllers.interfaces.IRoomController;
import models.Room;
import repositories.interfaces.IRoomRepository;

import java.util.List;

public class RoomController implements IRoomController {
    private final IRoomRepository repo;

    public RoomController(IRoomRepository repo) {
        this.repo = repo;
    }

    @Override
    public String addRoom(int roomNumber, String roomType, double price) {
        Room room = new Room(roomNumber, roomType, price);
        boolean created = repo.createRoom(room);
        return (created) ? "Room was added successfully." : "Failed to add the room.";
    }

    @Override
    public String getRoomById(int id) {
        Room room = repo.getRoomById(id);
        return (room == null) ? "Room not found." : room.toString();
    }

    @Override
    public String getAllRooms() {
        List<Room> rooms = repo.getAllRooms();
        StringBuilder response = new StringBuilder("Available rooms:\n");
        for (Room room : rooms) {
            response.append("Room ID: ").append(room.getId())
                    .append(", Room Type: ").append(room.getRoomType())
                    .append(", Price: ").append(room.getPrice()).append(" ₸\n");
        }
        return response.toString();
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
        boolean deleted = repo.deleteAllRooms();
        return deleted ? "All rooms were deleted successfully." : "Failed to delete all rooms.";
    }

}
