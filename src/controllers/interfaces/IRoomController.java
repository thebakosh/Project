package controllers.interfaces;

import models.Room;

import java.util.List;

public interface IRoomController {
    String addRoom(int roomNumber, String roomType, double price);
    String getRoomById(int id);
    String getAllRooms();
    String deleteAllRooms();
    List<String> getRoomTypes();
    List<Room> getAvailableRoomsByType(String roomType);
}

