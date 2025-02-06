package services;

import models.Booking;
import repositories.interfaces.IBookingRepository;

import java.sql.Date;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingService {
    private final IBookingRepository bookingRepository;

    public BookingService(IBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public boolean addBooking(int guestId, int roomId, String checkInDate, String checkOutDate) {
        Booking booking = new Booking(guestId, roomId, Date.valueOf(checkInDate), Date.valueOf(checkOutDate));
        return bookingRepository.createBooking(booking);
    }

    public Optional<Booking> getBookingById(int id) {
        return Optional.ofNullable(bookingRepository.getBookingById(id));
    }

    public String getAllBookings() {
        return Optional.ofNullable(bookingRepository.getAllBookings())
                .filter(bookings -> !bookings.isEmpty())
                .map(bookings -> bookings.stream()
                        .map(Booking::toString)
                        .collect(Collectors.joining("\n")))
                .orElse("No bookings available.");
    }

    public boolean deleteAllBookings() {
        return bookingRepository.deleteAllBookings();
    }

    public String getBookingDetailsById(int bookingId) {
        return bookingRepository.getBookingDetailsById(bookingId);
    }
}

