import controllers.interfaces.*;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import models.Room;

public class HotelApplication {
    private final IGuestController guestController;
    private final IRoomController roomController;
    private final IBookingController bookingController;
    private final IPaymentController paymentController;
    private final Scanner scanner = new Scanner(System.in);
    private User currentUser = null;

    public HotelApplication(IGuestController guestController, IRoomController roomController,
                            IBookingController bookingController, IPaymentController paymentController) {
        this.guestController = guestController;
        this.roomController = roomController;
        this.bookingController = bookingController;
        this.paymentController = paymentController;
        this.currentUser = currentUser;
    }


    private void mainMenu() {
        System.out.println();
        System.out.println("Welcome to Hotel Management Application");
        System.out.println("Logged in as: " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
        System.out.println("Select one of the following options:");
        System.out.println("1. Manage Guests");
        System.out.println("2. Manage Rooms");
        System.out.println("3. Manage Bookings");
        System.out.println("4. Manage Payments");
        System.out.println("0. Exit");
        System.out.print("Select an option (0-4): ");
    }

    public void start() {
        try {
            while (true) {
                mainMenu();
                try {
                    int option = scanner.nextInt();
                    switch (option) {
                        case 1 -> guestMenu();
                        case 2 -> roomMenu();
                        case 3 -> bookingMenu();
                        case 4 -> paymentMenu();
                        default -> {
                            System.out.println("Exiting application...");
                            return;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid option!");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("----------------------------------------");
            }
        } finally {
            scanner.close();
        }
    }

    private void guestMenu() {
        System.out.println("Guest Management:");
        System.out.println("1. Get all guests");
        System.out.println("2. Get guest by ID");
        System.out.println("3. Add new guest");
        System.out.println("4. Delete all guests");
        System.out.println("0. Back to Main Menu");
        System.out.print("Select an option (0-4): ");
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> System.out.println(guestController.getAllGuests());
                case 2 -> {
                    System.out.print("Enter guest ID: ");
                    System.out.println(guestController.getGuestById(scanner.nextInt()));
                }
                case 3 -> {
                    if (currentUser.getRole().equals("Admin")) {
                        addGuest();
                    } else {
                        System.out.println("You do not have permission to add guests.");
                    }
                }
                case 4 -> {
                    if (currentUser.getRole().equals("Admin")) {
                        deleteAllGuests();
                    } else {
                        System.out.println("You do not have permission to delete guests.");
                    }
                }
                default -> System.out.println("Returning to Main Menu...");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
        }
    }

    private void roomMenu() {
        System.out.println("Room Management:");
        System.out.println("1. Get all rooms");
        System.out.println("2. Add new room");
        System.out.println("3. Delete all rooms");
        System.out.println("4. Get room types");
        System.out.println("5. Get available rooms by type");
        if (currentUser.getRole().equals("Admin")) {
            System.out.println("6. Update room details");
        }
        System.out.println("0. Back to Main Menu");

        System.out.print("Select an option (0-6): ");
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> System.out.println(roomController.getAllRooms());
                case 2 -> {
                    if (currentUser.getRole().equals("Admin")) {
                        addRoom();
                    } else {
                        System.out.println("You do not have permission to add rooms.");
                    }
                }
                case 3 -> {
                    if (currentUser.getRole().equals("Admin")) {
                        deleteAllRooms();
                    } else {
                        System.out.println("You do not have permission to delete rooms.");
                    }
                }
                case 4 -> getRoomTypes();
                case 5 -> getAvailableRoomsByType();

                case 6 -> {
                    if (currentUser.getRole().equals("Admin")) {
                        updateRoomDetails();
                    } else {
                        System.out.println("You do not have permission to update room details.");
                    }
                }
                default -> System.out.println("Returning to Main Menu...");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
        }
    }

    private void bookingMenu() {
        System.out.println("Booking Management:");
        System.out.println("1. Get all bookings");
        System.out.println("2. Get booking by ID");
        System.out.println("3. Get full booking details by ID");
        if (currentUser.getRole().equals("Admin")) {
            System.out.println("4. Add new booking");
            System.out.println("5. Delete all bookings");
        }
        System.out.println("0. Back to Main Menu");
        System.out.print("Select an option (0-5): ");
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> System.out.println(bookingController.getAllBookings());
                case 2 -> {
                    System.out.print("Enter booking ID: ");
                    System.out.println(bookingController.getBookingById(scanner.nextInt()));
                }
                case 3 -> {
                    System.out.print("Enter booking ID: ");
                    int bookingId = scanner.nextInt();
                    System.out.println(bookingController.getBookingDetailsById(bookingId));
                }
                case 4 -> {
                    if (currentUser.getRole().equals("Admin")) {
                        addBooking();
                    } else {
                        System.out.println("You do not have permission to add bookings.");
                    }
                }
                case 5 -> {
                    if (currentUser.getRole().equals("Admin")) {
                        deleteAllBookings();
                    } else {
                        System.out.println("You do not have permission to delete bookings.");
                    }
                }
                default -> System.out.println("Returning to Main Menu...");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
        }
    }

    private void paymentMenu() {
        System.out.println("Payment Management:");
        System.out.println("1. Get all payments");
        System.out.println("2. Get payment by ID");
        if (currentUser.getRole().equals("Admin")) {
            System.out.println("3. Add new payment");
            System.out.println("4. Delete all payments");
            System.out.println("5. Get total income for a specific date");
        }
        System.out.println("0. Back to Main Menu");
        System.out.print("Select an option (0-5): ");
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> System.out.println(paymentController.getAllPayments());
                case 2 -> {
                    System.out.print("Enter payment ID: ");
                    System.out.println(paymentController.getPaymentById(scanner.nextInt()));
                }
                case 3 -> {
                    if (currentUser.getRole().equals("Admin")) {
                        addPayment();
                    } else {
                        System.out.println("You do not have permission to add payments.");
                    }
                }
                case 4 -> {
                    if (currentUser.getRole().equals("Admin")) {
                        deleteAllPayments();
                    } else {
                        System.out.println("You do not have permission to delete payments.");
                    }
                }
                case 5 -> {
                    System.out.print("Enter the date (YYYY-MM-DD): ");
                    String dateInput = scanner.next();
                    LocalDate date = LocalDate.parse(dateInput);
                    System.out.println(paymentController.getTotalIncomeForDate(date));
                }
                default -> System.out.println("Returning to Main Menu...");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
        }
    }

