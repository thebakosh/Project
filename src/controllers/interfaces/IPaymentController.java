package controllers.interfaces;

import java.time.LocalDate;

public interface IPaymentController {
    String addPayment(int bookingId, double paymentAmount, String paymentDate);
    String getPaymentById(int id);
    String getAllPayments();
    String deleteAllPayments();
    String getTotalIncomeForDate(LocalDate date);
}



