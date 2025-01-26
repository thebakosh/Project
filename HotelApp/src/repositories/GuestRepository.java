package repositories;

import data.interfaces.IDB;
import models.Guest;
import repositories.interfaces.IGuestRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestRepository implements IGuestRepository {
    private final IDB db;

    public GuestRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createGuest(Guest guest) {
        if (guest.getFirstName() == null || guest.getLastName() == null ||
                guest.getEmail() == null || guest.getPhoneNumber() == null) {
            System.out.println("Guest data contains null values.");
            return false;
        }

        String sql = "INSERT INTO guests (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, guest.getFirstName());
            st.setString(2, guest.getLastName());
            st.setString(3, guest.getEmail());
            st.setString(4, guest.getPhoneNumber());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }


    @Override
    public Guest getGuestById(int id) {
        String sql = "SELECT * FROM guests WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Guest(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Guest> getAllGuests() {
        String sql = "SELECT id, first_name, last_name, email, phone_number FROM guests";
        List<Guest> guests = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Guest guest = new Guest(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number")
                );
                guests.add(guest);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return guests;
    }
    @Override
    public boolean deleteAllGuests() {
        String deleteSql = "DELETE FROM guests";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(deleteSql);
            if (rowsAffected > 0) {
                resetGuestIdSequence();
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void resetGuestIdSequence() {
        String resetSequenceSql = "SELECT setval('guests_id_seq', 1, false)";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(resetSequenceSql);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }


}


