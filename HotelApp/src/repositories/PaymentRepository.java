package repositories;

import data.interfaces.IDB;
import models.Payment;
import repositories.interfaces.IPaymentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository implements IPaymentRepository {
    private final IDB db;

    public PaymentRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createPayment(Payment payment) {
        try (Connection connection = db.getConnection()) {
            String sql = "INSERT INTO payments (booking_id, payment_amount, payment_date) VALUES (?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, payment.getBookingId());
            st.setDouble(2, payment.getPaymentAmount());
            st.setDate(3, payment.getPaymentDate());

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void resetPaymentIdSequence() {
        String resetSequenceSql = "SELECT setval('payments_id_seq', 1, false)";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(resetSequenceSql);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    @Override
    public Payment getPaymentById(int id) {
        try (Connection connection = db.getConnection()) {
            String sql = "SELECT * FROM payments WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Payment(
                        rs.getInt("id"),
                        rs.getInt("booking_id"),
                        rs.getDouble("payment_amount"),
                        rs.getDate("payment_date")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = db.getConnection()) {
            String sql = "SELECT * FROM payments";
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("id"),
                        rs.getInt("booking_id"),
                        rs.getDouble("payment_amount"),
                        rs.getDate("payment_date")
                );
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return payments;
    }

    @Override
    public boolean deleteAllPayments() {
        String deleteSql = "DELETE FROM payments";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(deleteSql);
            if (rowsAffected > 0) {
                resetPaymentIdSequence();
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }


}