    private void deleteAllPayments() {
        System.out.print("Are you sure you want to delete all payments? (yes/no): ");
        String confirmation = scanner.next();
        if (confirmation.equalsIgnoreCase("yes")) {
            System.out.println(paymentController.deleteAllPayments());
        } else {
            System.out.println("Operation cancelled.");
        }
    }



    private void addGuest() {
        System.out.print("Enter first name: ");
        String firstName = scanner.next();
        System.out.print("Enter last name: ");
        String lastName = scanner.next();
        System.out.print("Enter email: ");
        String email = scanner.next();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.next();
        System.out.println(guestController.createGuest(firstName, lastName, email, phoneNumber));
    }

    private void addRoom() {
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        System.out.print("Enter room type: ");
        String roomType = scanner.next();
        System.out.print("Enter room price: ");
        double price = scanner.nextDouble();
        System.out.println(roomController.addRoom(roomNumber, roomType, price));
    }

    private void addBooking() {
        System.out.print("Enter guest ID: ");
        int guestId = scanner.nextInt();

        System.out.println("Available room types:");
        List<String> roomTypes = roomController.getRoomTypes();
        for (int i = 0; i < roomTypes.size(); i++) {
            System.out.println((i + 1) + ". " + roomTypes.get(i));
        }

        System.out.print("Select a room type by number: ");
        int roomTypeChoice = scanner.nextInt();
        String selectedRoomType = roomTypes.get(roomTypeChoice - 1);

        System.out.println("Fetching available rooms for selected type...");
        List<Room> availableRooms = roomController.getAvailableRoomsByType(selectedRoomType);
        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms for this type.");
            return;
        }

        System.out.println("Available rooms:");
        for (int i = 0; i < availableRooms.size(); i++) {
            Room room = availableRooms.get(i);
            System.out.println((i + 1) + ". Room " + room.getRoomNumber() + " (" + room.getPrice() + " ₸)");
        }

        System.out.print("Select a room by number: ");
        int roomChoice = scanner.nextInt();
        int roomId = availableRooms.get(roomChoice - 1).getId();

        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkInDate = scanner.next();

        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOutDate = scanner.next();

        System.out.println(bookingController.addBooking(guestId, roomId, checkInDate, checkOutDate));
    }


    private void getTotalIncome() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String dateInput = scanner.next();
        try {
            LocalDate date = LocalDate.parse(dateInput); // Преобразуем строку в LocalDate
            System.out.println(bookingController.getTotalIncomeForDate(date));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Use YYYY-MM-DD.");
        }
    }


    private void addPayment() {
        System.out.print("Enter booking ID: ");
        int bookingId = scanner.nextInt();
        System.out.print("Enter payment amount: ");
        double paymentAmount = scanner.nextDouble();
        System.out.print("Enter payment date (YYYY-MM-DD): ");
        String paymentDate = scanner.next();
        System.out.println(paymentController.addPayment(bookingId, paymentAmount, paymentDate));
    }
    private void deleteAllGuests() {
        System.out.print("Are you sure you want to delete all guests? (yes/no): ");
        String confirmation = scanner.next();
        if (confirmation.equalsIgnoreCase("yes")) {
            System.out.println(guestController.deleteAllGuests());
        } else {
            System.out.println("Operation cancelled.");
        }
    }
    private void deleteAllRooms() {
        System.out.print("Are you sure you want to delete all rooms? (yes/no): ");
        String confirmation = scanner.next();
        if (confirmation.equalsIgnoreCase("yes")) {
            System.out.println(roomController.deleteAllRooms());
        } else {
            System.out.println("Operation cancelled.");
        }
    }
    private void deleteAllBookings() {
        System.out.print("Are you sure you want to delete all bookings? (yes/no): ");
        String confirmation = scanner.next();
        if (confirmation.equalsIgnoreCase("yes")) {
            System.out.println(bookingController.deleteAllBookings());
        } else {
            System.out.println("Operation cancelled.");
        }
    }


}

