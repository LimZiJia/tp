package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.HousekeepingDetails;

/**
 * Jackson-friendly version of {@link HousekeepingDetails}.
 */
public class JsonAdaptedDetails {

    private final String details;

    /**
     * Constructs a {@code JsonAdaptedDetails} with the given {@code details}.
     */
    @JsonCreator
    public JsonAdaptedDetails(String details) {
        this.details = details;
    }

    /**
     * Converts a given {@code HousekeepingDetails} into this class for Jackson use.
     */
    public JsonAdaptedDetails(HousekeepingDetails source) {
        this.details = source.toString();
    }

    @JsonValue
    public String getDetails() {
        return details;
    }

    public HousekeepingDetails toModelType() throws IllegalValueException {
        if (!HousekeepingDetails.isValidHouseKppingDetailsStorage(details)) {
            throw new IllegalValueException(HousekeepingDetails.MESSAGE_CONSTRAINTS);
        }
        return new HousekeepingDetails(details);
    }
}
