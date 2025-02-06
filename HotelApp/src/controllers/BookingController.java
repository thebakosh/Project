package controllers;

import controllers.interfaces.IBookingController;
import models.Booking;
import repositories.interfaces.IBookingRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingController implements IBookingController {
    private final IBookingRepository bookingRepository;

    public BookingController(IBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public String addBooking(int guestId, int roomId, String checkInDate, String checkOutDate) {
        return bookingRepository.createBooking(new Booking(guestId, roomId, Date.valueOf(checkInDate), Date.valueOf(checkOutDate)))
                ? "Booking successfully added."
                : "Failed to add booking.";
    }

    @Override
    public String getBookingById(int id) {
        return Optional.ofNullable(bookingRepository.getBookingById(id))
                .map(Booking::toString)
                .orElse("Booking not found.");
    }

    @Override
    public String getAllBookings() {
        return Optional.ofNullable(bookingRepository.getAllBookings())
                .filter(bookings -> !bookings.isEmpty())
                .map(bookings -> bookings.stream()
                        .map(Booking::toString)
                        .collect(Collectors.joining("\n")))
                .orElse("No bookings available.");
    }

    @Override
    public String deleteAllBookings() {
        return bookingRepository.deleteAllBookings()
                ? "All bookings were deleted successfully."
                : "Failed to delete all bookings.";
    }
    @Override
    public String getBookingDetailsById(int bookingId) {
        return bookingRepository.getBookingDetailsById(bookingId);
    }

}

