package controllers;

import controllers.interfaces.IRoomController;
import models.Room;
import services.RoomService;

import java.time.LocalDate;
import java.util.List;

public class RoomController implements IRoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public String addRoom(int roomNumber, String roomType, double price) {
        return roomService.addRoom(roomNumber, roomType, price);
    }

    @Override
    public String getRoomById(int id) {
        return roomService.getRoomById(id)
                .map(Room::toString)
                .orElse("Room not found.");
    }

    @Override
    public String getAllRooms() {
        return roomService.getAllRooms();
    }

    @Override
    public List<String> getRoomTypes() {
        return roomService.getRoomTypes();
    }

    @Override
    public List<Room> getAvailableRoomsByType(String roomType) {
        return roomService.getAvailableRoomsByType(roomType);
    }

    @Override
    public String deleteAllRooms() {
        return roomService.deleteAllRooms()
                ? "All rooms were deleted successfully."
                : "Failed to delete all rooms.";
    }

    @Override
    public boolean updateRoomDetails(int roomId, String roomType, double price) {
        return roomService.updateRoomDetails(roomId, roomType, price);
    }

    @Override
    public List<Room> getAvailableRoomsByTypeAndDate(String roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        return roomService.getAvailableRoomsByTypeAndDate(roomType, checkInDate, checkOutDate);
    }
}
