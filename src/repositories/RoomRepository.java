package repositories;

import data.interfaces.IDB;
import models.Room;
import repositories.interfaces.IRoomRepository;
import factories. *;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements IRoomRepository {
    private final IDB db;

    public RoomRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, room_type, price) VALUES (?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, room.getRoomNumber());
            st.setString(2, room.getRoomType());
            st.setDouble(3, room.getPrice());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Room getRoomById(int id) {
        String sql = "SELECT * FROM rooms WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return RoomFactory.createRoomWithId(
                        rs.getInt("id"),
                        rs.getInt("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }



    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Connection connection = db.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                rooms.add(RoomFactory.createRoomWithId(
                        rs.getInt("id"),
                        rs.getInt("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return rooms;
    }



    @Override
    public boolean deleteAllRooms() {
        String deleteSql = "DELETE FROM rooms";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(deleteSql);
            if (rowsAffected > 0) {
                resetRoomIdSequence();
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<String> getRoomTypes() {
        String sql = "SELECT DISTINCT room_type FROM rooms";
        List<String> roomTypes = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                roomTypes.add(rs.getString("room_type"));
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return roomTypes;
    }

    @Override
    public List<Room> getAvailableRoomsByType(String roomType) {
        String sql = "SELECT * FROM rooms WHERE room_type = ? AND id NOT IN " +
                "(SELECT room_id FROM bookings WHERE CURRENT_DATE BETWEEN check_in_date AND check_out_date)";
        List<Room> availableRooms = new ArrayList<>();

        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, roomType);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Room room = new Room.Builder()
                        .setId(rs.getInt("id"))
                        .setRoomNumber(rs.getInt("room_number"))
                        .setRoomType(rs.getString("room_type"))
                        .setPrice(rs.getDouble("price"))
                        .build();

                availableRooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return availableRooms;
    }


    @Override
    public void resetRoomIdSequence() {
        String resetSequenceSql = "SELECT setval('rooms_id_seq', 1, false)";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(resetSequenceSql);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    @Override
    public boolean updateRoomDetails(int roomId, String roomType, double price) {
        String sql = "UPDATE rooms SET room_type = ?, price = ? WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, roomType);
            st.setDouble(2, price);
            st.setInt(3, roomId);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }
    @Override
    public List<Room> getAvailableRoomsByTypeAndDate(String roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        String sql = "SELECT * FROM rooms WHERE room_type = ? " +
                "AND id NOT IN (SELECT room_id FROM bookings " +
                "WHERE (check_in_date <= ? AND check_out_date >= ?) " +
                "OR (check_in_date <= ? AND check_out_date >= ?) " +
                "OR (check_in_date >= ? AND check_out_date <= ?))";

        List<Room> availableRooms = new ArrayList<>();
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, roomType);
            st.setDate(2, Date.valueOf(checkOutDate));
            st.setDate(3, Date.valueOf(checkInDate));
            st.setDate(4, Date.valueOf(checkInDate));
            st.setDate(5, Date.valueOf(checkOutDate));
            st.setDate(6, Date.valueOf(checkInDate));
            st.setDate(7, Date.valueOf(checkOutDate));

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Room room = new Room.Builder()
                        .setId(rs.getInt("id"))
                        .setRoomNumber(rs.getInt("room_number"))
                        .setRoomType(rs.getString("room_type"))
                        .setPrice(rs.getDouble("price"))
                        .build();

                availableRooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return availableRooms;
    }
}
