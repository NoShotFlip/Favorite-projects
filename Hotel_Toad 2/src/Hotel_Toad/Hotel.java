package Hotel_Toad;

import java.sql.SQLException;

/**
 * Represents a hotel entity with functionalities encompassing room reservations and cancellations.
 * @author Toad's Taskforce
 * @version 1.0
 * <p>
 * Date of Creation: 28 July 2023
 * </p>
 *
 * <p>
 * This class not only manages the in-memory representation of the hotel but also interfaces with a database
 * for persistent storage of reservation details.
 * </p>
 */
public class Hotel {

    /** An array representing all the rooms in the hotel. */
    public static Room[] rooms;

    /**
     * Constructs a new Hotel with the given rooms.
     *
     * @param rooms the array of rooms that belong to this hotel.
     */
    public Hotel(Room[] rooms){
        this.rooms = rooms;
    }

    /**
     * Processes a room reservation request for a guest. If successful, details of the reservation are returned.
     * Otherwise, a null value indicates the absence of available rooms of the desired type.
     *
     * @param guest    the guest making the reservation.
     * @param roomType the type of room desired by the guest.
     * @param checkIn  the check-in date.
     * @param checkOut the check-out date.
     * @param email    the email of the guest.
     * @param bill     total billing details.
     *
     * @return details of the reserved room or null if the desired room type is unavailable.
     *
     * @throws ClassNotFoundException if there's an issue loading a database class.
     * @throws SQLException if there's a database access error.
     */
    public static String reserveRoom(Guest guest, String roomType, String checkIn, String checkOut, String email, String bill) throws ClassNotFoundException, SQLException{
        for (int i = 0; i < rooms.length; i++){
            if (rooms[i].getRoomType().equalsIgnoreCase(roomType) && rooms[i].isAvailable()){
                //rooms[i].availability = false;
                return "Room Reservation Successful!" +
                        "\nGuest Name: " + guest.getFirstName() + " " + guest.getLastName() + " "
                        + "\nGuest Email: " + guest.getEmail()  +"\nRoom Type: " + roomType + "\nRoom number: "
                        + rooms[i].getRoomNumber() + "\nCheck-in: " + checkIn + "\nCheck-out: " + checkOut + bill
                        + "\nDon't forget your room number if you want to cancel your reservation!";
            }
        }
        return null;
    }

    /**
     * Handles room reservation and simultaneously updates the database to reflect these changes.
     *
     * @param guest    details of the guest requesting the reservation.
     * @param roomType the kind of room the guest desires.
     * @param checkIn  intended check-in date.
     * @param checkOut intended check-out date.
     * @param email    contact email of the guest.
     *
     * @throws ClassNotFoundException if there's an issue connecting with the database driver.
     * @throws SQLException if there's an issue accessing the database.
     */
    public static void reserveRoom1(Guest guest, String roomType, String checkIn, String checkOut, String email) throws ClassNotFoundException, SQLException{
        for (int i = 0; i < rooms.length; i++){
            if (rooms[i].getRoomType().equalsIgnoreCase(roomType) && rooms[i].isAvailable()){
                rooms[i].availability = false;
                
                int roomNumber = rooms[i].getRoomNumber();
                //Calls addtoCustomerDetails from DataBase class to store the information
                Database.addtoCustomerDetails(guest.getFirstName(), guest.getLastName(), email, roomNumber);
                //Calls addBooking from DataBase class to store the information
                Database.addBooking(roomNumber,checkIn,checkOut);
                //Calls updateRoomAvailability from DataBase class to store the information
                Database.updateRoomAvailability(roomNumber);
                return;
            }
        }
        
    }

    /**
     * Cancels an existing room reservation and updates the database to reflect the room's availability.
     *
     * @param roomNumber the specific number of the room whose reservation needs cancellation.
     *
     * @return a message indicating whether the cancellation was successful or not.
     *
     * @throws ClassNotFoundException if there's an issue connecting with the database driver.
     * @throws SQLException if there's an issue accessing the database.
     */
    public static String cancelRoom(int roomNumber) throws ClassNotFoundException, SQLException{
        String message;
        for (int i = 0; i < rooms.length; i++){
            if (rooms[i].getRoomNumber() == roomNumber && !rooms[i].isAvailable()) {
                // Mark room as available;
                rooms[i].availability = true;

                // Update database to reflect cancellation
                //Calls deleteCustomerDetails from DataBase class to delete in the Database
                Database.deleteCustomerDetails(roomNumber);
                //Calls nowAvailabe from DataBase class to Updates in the Database
                Database.nowAvailable(roomNumber);
                //Calls deleteBooking from DataBase class to delete in the Database
                Database.deleteBooking(roomNumber);
                message = "Reservation Canceled!";
                return message;
            }
        }
        message = "Error: No Matching Reservation Found.";
        return message;
    }

}
