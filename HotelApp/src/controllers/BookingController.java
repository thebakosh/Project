package controllers;

import controllers.interfaces.IBookingController;
import models.Booking;
import services.BookingService;

public class BookingController implements IBookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String addBooking(int guestId, int roomId, String checkInDate, String checkOutDate) {
        return bookingService.addBooking(guestId, roomId, checkInDate, checkOutDate)
                ? "Booking successfully added."
                : "Failed to add booking.";
    }

    @Override
    public String getBookingById(int id) {
        return bookingService.getBookingById(id)
                .map(Booking::toString)
                .orElse("Booking not found.");
    }

    @Override
    public String getAllBookings() {
        return bookingService.getAllBookings();
    }

    @Override
    public String deleteAllBookings() {
        return bookingService.deleteAllBookings()
                ? "All bookings were deleted successfully."
                : "Failed to delete all bookings.";
    }

    @Override
    public String getBookingDetailsById(int bookingId) {
        return bookingService.getBookingDetailsById(bookingId);
    }
    @Override
    public boolean isBookingExists(int bookingId) {
        return bookingService.isBookingExists(bookingId);
    }
}
