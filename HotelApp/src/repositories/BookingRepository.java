package repositories;

import data.interfaces.IDB;
import models.Booking;
import repositories.interfaces.IBookingRepository;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public double getTotalIncomeForDate(LocalDate date) {
        String sql = "SELECT SUM(rooms.price) AS total_income " +
                "FROM bookings " +
                "JOIN rooms ON bookings.room_id = rooms.id " +
                "WHERE ? BETWEEN bookings.check_in_date AND bookings.check_out_date";

        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setDate(1, Date.valueOf(date));
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_income");
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return 0.0;
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
    public boolean bookRoom(int roomId, LocalDateTime startTime, LocalDateTime endTime) {
        try (Connection con = db.getConnection()) {
            String checkAvailabilityQuery = "SELECT * FROM bookings WHERE room_id = ? AND end_time > ?";
            PreparedStatement checkStmt = con.prepareStatement(checkAvailabilityQuery);
            checkStmt.setInt(1, roomId);
            checkStmt.setTimestamp(2, Timestamp.valueOf(startTime));
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return false;
            }

            String bookRoomQuery = "INSERT INTO bookings (room_id, start_time, end_time) VALUES (?, ?, ?)";
            PreparedStatement bookStmt = con.prepareStatement(bookRoomQuery);
            bookStmt.setInt(1, roomId);
            bookStmt.setTimestamp(2, Timestamp.valueOf(startTime));
            bookStmt.setTimestamp(3, Timestamp.valueOf(endTime));
            bookStmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateRoomStatus() {
        try (Connection con = db.getConnection()) {
            String updateStatusQuery = "UPDATE rooms SET status = 'available' WHERE id IN (SELECT room_id FROM bookings WHERE end_time < NOW())";
            PreparedStatement updateStmt = con.prepareStatement(updateStatusQuery);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


