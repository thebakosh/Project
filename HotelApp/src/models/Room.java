package models;

public class Room {
    private final int id;
    private final int roomNumber;
    private final String roomType;
    private final double price;

    private Room(Builder builder) {
        this.id = builder.id;
        this.roomNumber = builder.roomNumber;
        this.roomType = builder.roomType;
        this.price = builder.price;
    }

    public int getId() {
        return id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
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

    public static class Builder {
        private int id;
        private int roomNumber;
        private String roomType;
        private double price;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setRoomNumber(int roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder setRoomType(String roomType) {
            this.roomType = roomType;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }
}
