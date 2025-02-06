package controllers;

import controllers.interfaces.IPaymentController;
import services.PaymentService;

import java.time.LocalDate;

public class PaymentController implements IPaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public String addPayment(int bookingId, double paymentAmount, String paymentDate) {
        return paymentService.addPayment(bookingId, paymentAmount, paymentDate);
    }

    @Override
    public String getPaymentById(int id) {
        return paymentService.getPaymentById(id)
                .map(Object::toString)
                .orElse("Payment not found.");
    }

    @Override
    public String getAllPayments() {
        return paymentService.getAllPayments();
    }

    @Override
    public String deleteAllPayments() {
        return paymentService.deleteAllPayments()
                ? "All payments were deleted successfully."
                : "Failed to delete all payments.";
    }

    @Override
    public String getTotalIncomeForDate(LocalDate date) {
        return paymentService.getTotalIncomeForDate(date);
    }
}
