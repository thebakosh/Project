package repositories.interfaces;

import models.Guest;

import java.util.List;

public interface IGuestRepository {
    boolean createGuest(Guest guest);
    Guest getGuestById(int id);
    List<Guest> getAllGuests();
    boolean deleteAllGuests();
    void resetGuestIdSequence();
    boolean isEmailExists(String email);
    boolean isPhoneNumberExists(String phoneNumber);
    boolean isGuestExists(int guestId);
}

