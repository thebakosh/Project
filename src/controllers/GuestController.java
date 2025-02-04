package controllers;

import controllers.interfaces.IGuestController;
import models.Guest;
import repositories.interfaces.IGuestRepository;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

public class GuestController implements IGuestController {
    private final IGuestRepository repo;

    public GuestController(IGuestRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createGuest(String firstName, String lastName, String email, String phoneNumber) {
        if (Arrays.stream(new String[]{firstName, lastName, email, phoneNumber})
                .anyMatch(field -> field == null || field.trim().isEmpty())) {
            return "All fields are required. Please provide valid data.";
        }

        if (!isValidEmail(email)) return "Invalid email format.";
        if (!isValidPhoneNumber(phoneNumber)) return "Phone number should only contain digits.";

        return repo.createGuest(new Guest(firstName, lastName, email, phoneNumber))
                ? "Guest was created successfully."
                : "Guest creation failed.";
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }

    @Override
    public String getGuestById(int id) {
        return Optional.ofNullable(repo.getGuestById(id))
                .map(Guest::toString)
                .orElse("Guest was not found.");
    }

    @Override
    public String getAllGuests() {
        return repo.getAllGuests().stream()
                .map(Guest::toString)
                .reduce((g1, g2) -> g1 + "\n" + g2)
                .orElse("No guests found.");
    }

    @Override
    public String deleteAllGuests() {
        return repo.deleteAllGuests() ? "All guests were deleted successfully."
                : "Failed to delete all guests.";
    }
}
