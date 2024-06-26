package housekeeping.hub.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import housekeeping.hub.logic.parser.ParserUtil;
import housekeeping.hub.model.AddressBook;
import housekeeping.hub.model.ReadOnlyAddressBook;
import housekeeping.hub.model.person.Address;
import housekeeping.hub.model.person.Area;
import housekeeping.hub.model.person.BookingList;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Email;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.model.person.HousekeepingDetails;
import housekeeping.hub.model.person.Name;
import housekeeping.hub.model.person.Phone;
import housekeeping.hub.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Client[] getSampleClients() {
        return new Client[] {
            new Client(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), getTagSet("friends"),
                    getHousekeepingDetails(Optional.of("2024-01-30 2 months")), new Area("east")),
            new Client(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"),
                    getHousekeepingDetails(Optional.of("2024-02-28 2 weeks")), new Area("east")),
            new Client(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), getTagSet("neighbours"),
                    getHousekeepingDetails(Optional.of("2024-03-03 1 years")), new Area("east")),
            new Client(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("family"),
                    getHousekeepingDetails(Optional.of("2024-03-11 39 days")), new Area("east")),
            new Client(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), getTagSet("classmates"),
                    getHousekeepingDetails(Optional.of("2024-04-01 6 months")), new Area("east")),
            new Client(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), getTagSet("colleagues"),
                    getHousekeepingDetails(Optional.empty()), new Area("east"))
        };
    }

    public static Housekeeper[] getSampleHousekeepers() {
        return new Housekeeper[] {
            new Housekeeper(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@example.com"),
                    new Address("Blk 123 Woodlands Street 45, #05-12"),
                    getTagSet("cleaning"), new Area("east"), new BookingList()),
            new Housekeeper(new Name("Jane Smith"), new Phone("98765432"), new Email("janesmith@example.com"),
                    new Address("Blk 456 Jurong East Avenue 89, #08-15"),
                    getTagSet("part-time"), new Area("east"), new BookingList()),
            new Housekeeper(new Name("Michael Tan"), new Phone("87654321"), new Email("michaeltan@example.com"),
                    new Address("Blk 789 Bukit Timah Road, #02-34"),
                    getTagSet("full-time"), new Area("east"), new BookingList()),
            new Housekeeper(new Name("Emily Lee"), new Phone("98761234"), new Email("emilylee@example.com"),
                    new Address("Blk 234 Sengkang Street 12, #07-23"),
                    getTagSet("pet-friendly"), new Area("east"), new BookingList()),
            new Housekeeper(new Name("Daniel Lim"), new Phone("87651234"), new Email("daniellim@example.com"),
                    new Address("Blk 345 Yishun Avenue 67, #10-45"),
                    getTagSet("experienced"), new Area("east"), new BookingList()),
            new Housekeeper(new Name("Samantha Tan"), new Phone("76543210"), new Email("samanthatan@example.com"),
                    new Address("Blk 678 Clementi Road, #03-21"),
                    getTagSet("trustworthy"), new Area("east"), new BookingList())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Client sampleClient : getSampleClients()) {
            sampleAb.addClient(sampleClient);
        }
        for (Housekeeper sampleHousekeeper : getSampleHousekeepers()) {
            sampleAb.addHousekeeper(sampleHousekeeper);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static HousekeepingDetails getHousekeepingDetails(Optional<String> details) {
        try {
            return ParserUtil.parseHousekeepingDetails(details);
        } catch (Exception e) {
            return null;
        }
    }

}
