package repositories.interfaces;

import models.Payment;
import java.time.LocalDate;
import java.util.List;

public interface IPaymentRepository {
    boolean createPayment(Payment payment);
    Payment getPaymentById(int id);
    List<Payment> getAllPayments();
    boolean deleteAllPayments();
    void resetPaymentIdSequence();
    double getTotalIncomeForDate(LocalDate date); // Moved from BookingRepository
}
