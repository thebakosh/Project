package repositories.interfaces;

import models.Payment;

import java.util.List;

public interface IPaymentRepository {
    boolean createPayment(Payment payment);
    Payment getPaymentById(int id);
    List<Payment> getAllPayments();
    boolean deleteAllPayments();
    void resetPaymentIdSequence();

}

