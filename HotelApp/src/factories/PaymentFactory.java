package factories;

import models.Payment;

import java.sql.Date;

public class PaymentFactory {

    public static Payment createNewPayment(int bookingId, double paymentAmount, Date paymentDate) {
        return new Payment.Builder()
                .setBookingId(bookingId)
                .setPaymentAmount(paymentAmount)
                .setPaymentDate(paymentDate)
                .build();
    }

    public static Payment createPaymentWithId(int id, int bookingId, double paymentAmount, Date paymentDate) {
        return new Payment.Builder()
                .setId(id)
                .setBookingId(bookingId)
                .setPaymentAmount(paymentAmount)
                .setPaymentDate(paymentDate)
                .build();
    }
}
