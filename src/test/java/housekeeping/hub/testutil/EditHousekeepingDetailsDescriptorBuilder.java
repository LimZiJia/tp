package housekeeping.hub.testutil;

import java.time.LocalDate;
import java.time.Period;

import housekeeping.hub.logic.commands.EditHousekeepingDetailsCommand.EditHousekeepingDetailsDescriptor;
import housekeeping.hub.model.person.Booking;
import housekeeping.hub.model.person.Person;

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
