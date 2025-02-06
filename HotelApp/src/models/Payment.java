package models;

import java.sql.Date;

public class Payment {
    private int id;
    private int bookingId;
    private double paymentAmount;
    private Date paymentDate;

    public Payment() {
    }

    public Payment(int bookingId, double paymentAmount, Date paymentDate) {
        setBookingId(bookingId);
        setPaymentAmount(paymentAmount);
        setPaymentDate(paymentDate);
    }

    public Payment(int id, int bookingId, double paymentAmount, Date paymentDate) {
        this(bookingId, paymentAmount, paymentDate);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", bookingId=" + bookingId +
                ", paymentAmount=" + paymentAmount +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
