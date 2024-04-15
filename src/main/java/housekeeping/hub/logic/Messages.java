package housekeeping.hub.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import housekeeping.hub.logic.parser.Prefix;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.model.person.HousekeepingDetails;
import housekeeping.hub.model.tag.Tag;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_AVAILABLE_HOUSEKEEPERS_LISTED_OVERVIEW =
            "%1$d housekeepers available at [%2$s, %3$s] listed!";
    public static final String MESSAGE_NO_AVAILABLE_HOUSEKEEPERS_LISTED_OVERVIEW =
            "No housekeepers available at [%1$s, %2$s]!";
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
            details = HousekeepingDetails.makeStoredDetailsReadableWithDeferment(housekeepingDetails.toString());
        }

        Set<Tag> tags = client.getTags();

        final StringBuilder builder = new StringBuilder();
        builder.append("\nName: ")
                .append(client.getName())
                .append("; Phone: ")
                .append(client.getPhone())
                .append("; Email: ")
                .append(client.getEmail())
                .append("; Address: ")
                .append(client.getAddress())
                .append("; Area: ")
                .append(client.getArea())
                .append("; Tags: ");
        if (tags.isEmpty()) {
            builder.append("No tags");
        } else {
            tags.forEach(builder::append);
        }
        builder.append("; Details: ")
                .append(details);
        return builder.toString();
    }

    /**
     * Formats the {@code housekeeper} for display to the user.
     */
    public static String formatHousekeeper(Housekeeper housekeeper) {
        final StringBuilder builder = new StringBuilder();
        Set<Tag> tags = housekeeper.getTags();

        builder.append("\nName: ")
                .append(housekeeper.getName())
                .append("; Phone: ")
                .append(housekeeper.getPhone())
                .append("; Email: ")
                .append(housekeeper.getEmail())
                .append("; Address: ")
                .append(housekeeper.getAddress())
                .append("; Area: ")
                .append(housekeeper.getArea())
                .append("; Tags: ");
        if (tags.isEmpty()) {
            builder.append("No tags");
        } else {
            tags.forEach(builder::append);
        }
        builder.append("; Booking List: ")
                .append(housekeeper.getBookingList());
        return builder.toString();
    }
}
