package factories;

import models.Booking;
import java.sql.Date;

public class BookingFactory {

    public static Booking createNewBooking(int guestId, int roomId, Date checkInDate, Date checkOutDate) {
        return new Booking.Builder()
                .setGuestId(guestId)
                .setRoomId(roomId)
                .setCheckInDate(checkInDate)
                .setCheckOutDate(checkOutDate)
                .build();
    }

    public static Booking createBookingWithId(int id, int guestId, int roomId, Date checkInDate, Date checkOutDate) {
        return new Booking.Builder()
                .setId(id)
                .setGuestId(guestId)
                .setRoomId(roomId)
                .setCheckInDate(checkInDate)
                .setCheckOutDate(checkOutDate)
                .build();
    }
}
