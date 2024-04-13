package housekeeping.hub.testutil;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import housekeeping.hub.logic.commands.EditHousekeepingDetailsCommand.EditHousekeepingDetailsDescriptor;
import housekeeping.hub.model.person.Address;
import housekeeping.hub.model.person.Area;
import housekeeping.hub.model.person.Booking;
import housekeeping.hub.model.person.BookingList;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Email;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.model.person.HousekeepingDetails;
import housekeeping.hub.model.person.Name;
import housekeeping.hub.model.person.Person;
import housekeeping.hub.model.person.Phone;
import housekeeping.hub.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditHousekeepingDetailsDescriptorBuilder {

    private EditHousekeepingDetailsDescriptor descriptor;

    public EditHousekeepingDetailsDescriptorBuilder() {
        descriptor = new EditHousekeepingDetailsDescriptor();
    }

    public EditHousekeepingDetailsDescriptorBuilder(EditHousekeepingDetailsDescriptor descriptor) {
        this.descriptor = new EditHousekeepingDetailsDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditHousekeepingDetailsDescriptorBuilder(Person person) {
        descriptor = new EditHousekeepingDetailsDescriptor();
        descriptor.setLastHousekeepingDate(person.getDetails().getLastHousekeepingDate());
        descriptor.setPreferredInterval(person.getDetails().getPreferredInterval());
        descriptor.setDeferment(person.getDetails().getDeferment());
        descriptor.setBooking(person.getDetails().getBooking());
    }

    /**
     * Sets the {@code Last Housekeeping Date} of the {@code EditHousekeepingDetailsDescriptor} that we are building.
     */
    public EditHousekeepingDetailsDescriptorBuilder withLastHousekeepingDate(LocalDate lHD) {
        descriptor.setLastHousekeepingDate(lHD);
        return this;
    }

    /**
     * Sets the {@code Preferred Interval} of the {@code EditHousekeepingDetailsDescriptor} that we are building.
     */
    public EditHousekeepingDetailsDescriptorBuilder withPreferredInterval(Period pI) {
        descriptor.setPreferredInterval(pI);
        return this;
    }

    /**
     * Sets the {@code Deferment} of the {@code EditHousekeepingDetailsDescriptor} that we are building.
     */
    public EditHousekeepingDetailsDescriptorBuilder withDeferment(Period def) {
        descriptor.setDeferment(def);
        return this;
    }

    /**
     * Sets the {@code Booking} of the {@code EditHousekeepingDetailsDescriptor} that we are building.
     */
    public EditHousekeepingDetailsDescriptorBuilder withBookingDate(Booking booking) {
        descriptor.setBooking(booking);
        return this;
    }


    public EditHousekeepingDetailsDescriptor build() {
        return descriptor;
    }
}
