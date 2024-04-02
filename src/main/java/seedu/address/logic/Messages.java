package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.HousekeepingDetails;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_AVAILABLE_HOUSEKEEPERS_LISTED_OVERVIEW =
            "%1$d housekeepers available at [%2$s, %3$s] listed!";
    public static final String MESSAGE_NO_AVAILABLE_HOUSEKEEPERS_LISTED_OVERVIEW =
            "No housekeepers available at [%1$s, %2$s]!";
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
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final HousekeepingDetails housekeepingDetails = person.getDetails();
        final String details;
        if (housekeepingDetails == null) {
            details = HousekeepingDetails.NO_DETAILS_PROVIDED;
        } else {
            details = HousekeepingDetails.makeStoredDetailsReadable(housekeepingDetails.toString());
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("\nName: ")
                .append(person.getName())
                .append("; Type: ")
                .append(person.getType())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Details: ")
                .append(details)
                .append("; Area: ")
                .append(person.getArea())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
