package models;

import java.sql.Date;

public class Booking {
    private int id;
    private int guestId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;

    public Booking() {
    }

    public Booking(int guestId, int roomId, Date checkInDate, Date checkOutDate) {
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Booking(int id, int guestId, int roomId, Date checkInDate, Date checkOutDate) {
        this(guestId, roomId, checkInDate, checkOutDate);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

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
}
