package repositories.interfaces;

import models.Booking;
import java.util.List;

public interface IBookingRepository {
    boolean createBooking(Booking booking);
    Booking getBookingById(int id);
    List<Booking> getAllBookings();
    void resetBookingIdSequence();
    boolean deleteAllBookings();
    String getBookingDetailsById(int bookingId);
}

