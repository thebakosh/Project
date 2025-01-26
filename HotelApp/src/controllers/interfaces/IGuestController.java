package controllers.interfaces;

public interface IGuestController {
    String createGuest(String firstName, String lastName, String email, String phoneNumber);
    String getGuestById(int id);
    String getAllGuests();
    String deleteAllGuests();

}
