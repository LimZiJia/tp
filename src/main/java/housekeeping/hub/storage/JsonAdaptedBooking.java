package housekeeping.hub.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import housekeeping.hub.commons.exceptions.IllegalValueException;
import housekeeping.hub.model.person.Booking;

/**
 * Jackson-friendly version of {@link Booking}.
 */
public class JsonAdaptedBooking {
    private final String booking;

    /**
     * Constructs a {@code JsonAdaptedBooking} with the given {@code booking}.
     */
    @JsonCreator
    public JsonAdaptedBooking(String booking) {
        this.booking = booking;
    }

    /**
     * Converts a given {@code Booking} into this class for Jackson use.
     */
    @JsonCreator
    public JsonAdaptedBooking(Booking source) {
        booking = source.toString();
    }

    @JsonValue
    public String getBooking() {
        return booking;
    }

    /**
     * Converts this Jackson-friendly adapted booking object into the model's {@code Booking} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted booking.
     */
    public Booking toModelType() throws IllegalValueException {
        if (!Booking.isValidBookedDateAndTime(booking)) {
            throw new IllegalValueException(Booking.MESSAGE_CONSTRAINTS);
        }
        return new Booking(booking);
    }
}
