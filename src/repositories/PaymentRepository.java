package repositories;

import data.interfaces.IDB;
import models.Payment;
import factories. *;
import repositories.interfaces.IPaymentRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaymentRepository implements IPaymentRepository {
    private final IDB db;

    public PaymentRepository(IDB db) {
        this.db = db;
    }

    public double getBookingPrice(int bookingId) {
        String sql = "SELECT r.price FROM bookings b " +
                "JOIN rooms r ON b.room_id = r.id " +
                "WHERE b.id = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            System.out.println("SQL error while fetching booking price: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean createPayment(Payment payment) {
        Scanner scanner = new Scanner(System.in);
        double bookingPrice = getBookingPrice(payment.getBookingId());

        if (bookingPrice == -1) {
            System.out.println("Invalid booking ID. Payment cannot be processed.");
            return false;
        }

        double enteredAmount = payment.getPaymentAmount();

        while (enteredAmount != bookingPrice) {
            System.out.println("Payment amount does not match booking price.");
            System.out.println("Booking price: " + bookingPrice + " ₸");
            System.out.print("Please enter the correct amount: ");

            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input! Please enter a valid numeric amount.");
                scanner.next();
            }

            enteredAmount = scanner.nextDouble();
        }

        String sql = "INSERT INTO payments (booking_id, payment_amount, payment_date) VALUES (?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, payment.getBookingId());
            stmt.setDouble(2, enteredAmount);
            stmt.setDate(3, payment.getPaymentDate());

            stmt.executeUpdate();
            System.out.println("Payment successfully added.");
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error while creating payment: " + e.getMessage());
        }
        return false;
    }

    @Override
    public double getTotalIncomeForDate(LocalDate date) {
        String sql = "SELECT SUM(payment_amount) AS total_income FROM payments WHERE payment_date = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total_income");
            }
        } catch (SQLException e) {
            System.out.println("SQL error while fetching total income: " + e.getMessage());
        }
        return 0.0;
    }

    @Override
    public Payment getPaymentById(int id) {
        String sql = "SELECT * FROM payments WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return PaymentFactory.createPaymentWithId(
                        rs.getInt("id"),
                        rs.getInt("booking_id"),
                        rs.getDouble("payment_amount"),
                        rs.getDate("payment_date")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error while fetching payment by ID: " + e.getMessage());
        }
        return null;
    }



    @Override
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try (Connection connection = db.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                payments.add(PaymentFactory.createPaymentWithId(
                        rs.getInt("id"),
                        rs.getInt("booking_id"),
                        rs.getDouble("payment_amount"),
                        rs.getDate("payment_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL error while fetching all payments: " + e.getMessage());
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
            System.out.println("SQL error while deleting payments: " + e.getMessage());
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
            System.out.println("SQL error while resetting payment ID sequence: " + e.getMessage());
        }
    }
}
