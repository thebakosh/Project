package services;

import models.Guest;
import factories. *;
import repositories.interfaces.IGuestRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GuestService {
    private final IGuestRepository repo;
    private final Scanner scanner = new Scanner(System.in);

    public GuestService(IGuestRepository repo) {
        this.repo = repo;
    }

    public String createGuest(String firstName, String lastName, String email, String phoneNumber) {
        while (firstName == null || !isValidName(firstName) || firstName.trim().isEmpty()) {
            System.out.print("Invalid first name. Enter again: ");
            firstName = scanner.nextLine();
        }

        while (lastName == null || !isValidName(lastName) || lastName.trim().isEmpty()) {
            System.out.print("Invalid last name. Enter again: ");
            lastName = scanner.nextLine();
        }

        while (!isValidEmail(email) || repo.isEmailExists(email)) {
            if (repo.isEmailExists(email)) {
                System.out.print("Email already exists. Enter a different email: ");
            } else {
                System.out.print("Invalid email format. Enter again: ");
            }
            email = scanner.nextLine();
        }

        while (!isValidPhoneNumber(phoneNumber) || repo.isPhoneNumberExists(phoneNumber)) {
            if (repo.isPhoneNumberExists(phoneNumber)) {
                System.out.print("Phone number already exists. Enter a different phone number: ");
            } else {
                System.out.print("Invalid Kazakhstan phone number format. Enter again: ");
            }
            phoneNumber = scanner.nextLine();
        }

        Guest guest = GuestFactory.createNewGuest(firstName, lastName, email, phoneNumber);
        boolean created = repo.createGuest(guest);
        return created ? "Guest was created successfully." : "Guest creation failed.";
    }

    public Optional<Guest> getGuestById(int id) {
        return Optional.ofNullable(repo.getGuestById(id));
    }

    public List<Guest> getAllGuests() {
        return repo.getAllGuests();
    }

    public boolean deleteAllGuests() {
        return repo.deleteAllGuests();
    }

    private boolean isValidName(String name) {
        return name.matches("^[A-Za-zА-Яа-яЁё\\s-]+$");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^(\\+7|8)(7\\d{2}|3\\d{2})\\d{7}$") ||
                phoneNumber.matches("^(\\+7|8)\\s?(7\\d{2}|3\\d{2})\\s?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$");
    }
}