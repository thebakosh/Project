package repositories.interfaces;

import models.Room;
import java.time.LocalDate;
import java.util.List;

public interface IRoomRepository {
    boolean createRoom(Room room);
    Room getRoomById(int id);
    List<Room> getAllRooms();
    boolean deleteAllRooms();
    List<String> getRoomTypes();
    List<Room> getAvailableRoomsByType(String roomType);
    List<Room> getAvailableRoomsByTypeAndDate(String roomType, LocalDate checkInDate, LocalDate checkOutDate);

    void resetRoomIdSequence();

    boolean updateRoomDetails(int roomId, String roomType, double price);
}
