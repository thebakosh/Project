package controllers;

import controllers.interfaces.IGuestController;
import models.Guest;
import services.GuestService;

import java.util.List;

public class GuestController implements IGuestController {
    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @Override
    public String createGuest(String firstName, String lastName, String email, String phoneNumber) {
        return guestService.createGuest(firstName, lastName, email, phoneNumber);
    }

    @Override
    public String getGuestById(int id) {
        return guestService.getGuestById(id)
                .map(Guest::toString)
                .orElse("Guest was not found.");
    }

    @Override
    public String getAllGuests() {
        List<Guest> guests = guestService.getAllGuests();
        return guests.isEmpty() ? "No guests found." : String.join("\n", guests.stream().map(Guest::toString).toList());
    }

    @Override
    public String deleteAllGuests() {
        return guestService.deleteAllGuests()
                ? "All guests were deleted successfully."
                : "Failed to delete all guests.";
    }
    @Override
    public boolean isGuestExists(int guestId) {
        return guestService.isGuestExists(guestId);
    }

}
