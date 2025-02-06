import controllers.*;
import controllers.interfaces.*;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.*;
import repositories.interfaces.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "2109", "hotel_management");


        IGuestRepository guestRepository = new GuestRepository(db);
        IRoomRepository roomRepository = new RoomRepository(db);
        IBookingRepository bookingRepository = new BookingRepository(db);
        IPaymentRepository paymentRepository = new PaymentRepository(db);


        IGuestController guestController = new GuestController(guestRepository);
        IRoomController roomController = new RoomController(roomRepository);
        IBookingController bookingController = new BookingController(bookingRepository);
        IPaymentController paymentController = new PaymentController(paymentRepository);


        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        while (currentUser == null) {
            System.out.println("Welcome to the Hotel Management System");
            System.out.print("Enter username: ");
            String username = scanner.next();
            System.out.print("Enter password: ");
            String password = scanner.next();

            if (username.equals("admin") && password.equals("admin")) {
                currentUser = new User("admin", "Admin");
                System.out.println("Login successful! Welcome, Admin.");
            } else if (username.equals("manager") && password.equals("manager")) {
                currentUser = new User("manager", "Manager");
                System.out.println("Login successful! Welcome, Manager.");
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }

        HotelApplication app = new HotelApplication(guestController, roomController, bookingController, paymentController, currentUser);

        try {
            app.start();
        } finally {
            db.close();
            scanner.close();
        }
    }
}
