package models;

import java.sql.Date;

public class Payment {
    private final int id;
    private final int bookingId;
    private final double paymentAmount;
    private final Date paymentDate;

    private Payment(Builder builder) {
        this.id = builder.id;
        this.bookingId = builder.bookingId;
        this.paymentAmount = builder.paymentAmount;
        this.paymentDate = builder.paymentDate;
    }

    public int getId() {
        return id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
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

    public static class Builder {
        private int id;
        private int bookingId;
        private double paymentAmount;
        private Date paymentDate;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setBookingId(int bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setPaymentAmount(double paymentAmount) {
            this.paymentAmount = paymentAmount;
            return this;
        }

        public Builder setPaymentDate(Date paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
