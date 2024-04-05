package housekeeping.hub.testutil;

import java.util.HashSet;
import java.util.Set;

import housekeeping.hub.model.person.Address;
import housekeeping.hub.model.person.Area;
import housekeeping.hub.model.person.BookingList;
import housekeeping.hub.model.person.Email;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.model.person.Name;
import housekeeping.hub.model.person.Phone;
import housekeeping.hub.model.tag.Tag;
import housekeeping.hub.model.util.SampleDataUtil;

/**
 * A utility class to help with building Housekeeper objects.
 */
public class HousekeeperBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_AREA = "west";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Area area;
    private Set<Tag> tags;
    private BookingList bookingList;

    /**
     * Creates a {@code HousekeeperBuilder} with the default details.
     */
    public HousekeeperBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        area = new Area(DEFAULT_AREA);
        tags = new HashSet<>();
        bookingList = new BookingList();
    }

    /**
     * Initializes the HousekeeperBuilder with the data of {@code housekeeperToCopy}.
     */
    public HousekeeperBuilder(Housekeeper housekeeperToCopy) {
        name = housekeeperToCopy.getName();
        phone = housekeeperToCopy.getPhone();
        email = housekeeperToCopy.getEmail();
        address = housekeeperToCopy.getAddress();
        area = housekeeperToCopy.getArea();
        tags = new HashSet<>(housekeeperToCopy.getTags());
        bookingList = new BookingList();
    }

    /**
     * Sets the {@code Name} of the {@code Housekeeper} that we are building.
     */
    public HousekeeperBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Housekeeper} that we are building.
     */
    public HousekeeperBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Housekeeper} that we are building.
     */
    public HousekeeperBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Housekeeper} that we are building.
     */
    public HousekeeperBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Housekeeper} that we are building.
     */
    public HousekeeperBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Area} of the {@code Housekeeper} that we are building.
     */
    public HousekeeperBuilder withArea(String area) {
        this.area = new Area(area);
        return this;
    }

    /**
     * Sets the {@code BookingList} of the {@code Housekeeper} that we are building.
     */
    public HousekeeperBuilder withBookingList(BookingList bookingList) {
        this.bookingList = bookingList;
        return this;
    }

    /**
     * Returns housekeeper with the given data.
     */
    public Housekeeper build() {
        return new Housekeeper(name, phone, email, address, tags, area, bookingList);
    }
}
