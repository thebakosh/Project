package controllers;

import controllers.interfaces.IPaymentController;
import models.Payment;
import repositories.interfaces.IPaymentRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class PaymentController implements IPaymentController {
    private final IPaymentRepository paymentRepository;

    public PaymentController(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public String addPayment(int bookingId, double paymentAmount, String paymentDate) {
        return paymentRepository.createPayment(new Payment(bookingId, paymentAmount, Date.valueOf(paymentDate)))
                ? "Payment was successfully added."
                : "Failed to add payment.";
    }

    @Override
    public String getPaymentById(int id) {
        return Optional.ofNullable(paymentRepository.getPaymentById(id))
                .map(Payment::toString)
                .orElse("Payment not found.");
    }

    @Override
    public String getAllPayments() {
        return Optional.ofNullable(paymentRepository.getAllPayments())
                .filter(payments -> !payments.isEmpty())
                .map(payments -> payments.stream()
                        .map(Payment::toString)
                        .collect(Collectors.joining("\n")))
                .orElse("No payments available.");
    }

    @Override
    public String deleteAllPayments() {
        return paymentRepository.deleteAllPayments()
                ? "All payments were deleted successfully."
                : "Failed to delete all payments.";
    }
    @Override
    public String getTotalIncomeForDate(LocalDate date) {
        double income = paymentRepository.getTotalIncomeForDate(date);
        return "Total income for " + date + ": " + income + " ₸";
    }
}
