package factories;

import models.Guest;

public class GuestFactory {

    public static Guest createNewGuest(String firstName, String lastName, String email, String phoneNumber) {
        return new Guest.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .build();
    }

    public static Guest createGuestWithId(int id, String firstName, String lastName, String email, String phoneNumber) {
        return new Guest.Builder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .build();
    }
}
