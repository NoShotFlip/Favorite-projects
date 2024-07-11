package Hotel_Toad;

import org.junit.Test;
import org.junit.Before;
import java.sql.SQLException;
import java.text.ParseException;

import static Hotel_Toad.Hotel.rooms;
import static org.junit.Assert.*;

public class HotelTest {

    private Hotel hotel;

    @Before
    public void setUp() throws Exception {

        Room r1 = new Room(1 ,"Single" , true);
        Room r2 = new Room(2 ,"Double" , true);
        Room r3 = new Room(3 ,"Triple" , true);
        Room r4 = new Room(4 ,"Single" , true);
        Room r5 = new Room(5 ,"Double" , true);
        Room r6 = new Room(6 ,"Triple" , true);
        Room r7 = new Room(7 ,"Single" , true);
        Room r8 = new Room(8 ,"Double" , true);
        Room r9 = new Room(9 ,"Triple" , true);
        Room r10 = new Room(10 ,"Suite" , true);

        Room[] rooms = {r1, r2, r3, r4, r5, r6, r7, r8, r9, r10};
        hotel = new Hotel(rooms);
    }

    @Test
    public void testReserveRoomSuccess() throws SQLException, ClassNotFoundException, ParseException {
        Guest guest = new Guest("Joe", "Mama", "joe@mama.com");
        Reservation reservation = new Reservation(2, 5, "08/16/2023", "09/16/2023", "Suite", "yo");
        Payment payment = new Payment();
        payment.calculateBill(reservation);
        String results = "Room Reservation Successful!" +
                "\nGuest Name: " + "Joe" + " " + "Mama" + " "
                + "\nGuest Email: " + "joe@mama.com"  +"\nRoom Type: " + "Suite" + "\nRoom number: "
                + "10" + "\nCheck-in: " + "08/16/2023" + "\nCheck-out: " + "09/16/2023" + "\nRoom Price: $962,550.00"
                + "\nDon't forget your room number if you want to cancel your reservation!";
        assertEquals(hotel.reserveRoom(guest, "Suite", "08/16/2023", "09/16/2023", "joe@mama.com", "\nRoom Price: $962,550.00"), results);
    }

    @Test
    public void testReserveRoomFail() throws SQLException, ClassNotFoundException, ParseException {
        Guest guest = new Guest("Joe", "Mama", "joe@mama.com");
        Reservation reservation = new Reservation(2, 5, "08/16/2023", "09/16/2023", "Suite", "yo");
        Payment payment = new Payment();
        payment.calculateBill(reservation);
        rooms[9].availability = false;
        String results = hotel.reserveRoom(guest, "Suite", "08/16/2023", "09/16/2023", "joe@mama.com", "\nRoom Price: $962,550.00");
        assertNull(results);
    }

    @Test
    public void testCancelRoomSuccess() throws SQLException, ClassNotFoundException {
        int roomNum = 0;
        Guest guest = new Guest("Joe", "Mama", "joe@mama.com");
        Reservation reservation = new Reservation(2, 5, "08/16/2023", "09/16/2023", "Suite", "yo");
        String results = hotel.cancelRoom(roomNum);
        assertEquals("Error: No Matching Reservation Found.", results);
    }

    @Test
    public void testCancelRoomFail() throws SQLException, ClassNotFoundException {
        rooms[0].availability = false;
        int roomNum = 1;
        Guest guest = new Guest("Joe", "Mama", "joe@mama.com");
        Reservation reservation = new Reservation(2, 5, "08/16/2023", "09/16/2023", "Suite", "yo");
        String results = hotel.cancelRoom(roomNum);
        assertEquals("Reservation Canceled!", results);
    }

}
