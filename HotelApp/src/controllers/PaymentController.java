package controllers;

import controllers.interfaces.IPaymentController;
import models.Payment;
import repositories.interfaces.IPaymentRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class PaymentController implements IPaymentController {
    private final IPaymentRepository paymentRepository;

    public PaymentController(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public String addPayment(int bookingId, double paymentAmount, String paymentDate) {
        Payment payment = new Payment(bookingId, paymentAmount, Date.valueOf(paymentDate));
        boolean created = paymentRepository.createPayment(payment);
        return created ? "Payment was successfully added." : "Failed to add payment.";
    }

    @Override
    public String getPaymentById(int id) {
        Payment payment = paymentRepository.getPaymentById(id);
        return payment == null ? "Payment not found." : payment.toString();
    }

    @Override
    public String getAllPayments() {
        List<Payment> payments = paymentRepository.getAllPayments();
        if (payments.isEmpty()) {
            return "No payments available.";
        }
        StringBuilder response = new StringBuilder();
        for (Payment payment : payments) {
            response.append(payment.toString()).append("\n");
        }
        return response.toString();
    }

    @Override
    public String deleteAllPayments() {
        boolean deleted = paymentRepository.deleteAllPayments();
        return deleted ? "All payments were deleted successfully." : "Failed to delete all payments.";
    }

    @Override
    public String getTotalIncomeForDate(LocalDate date) {
        double income = paymentRepository.getTotalIncomeForDate(date);
        return "Total income for " + date + ": " + income + " ₸";
    }
}
