package controllers;

import controllers.interfaces.IGuestController;
import models.Guest;
import repositories.interfaces.IGuestRepository;

import java.util.List;

public class GuestController implements IGuestController {
    private final IGuestRepository repo;

    public GuestController(IGuestRepository repo) {
        this.repo = repo;
    }
    @Override
    public String createGuest(String firstName, String lastName, String email, String phoneNumber) {
        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return "All fields are required. Please provide valid data.";
        }
        if (!isValidEmail(email)) {
            return "Invalid email format.";
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            return "Phone number should only contain digits.";
        }
        Guest guest = new Guest(firstName, lastName, email, phoneNumber);
        boolean created = repo.createGuest(guest);

        return created ? "Guest was created successfully." : "Guest creation failed.";
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d+");
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