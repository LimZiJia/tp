package housekeeping.hub.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import housekeeping.hub.logic.commands.EditCommand.EditPersonDescriptor;
import housekeeping.hub.model.person.Address;
import housekeeping.hub.model.person.Area;
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
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
        descriptor.setArea(person.getArea());
        if (person.isClient()) {
            Client client = (Client) person;
            descriptor.setDetails(client.getDetails());
        } else {
            Housekeeper housekeeper = (Housekeeper) person;
        }
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptorBuilder withArea(String area) {
        descriptor.setArea(new Area(area));
        return this;
    }

    public EditPersonDescriptorBuilder withBookingList(BookingList bookingList) {
        descriptor.setBookingList(bookingList);
        return this;
    }

    public EditPersonDescriptorBuilder withDetails(HousekeepingDetails details) {
        descriptor.setDetails(details);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
