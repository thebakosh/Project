package repositories;

import data.interfaces.IDB;
import models.Booking;
import repositories.interfaces.IBookingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements IBookingRepository {
    private final IDB db;

    public BookingRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (guest_id, room_id, check_in_date, check_out_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, booking.getGuestId());
            stmt.setInt(2, booking.getRoomId());
            stmt.setDate(3, booking.getCheckInDate());
            stmt.setDate(4, booking.getCheckOutDate());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Booking(
                            rs.getInt("id"),
                            rs.getInt("guest_id"),
                            rs.getInt("room_id"),
                            rs.getDate("check_in_date"),
                            rs.getDate("check_out_date")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection connection = db.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("id"),
                        rs.getInt("guest_id"),
                        rs.getInt("room_id"),
                        rs.getDate("check_in_date"),
                        rs.getDate("check_out_date")
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return bookings;
    }
    @Override
    public boolean deleteAllBookings() {
        String deleteSql = "DELETE FROM bookings";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(deleteSql);
            if (rowsAffected > 0) {
                resetBookingIdSequence();
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }


    @Override
    public void resetBookingIdSequence() {
        String resetSequenceSql = "SELECT setval('bookings_id_seq', 1, false)";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(resetSequenceSql);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    @Override
    public String getBookingDetailsById(int bookingId) {
        String sql = "SELECT " +
                "b.id AS booking_id, " +
                "g.id AS guest_id, " +
                "g.first_Name AS guest_first_name, " +
                "g.last_Name AS guest_last_name, " +
                "g.email AS guest_email, " +
                "g.phone_Number AS guest_phone_number, " +
                "r.id AS room_id, " +
                "r.room_number AS room_number, " +
                "r.room_type AS room_type, " +
                "r.price AS room_price, " +
                "b.check_In_Date AS check_in_date, " +
                "b.check_Out_Date AS check_out_date, " +
                "p.id AS payment_id, " +
                "p.payment_Amount AS payment_amount, " +
                "p.payment_Date AS payment_date " +
                "FROM bookings b " +
                "JOIN guests g ON b.guest_Id = g.id " +
                "JOIN rooms r ON b.room_Id = r.id " +
                "LEFT JOIN payments p ON b.id = p.booking_Id " +
                "WHERE b.id = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String bookingDetails = String.format("Booking ID: %d\n" +
                                    "Guest ID: %d, Name: %s %s, Email: %s, Phone: %s\n" +
                                    "Room ID: %d, Room Number: %s, Room Type: %s, Room Price: %.2f\n" +
                                    "Check-in Date: %s, Check-out Date: %s\n" +
                                    "Payment ID: %d, Amount: %.2f, Payment Date: %s",
                            rs.getInt("booking_id"),
                            rs.getInt("guest_id"),
                            rs.getString("guest_first_name"),
                            rs.getString("guest_last_name"),
                            rs.getString("guest_email"),
                            rs.getString("guest_phone_number"),
                            rs.getInt("room_id"),
                            rs.getString("room_number"),
                            rs.getString("room_type"),
                            rs.getDouble("room_price"),
                            rs.getDate("check_in_date"),
                            rs.getDate("check_out_date"),
                            rs.getInt("payment_id"),
                            rs.getDouble("payment_amount"),
                            rs.getDate("payment_date")
                    );
                    return bookingDetails;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return "Booking not found.";
    }
}


