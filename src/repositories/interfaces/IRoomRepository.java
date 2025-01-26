package repositories.interfaces;

import models.Room;

import java.util.List;

public interface IRoomRepository {
    boolean createRoom(Room room);
    Room getRoomById(int id);
    List<Room> getAllRooms();
    boolean deleteAllRooms();
    void resetRoomIdSequence();
    List<String> getRoomTypes();
    List<Room> getAvailableRoomsByType(String roomType);

}

