package factories;

import models.Room;

public class RoomFactory {

    public static Room createNewRoom(int roomNumber, String roomType, double price) {
        return new Room.Builder()
                .setRoomNumber(roomNumber)
                .setRoomType(roomType)
                .setPrice(price)
                .build();
    }

    public static Room createRoomWithId(int id, int roomNumber, String roomType, double price) {
        return new Room.Builder()
                .setId(id)
                .setRoomNumber(roomNumber)
                .setRoomType(roomType)
                .setPrice(price)
                .build();
    }
}
