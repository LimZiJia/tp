package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Client;
import seedu.address.model.person.Housekeeper;
import seedu.address.model.person.HousekeepingDetails;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX = "The client index provided is invalid";
    public static final String MESSAGE_INVALID_HOUSEKEEPER_DISPLAYED_INDEX =
            "The housekeeper index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d person(s) listed!";
    public static final String MESSAGE_CLIENTS_LISTED_OVERVIEW = "%1$d client(s) listed!";
    public static final String MESSAGE_HOUSEKEEPERS_LISTED_OVERVIEW = "%1$d housekeeper(s) listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code client} for display to the user.
     */
    public static String formatClient(Client client) {
        final HousekeepingDetails housekeepingDetails = client.getDetails();
        final String details;
        if (housekeepingDetails == null) {
            details = HousekeepingDetails.NO_DETAILS_PROVIDED;
        } else {
            details = HousekeepingDetails.makeStoredDetailsReadable(housekeepingDetails.toString());
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("\nName: ")
                .append(client.getName())
                .append("; Type: ")
                .append(client.getType())
                .append("; Phone: ")
                .append(client.getPhone())
                .append("; Email: ")
                .append(client.getEmail())
                .append("; Address: ")
                .append(client.getAddress())
                .append("; Details: ")
                .append(details)
                .append("; Area: ")
                .append(client.getArea())
                .append("; Tags: ");
        client.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code housekeeper} for display to the user.
     */
    public static String formatHousekeeper(Housekeeper housekeeper) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nName: ")
                .append(housekeeper.getName())
                .append("; Type: ")
                .append(housekeeper.getType())
                .append("; Phone: ")
                .append(housekeeper.getPhone())
                .append("; Email: ")
                .append(housekeeper.getEmail())
                .append("; Address: ")
                .append(housekeeper.getAddress())
                .append("; Booking List: ")
                .append(housekeeper.getBookingList())
                .append("; Area: ")
                .append(housekeeper.getArea())
                .append("; Tags: ");
        housekeeper.getTags().forEach(builder::append);
        return builder.toString();
    }
}
