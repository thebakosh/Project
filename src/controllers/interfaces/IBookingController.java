package controllers.interfaces;

public interface IBookingController {
    String addBooking(int guestId, int roomId, String checkInDate, String checkOutDate);
    String getBookingById(int id);
    String getAllBookings();
    String deleteAllBookings();
    String getBookingDetailsById(int bookingId);
}
