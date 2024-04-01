package seedu.address.model.person;

import java.time.LocalDate;
import java.time.Period;

/**
 * Represents the housekeeping details of a client.
 */
public class HousekeepingDetails implements Comparable<HousekeepingDetails> {
    public static final String MESSAGE_CONSTRAINTS =
            "Housekeeping details should be in the format: yyyy-mm-dd n (days|weeks|months|years) "
                    + "where n is an integer quantity of days, weeks, months or years.";
    public static final String MESSAGE_CONSTRAINTS_STORAGE =
            "Housekeeping details should be in the format: 'yyyy-mm-dd P?Y?M?W?D? yyyy-mm-dd P?Y?M?W?D?' or 'null'"
                    + "where P is the period designator, Y is years, M is months, W is weeks D is days. "
                    + "YMWD must be in that order. All fields are optional."
                    + "The second date is the booking date and it can be null";
    public static final String NO_DETAILS_PROVIDED = "No housekeeping details provided";

    public static final HousekeepingDetails empty = new HousekeepingDetails();

    /** The last date the housekeeping was done. */
    private LocalDate lastHousekeepingDate;
    /** Client's preferred time between housekeeping services. */
    private Period preferredInterval;
    /** The date the housekeeping is booked or null if there is no booking */
    private LocalDate bookingDate;
    /** The period to delay calling the client */
    private Period deferment;

    /** User must add as "yyyy-mm-dd n (days|weeks|months|years)" */
    public static boolean isValidHousekeepingDetailsUser(String test) {
        return test.matches("\\d{4}-\\d{2}-\\d{2} \\d+ (days|weeks|months|years)");
    }

    /** String can be stored as "null" or "yyyy-mm-dd P?Y?M?W?D? yyyy-mm-dd P?Y?M?W?D?" */
    public static boolean isValidHousekeepingDetailsStorage(String test) {
        String[] s = test.split(" ");
        return test.equals("null")
                || s.length == 4
                && (s[0].matches("\\d{4}-\\d{2}-\\d{2}")
                && s[1].matches("P(?!$)(\\d+Y)?(\\d+M)?(\\d+W)?(\\d+D)?")
                && s[2].equals("null")) || (s[2].matches("\\d{4}-\\d{2}-\\d{2}"))
                && s[3].matches("P(?!$)(\\d+Y)?(\\d+M)?(\\d+W)?(\\d+D)?");
    }

    /**
     *  Converts the stored string representation of the housekeeping details to a readable format.
     *
     * @param details The stored string representation of the housekeeping details.
     * @return        Readable string representation of the housekeeping details.
     */
    public static String makeStoredDetailsReadable(String details) {
        if (details.equals("null")) {
            return NO_DETAILS_PROVIDED;
        }
        else if (!isValidHousekeepingDetailsStorage(details)) {
            return "Invalid housekeeping details format";
        }

        // Converting Period of preferred interval to a readable format
        String[] s = details.split(" "); // If valid s[0] = lastHousekeepingDate, s[1] = preferredInterval,
                                               // s[2] = bookingDate, s[3] = deferment
        String num = s[1].substring(1, s[1].length() - 1);
        String unit = s[1].substring(s[1].length() - 1);
        String unitString;
        switch (unit) {
        case "Y":
            unitString = "years";
            break;
        case "M":
            unitString = "months";
            break;
        case "W":
            unitString = "weeks";
            break;
        case "D":
            unitString = "days";
            break;
        default:
            unitString = "Invalid unit";
        }

        // Makes null booking readable
        String booking = s[2].equals("null") ? "No booking" : s[2];

        return String.format("Last housekeeping date: %s\nPreferred interval: %s %s\nBooking date: %s",
                s[0], num, unitString, booking);
    }

    /**
     * Creates a HousekeepingDetails object with no details provided.
     */
    public HousekeepingDetails() {
        this.lastHousekeepingDate = null;
        this.preferredInterval = null;
        this.bookingDate = null;
        this.deferment = null;
    }

    /**
     * Creates a HousekeepingDetails object.
     * @param lastHousekeepingDate
     * @param preferredInterval
     */
    public HousekeepingDetails(LocalDate lastHousekeepingDate, Period preferredInterval) {
        this.lastHousekeepingDate = lastHousekeepingDate;
        this.preferredInterval = preferredInterval;
        this.bookingDate = null;
        this.deferment = Period.ZERO;
    }

    /**
     * Creates a HousekeepingDetails object using a string representation of the housekeeping details used for storage.
     * @param details A string representation of the housekeeping details in the format: "null" or
     *                yyyy-mm-dd P?Y?M?W?D? yyyy-mm-dd P?Y?M?W?D?
     */
    public HousekeepingDetails(String details) {
        if (details.equals("null")) {
            this.lastHousekeepingDate = null;
            this.preferredInterval = null;
            this.bookingDate = null;
            this.deferment = null;
        } else {
            String[] s = details.split(" ");
            this.lastHousekeepingDate = LocalDate.parse(s[0]);
            this.preferredInterval = Period.parse(s[1]);
            this.bookingDate = s[2].equals("null") ? null : LocalDate.parse(s[2]);
            this.deferment = Period.parse(s[3]);
        }
    }

    /** Checks if the housekeeping details is empty */
    public boolean isEmpty() {
        return this.equals(empty);
    }

    public boolean hasBooking() {
        return bookingDate != null;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void deleteBookingDate() {
        this.bookingDate = null;
    }

    public void addDeferment(Period deferment) {
        this.deferment = this.deferment.plus(deferment);
    }

    public LocalDate getNextHousekeepingDate() {
        if (lastHousekeepingDate == null || preferredInterval == null || deferment == null) {
            return LocalDate.MAX; // If not enough details available, the client will not be called
        }
        return lastHousekeepingDate.plus(preferredInterval).plus(deferment);
    }

    @Override
    public int compareTo(HousekeepingDetails other) {
        return this.getNextHousekeepingDate().compareTo(other.getNextHousekeepingDate());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof HousekeepingDetails)) {
            return false;
        }
        HousekeepingDetails otherDetails = (HousekeepingDetails) other;

        // First predicate of each && is for null values, second predicate is for non-null values
        return ((lastHousekeepingDate == otherDetails.lastHousekeepingDate
                || lastHousekeepingDate.equals(otherDetails.lastHousekeepingDate))
                && (preferredInterval == otherDetails.preferredInterval
                ||preferredInterval.equals(otherDetails.preferredInterval))
                && (bookingDate == otherDetails.bookingDate
                || bookingDate.equals(otherDetails.bookingDate))
                && (deferment == otherDetails.deferment
                || deferment.equals(otherDetails.deferment)));
        }

    @Override
    public String toString() {
        if (this.equals(empty)) {
            return "null";
        }
        return lastHousekeepingDate + " " + preferredInterval + " " + bookingDate + " " + deferment;
    }
 }