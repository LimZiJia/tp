package seedu.address.testutil;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Area;
import seedu.address.model.person.Client;
import seedu.address.model.person.Email;
import seedu.address.model.person.HousekeepingDetails;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Client objects.
 */
public class ClientBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_AREA = "west";
    public static final String DEFAULT_LAST_CLEANING_DATE = "2021-10-10";
    public static final String DEFAULT_PREFERRED_INTERVAL = "1";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Area area;
    private Set<Tag> tags;
    private HousekeepingDetails housekeepingDetails;

    /**
     * Creates a {@code ClientBuilder} with the default details.
     */
    public ClientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        area = new Area(DEFAULT_AREA);
        tags = new HashSet<>();
        housekeepingDetails = new HousekeepingDetails(LocalDate.parse(DEFAULT_LAST_CLEANING_DATE),
                Period.ofMonths(Integer.parseInt(DEFAULT_PREFERRED_INTERVAL)));
    }

    /**
     * Initializes the ClientBuilder with the data of {@code clientToCopy}.
     */
    public ClientBuilder(Client clientToCopy) {
        name = clientToCopy.getName();
        phone = clientToCopy.getPhone();
        email = clientToCopy.getEmail();
        address = clientToCopy.getAddress();
        area = clientToCopy.getArea();
        tags = new HashSet<>(clientToCopy.getTags());
        housekeepingDetails = clientToCopy.getDetails();
    }

    /**
     * Sets the {@code Name} of the {@code Client} that we are building.
     */
    public ClientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Client} that we are building.
     */
    public ClientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Client} that we are building.
     */
    public ClientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Client} that we are building.
     */
    public ClientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Client} that we are building.
     */
    public ClientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Area} of the {@code Client} that we are building.
     */
    public ClientBuilder withArea(String area) {
        this.area = new Area(area);
        return this;
    }

    /**
     * Sets the {@code HousekeepingDetails} of the {@code Client} that we are building.
     */
    public ClientBuilder withHousekeepingDetails(LocalDate lastCleaningDate, Period preferredInterval) {
        this.housekeepingDetails = new HousekeepingDetails(lastCleaningDate, preferredInterval);
        return this;
    }

    /**
     * Returns client with the given data.
     */
    public Client build() {
        return new Client(name, phone, email, address, tags, housekeepingDetails, area);
    }
}
