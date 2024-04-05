package housekeeping.hub.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import housekeeping.hub.commons.exceptions.IllegalValueException;
import housekeeping.hub.model.person.Address;
import housekeeping.hub.model.person.Booking;
import housekeeping.hub.model.person.BookingList;
import housekeeping.hub.model.person.Area;
import housekeeping.hub.model.person.Email;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.model.person.Name;
import housekeeping.hub.model.person.Phone;
import housekeeping.hub.model.tag.Tag;

public class JsonAdaptedHousekeeper extends JsonAdaptedPerson {
    protected final ArrayList<JsonAdaptedBooking> bookingList;

    /**
     * Constructs a {@code JsonAdaptedHousekeeper} with the given housekeeper details.
     */
    @JsonCreator
    public JsonAdaptedHousekeeper(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("hub") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("area") String area,
                             @JsonProperty("booking list") ArrayList<JsonAdaptedBooking> bookingList) {
        super(name, phone, email, address, tags, area);
        this.bookingList = bookingList;
    }

    /**
     * Converts a given {@code Housekeeper} into this class for Jackson use.
     */
    public JsonAdaptedHousekeeper(Housekeeper source) {
        super(source);
        ArrayList<JsonAdaptedBooking> bookingList = new ArrayList<>();

        for (Booking booking : source.getBookingList().getBookings()) {
            JsonAdaptedBooking jsonAdaptedBooking = new JsonAdaptedBooking(booking);
            bookingList.add(jsonAdaptedBooking);
        }

        this.bookingList = bookingList;
    }

    @Override
    public Housekeeper toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final Area modelArea = new Area(area);

        final ArrayList<Booking> personBookings = new ArrayList<>();
        for (JsonAdaptedBooking booking : bookingList) {
            personBookings.add(booking.toModelType());
        }

        final BookingList modelBookingList = new BookingList(personBookings);

        return new Housekeeper(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelArea, modelBookingList);
    }
}
