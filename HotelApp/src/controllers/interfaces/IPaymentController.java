package controllers.interfaces;

public interface IPaymentController {
    String addPayment(int bookingId, double paymentAmount, String paymentDate);
    String getPaymentById(int id);
    String getAllPayments();
    String deleteAllPayments();

}


