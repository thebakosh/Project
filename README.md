Topic: Hotel Booking Management System
Definition
A Hotel Booking Management System is a platform designed to help customers search, book, and manage hotel reservations. It also facilitates hotel administrators in managing room availability, bookings, and customer details efficiently.
1. Rooms
Purpose: Represents individual rooms within the hotel, including their type, price, and unique identification.

Key Fields:

id (Primary Key): Unique identifier for each room.
room_number: The room number visible to guests.
room_type: Type of room (e.g., Single, Double, Suite).
price: The price of the room per night.

CREATE TABLE rooms (
    id SERIAL PRIMARY KEY,
    room_number INT UNIQUE NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

2. Bookings
Purpose: Connects guests and rooms, storing details about a guest's stay.

Key Fields:

id (Primary Key): Unique identifier for each booking.
guest_Id (Foreign Key): Refers to a guest from the guests table.
room_Id (Foreign Key): Refers to a room from the rooms table.
checkIn_Date: Start date of the booking.
checkOut_Date: End date of the booking.

Relationships:

Many-to-One: Multiple bookings can reference the same guest or room.

CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    guest_Id INT NOT NULL,
    room_Id INT NOT NULL,
    check_In_Date DATE NOT NULL,
    check_Out_Date DATE NOT NULL,
    FOREIGN KEY (guest_Id) REFERENCES guests (id) ON DELETE CASCADE,
    FOREIGN KEY (room_Id) REFERENCES rooms (id) ON DELETE CASCADE
);


3. Guests
Purpose: Stores personal information about the guests.

Key Fields:

id (Primary Key): Unique identifier for each guest.
first_Name: Guest’s first name.
last_Name: Guest’s last name.
email: Guest’s email address (unique).
phone_Number: Guest’s phone number (unique).


CREATE TABLE guests (
    id SERIAL PRIMARY KEY,
    first_Name VARCHAR(50) NOT NULL,
    last_Name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_Number VARCHAR(15) UNIQUE NOT NULL
);

4. Payments
Purpose: Stores payment details for bookings made by guests.

Key Fields:

id (Primary Key): Unique identifier for each payment.
booking_Id (Foreign Key): Refers to a booking in the bookings table.
payment_Amount: Amount paid by the guest.
payment_Date: Date of payment.
Relationships:

Many-to-One: Multiple payments can reference the same booking.


CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    booking_Id INT NOT NULL,
    payment_Amount DECIMAL(10, 2) NOT NULL,
    payment_Date DATE NOT NULL,
    FOREIGN KEY (booking_Id) REFERENCES bookings (id) ON DELETE CASCADE
);

Entity Relationships
Guests ↔ Bookings:

A guest can make multiple bookings, but each booking is linked to one guest (One-to-Many relationship).
Rooms ↔ Bookings:

A room can have multiple bookings over time, but each booking refers to one room (One-to-Many relationship).
Bookings ↔ Payments:

A booking can have multiple payments, but each payment is tied to one booking (One-to-Many relationship).

If a guest or room is deleted, their associated bookings and payments are also deleted automatically due to the ON DELETE CASCADE constraint.

DELETE FROM guests;
ALTER SEQUENCE guests_id_seq RESTART WITH 1;
