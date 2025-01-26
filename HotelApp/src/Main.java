import controllers.*;
import controllers.interfaces.*;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.*;
import repositories.interfaces.*;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "1111", "hotel_management");

        IGuestRepository guestRepository = new GuestRepository(db);
        IRoomRepository roomRepository = new RoomRepository(db);
        IBookingRepository bookingRepository = new BookingRepository(db);
        IPaymentRepository paymentRepository = new PaymentRepository(db);

        IGuestController guestController = new GuestController(guestRepository);
        IRoomController roomController = new RoomController(roomRepository);
        IBookingController bookingController = new BookingController(bookingRepository);
        IPaymentController paymentController = new PaymentController(paymentRepository);

        HotelApplication app = new HotelApplication(guestController, roomController, bookingController, paymentController);

        try {
            app.start();
        } finally {
            db.close();
        }
    }
}


