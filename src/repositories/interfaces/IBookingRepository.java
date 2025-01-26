package repositories.interfaces;

import models.Booking;

import java.time.LocalDate;
import java.util.List;

public interface IBookingRepository {
    boolean createBooking(Booking booking);
    Booking getBookingById(int id);
    List<Booking> getAllBookings();
    double getTotalIncomeForDate(LocalDate date);
    void resetBookingIdSequence();
    boolean deleteAllBookings();

}
