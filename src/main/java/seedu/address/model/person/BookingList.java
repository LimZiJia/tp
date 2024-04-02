package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Encapsulates the behaviour of a list of Bookings.
 */
public class BookingList {
    public static final String MESSAGE_DUPLICATE = "is unavailable at the specified date and time."
            + " Please input a different date and time.";
    public static final String MESSAGE_INVALID_DELETE = "The booking index provided is invalid.";
    private static final String MESSAGE_SUCCESS_ADD = "This booking has successfully been added: %1$s.";
    private static final String MESSAGE_SUCCESS_DELETE = "This booking has successfully been deleted: %1$s.";
    private static final String MESSAGE_SUCCESS_LIST = "Bookings:%1$s";

    private ArrayList<Booking> bookings;

    /**
     * Constructs an BookingList object with an empty list of bookings.
     */
    public BookingList() {
        bookings = new ArrayList<>();
    }

    /**
     * Constructs a BookingList object with another Bookinglist.
     *
     * @param bookingList other BookingList whose list to copy
     */
    public BookingList(BookingList bookingList) {
        this.bookings = bookingList.getBookings();
    }

    /**
     * Constructs a BookingList object with an ArrayList<Booking>.
     *
     * @param bookingList ArrayList<Booking> containing list of bookings
     */
    public BookingList(ArrayList<Booking> bookingList) {
        this.bookings = bookingList;
    }

    /**
     * Creates a new booking with the specified booked date and time.
     *
     * @param bookedDateAndTime of the booking to be created
     * @return Booking object
     */
    public Booking createBooking(String bookedDateAndTime) {
        Booking booking = new Booking(bookedDateAndTime);
        return booking;
    }

    /**
     * Checks if a booking with the specified booked date and time already exists in the booking list.
     *
     * @param bookedDateAndTime of booking to be checked
     * @return True if there is a duplicate, false otherwise
     */
    public boolean hasDuplicate(String bookedDateAndTime) {
        Booking bookingToCheck = new Booking(bookedDateAndTime);
        for (Booking booking : bookings) {
            if (booking.equals(bookingToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new booking with the specified date and time to the booking list.
     *
     * @param bookedDateAndTime of new booking to be added
     * @return String containing message for successfully adding a booking
     */
    public String addBooking(String bookedDateAndTime) {
        Booking booking = createBooking(bookedDateAndTime);
        bookings.add(booking);
        return String.format(MESSAGE_SUCCESS_ADD, booking.toString());
    }

    /**
     * Checks if provided index is valid.
     *
     * @param index to be checked
     * @return True if valid, false otherwise
     */
    public boolean isValidDeleteIndex(int index) {
        if (index <= bookings.size()) {
            return true;
        }
        return false;
    }

    /**
     * Deletes the booking at the specified index from the booking list.
     *
     * @param index of booking to be deleted
     * @return String containing message for successfully deleting the booking
     */
    public String deleteBooking(int index) {
        int targetIndex = index - 1;
        Booking targetBooking = bookings.get(targetIndex);
        bookings.remove(targetIndex);
        return String.format(MESSAGE_SUCCESS_DELETE, targetBooking.toString());
    }

    /**
     * Lists all bookings in the booking list.
     *
     * @return String containing message of the list of bookings
     */
    public String listBooking() {
        Collections.sort(bookings);
        String listMessage = "";
        for (int i = 0; i < bookings.size(); i++) {
            String bookingString = (i + 1) + ". " + bookings.get(i).toString();
            listMessage += "\n" + bookingString;
        }
        return String.format(MESSAGE_SUCCESS_LIST, listMessage);
    }

    public ArrayList<Booking> getBookings() {
        if (bookings == null) {
            return new ArrayList<>();
        }
        return bookings;
    }

    @Override
    public String toString() {
        if (bookings.isEmpty()) {
            return "No bookings available";
        }
        return bookings.toString();
    }
}
