package controllers.interfaces;

import models.Room;
import java.time.LocalDate;
import java.util.List;

public interface IRoomController {
    String addRoom(int roomNumber, String roomType, double price);
    String getRoomById(int id);
    String getAllRooms();
    List<String> getRoomTypes();
    List<Room> getAvailableRoomsByType(String roomType);
    List<Room> getAvailableRoomsByTypeAndDate(String roomType, LocalDate checkInDate, LocalDate checkOutDate);
    String deleteAllRooms();
    boolean updateRoomDetails(int roomId, String roomType, double price);
}
