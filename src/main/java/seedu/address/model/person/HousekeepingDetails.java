package seedu.address.model.person;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the housekeeping details of a client.
 */
public class HousekeepingDetails implements Comparable<HousekeepingDetails> {
    private static final Pattern USER_FORMAT = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d+ (days|weeks|months|years)");
    private static final Pattern STORAGE_FORMAT =
            Pattern.compile("(null|(\\d{4}-\\d{2}-\\d{2})\\s" // Last housekeeping date
            + "(P(?!$)(\\d+Y)?(\\d+M)?(\\d+W)?(\\d+D)?)\\s" // Preferred interval
            + "(null|\\d{4}-\\d{2}-\\d{2}\\s+(am|pm))\\s" // Booking date (can be null)
            + "(P(?!$)(\\d+Y)?(\\d+M)?(\\d+W)?(\\d+D)?))"); // Deferment
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
    private Booking booking;
    /** The period to delay calling the client */
    private Period deferment;

    /** User must add as "yyyy-mm-dd n (days|weeks|months|years)" */
    public static boolean isValidHousekeepingDetailsUser(String test) {
        Matcher userInputMatcher = USER_FORMAT.matcher(test);
        return userInputMatcher.matches();
    }

    /** String can be stored as "null" or "yyyy-mm-dd P?Y?M?W?D? (null|yyyy-mm-dd (am|pm)) P?Y?M?W?D?" */
    public static boolean isValidHousekeepingDetailsStorage(String test) {
        Matcher storageMatcher = STORAGE_FORMAT.matcher(test);
        return storageMatcher.matches();
    }

    /**
     * Converts the stored string representation of the housekeeping details to a readable format horizontally.
     * @param details The stored string representation of the housekeeping details.
     * @return Readable string representation of the housekeeping details.
     */
    public static String makeStoredDetailsReadableHorizontally(String details) {
        if (details.equals("null")) {
            return NO_DETAILS_PROVIDED;
        }
        else if (!isValidHousekeepingDetailsStorage(details)) {
            return "Invalid housekeeping details format";
        }

        // Converting Period of preferred interval to a readable format
        String[] s = details.split(" ");
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

        return String.format("Last housekeeping: %s, Preferred interval: %s %s, Booking date: %s",
                s[0], num, unitString, booking);
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
                                               // s[2] = booking, s[3] = deferment
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

        return String.format("Last housekeeping: %s\nPreferred interval: %s %s\nBooking date: %s",
                s[0], num, unitString, booking);
    }

    /**
     * Creates a HousekeepingDetails object with no details provided.
     */
    public HousekeepingDetails() {
        this.lastHousekeepingDate = null;
        this.preferredInterval = null;
        this.booking = null;
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
        this.booking = null;
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
            this.booking = null;
            this.deferment = null;
        } else {
            // Using groups to extract details
            Matcher storageMatcher = STORAGE_FORMAT.matcher(details);
            storageMatcher.matches();
            this.lastHousekeepingDate = LocalDate.parse(storageMatcher.group(2));
            this.preferredInterval = Period.parse(storageMatcher.group(3));
            this.booking = storageMatcher.group(8).equals("null") ? null : new Booking(storageMatcher.group(8));
            this.deferment = Period.parse(storageMatcher.group(10));
        }
    }

    /** Checks if the housekeeping details is empty */
    public boolean isEmpty() {
        return this.equals(empty);
    }

    public boolean hasBooking() {
        return booking != null;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void deleteBooking() {
        this.booking = null;
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
                && (booking == otherDetails.booking
                || booking.equals(otherDetails.booking))
                && (deferment == otherDetails.deferment
                || deferment.equals(otherDetails.deferment)));
        }

    @Override
    public String toString() {
        if (this.equals(empty)) {
            return "null";
        }
        return lastHousekeepingDate + " " + preferredInterval + " " + booking + " " + deferment;
    }
 }