package controllers;

import controllers.interfaces.IBookingController;
import models.Booking;
import repositories.interfaces.IBookingRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class BookingController implements IBookingController {
    private final IBookingRepository bookingRepository;

    public BookingController(IBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public String addBooking(int guestId, int roomId, String checkInDate, String checkOutDate) {
        Booking booking = new Booking(guestId, roomId, Date.valueOf(checkInDate), Date.valueOf(checkOutDate));
        boolean created = bookingRepository.createBooking(booking);
        return created ? "Booking successfully added." : "Failed to add booking.";
    }

    @Override
    public String getBookingById(int id) {
        Booking booking = bookingRepository.getBookingById(id);
        return booking == null ? "Booking not found." : booking.toString();
    }

    @Override
    public String getAllBookings() {
        List<Booking> bookings = bookingRepository.getAllBookings();
        if (bookings.isEmpty()) {
            return "No bookings available.";
        }
        StringBuilder response = new StringBuilder();
        for (Booking booking : bookings) {
            response.append(booking.toString()).append("\n");
        }
        return response.toString();
    }
    @Override
    public String deleteAllBookings() {
        boolean deleted = bookingRepository.deleteAllBookings();
        return deleted ? "All bookings were deleted successfully." : "Failed to delete all bookings.";
    }
    @Override
    public String getBookingDetailsById(int bookingId) {
        return bookingRepository.getBookingDetailsById(bookingId);
    }

}

