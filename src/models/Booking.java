package models;
import java.sql.Date;

public class Booking {
    private final int id;
    private final int guestId;
    private final int roomId;
    private final Date checkInDate;
    private final Date checkOutDate;

    private Booking(Builder builder) {
        this.id = builder.id;
        this.guestId = builder.guestId;
        this.roomId = builder.roomId;
        this.checkInDate = builder.checkInDate;
        this.checkOutDate = builder.checkOutDate;
    }
    public int getId() {return id;}
    public int getGuestId() {return guestId;}
    public int getRoomId() {return roomId;}
    public Date getCheckInDate() {return checkInDate;}
    public Date getCheckOutDate() {return checkOutDate;}
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", guestId=" + guestId +
                ", roomId=" + roomId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                '}';
    }

    public static class Builder {
        private int id;
        private int guestId;
        private int roomId;
        private Date checkInDate;
        private Date checkOutDate;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }
        public Builder setGuestId(int guestId) {
            this.guestId = guestId;
            return this;
        }
        public Builder setRoomId(int roomId) {
            this.roomId = roomId;
            return this;
        }
        public Builder setCheckInDate(Date checkInDate) {
            this.checkInDate = checkInDate;
            return this;
        }
        public Builder setCheckOutDate(Date checkOutDate) {
            this.checkOutDate = checkOutDate;
            return this;
        }
        public Booking build() {
            return new Booking(this);
        }
    }
}
