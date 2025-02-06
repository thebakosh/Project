package models;

public class Room {
    private int id;
    private int roomNumber;
    private String roomType;
    private double price;

    public Room() {
    }

    public Room(int roomNumber, String roomType, double price) {
        setRoomNumber(roomNumber);
        setRoomType(roomType);
        setPrice(price);
    }

    public Room(int id, int roomNumber, String roomType, double price) {
        this(roomNumber, roomType, price);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                '}';
    }
}
