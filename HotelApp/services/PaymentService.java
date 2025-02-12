package services;

import models.Payment;
import repositories.interfaces.IPaymentRepository;
import factories. *;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaymentService {
    private final IPaymentRepository paymentRepository;

    public PaymentService(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public String addPayment(int bookingId, double paymentAmount, String paymentDate) {
        if (paymentAmount <= 0) {
            return "Invalid payment amount.";
        }

        Payment payment = PaymentFactory.createNewPayment(
                bookingId,
                paymentAmount,
                Date.valueOf(paymentDate)
        );

        boolean success = paymentRepository.createPayment(payment);
        return success ? "Payment was successfully added." : "Failed to add payment.";
    }


    public Optional<Payment> getPaymentById(int id) {
        return Optional.ofNullable(paymentRepository.getPaymentById(id));
    }

    public String getAllPayments() {
        List<Payment> payments = paymentRepository.getAllPayments();
        return payments.isEmpty()
                ? "No payments available."
                : payments.stream().map(Payment::toString).collect(Collectors.joining("\n"));
    }

    public boolean deleteAllPayments() {
        return paymentRepository.deleteAllPayments();
    }

    public String getTotalIncomeForDate(LocalDate date) {
        double income = paymentRepository.getTotalIncomeForDate(date);
        return "Total income for " + date + ": " + income + " ₸";
    }
}
