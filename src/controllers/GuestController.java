package controllers;

import controllers.interfaces.IGuestController;
import models.Guest;
import repositories.interfaces.IGuestRepository;

import java.util.List;
import java.util.Scanner;

public class GuestController implements IGuestController {
    private final IGuestRepository repo;

    public GuestController(IGuestRepository repo) {
        this.repo = repo;
    }
    @Override
    public String createGuest(String firstName, String lastName, String email, String phoneNumber) {
        Scanner scanner = new Scanner(System.in);

        while (firstName == null || !isValidName(firstName)|| firstName.trim().isEmpty()) {
            System.out.print("Invalid first name. Enter again: ");
            firstName = scanner.nextLine();
        }

        while (lastName == null || !isValidName(lastName)|| lastName.trim().isEmpty()) {
            System.out.print("Invalid last name. Enter again: ");
            lastName = scanner.nextLine();
        }

        while (!isValidEmail(email)) {
            System.out.print("Invalid email format. Enter again: ");
            email = scanner.nextLine();
        }

        while (!isValidPhoneNumber(phoneNumber)) {
            System.out.print("Invalid Kazakhstan phone number format. Enter again: ");
            phoneNumber = scanner.nextLine();
        }

        Guest guest = new Guest(firstName, lastName, email, phoneNumber);
        boolean created = repo.createGuest(guest);

        return created ? "Guest was created successfully." : "Guest creation failed.";
    }

    private boolean isValidName(String name) {
        if (!name.matches("^[A-Za-zА-Яа-яЁё\\s-]+$")) {
            System.out.println("Invalid name! Only letters, spaces, or hyphens are allowed.");
            return false;
        }
        return true;
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^(\\+7|8)(7\\d{2}|3\\d{2})\\d{7}$") ||
                phoneNumber.matches("^(\\+7|8)\\s?(7\\d{2}|3\\d{2})\\s?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$");
    }

    @Override
    public String getGuestById(int id) {
        Guest guest = repo.getGuestById(id);
        return (guest == null) ? "Guest was not found" : guest.toString();
    }

    @Override
    public String getAllGuests() {
        List<Guest> guests = repo.getAllGuests();
        StringBuilder responce = new StringBuilder();
        for (Guest guest : guests) {
            responce.append(guest.toString()).append("\n");
        }
        return responce.toString();
    }
    @Override
    public String deleteAllGuests() {
        boolean deleted = repo.deleteAllGuests();
        return deleted ? "All guests were deleted successfully." : "Failed to delete all guests.";
    }
}