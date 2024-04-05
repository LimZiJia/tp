package housekeeping.hub.model.person;

import java.time.LocalDate;
import java.util.Set;

import housekeeping.hub.commons.util.ToStringBuilder;
import housekeeping.hub.model.tag.Tag;


/**
 * Represents a Person in the hub book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client extends Person implements Comparable<Client> {
    /** The housekeeping details of the client. Used to generate call list by predicting next session date */
    private HousekeepingDetails housekeepingDetails;

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param tags
     */
    public Client(Name name, Phone phone, Email email, Address address,
                  Set<Tag> tags, HousekeepingDetails housekeepingDetails, Area area) {
        super(name, phone, email, address, tags, area);
        this.housekeepingDetails = housekeepingDetails;
    }

    /**
     * Returns true if a client has housekeeping details.
     */
    public boolean hasHousekeepingDetails() {
        return !housekeepingDetails.isEmpty();
    }

    public void setHousekeepingDetails(HousekeepingDetails housekeepingDetails) {
        this.housekeepingDetails = housekeepingDetails;
    }

    public LocalDate getNextHousekeepingDate() {
        return this.housekeepingDetails.getNextHousekeepingDate();
    }

    public boolean hasBooking() {
        return this.housekeepingDetails.hasBooking();
    }

    /**
     * Returns the housekeeping details of the client.
     */
    @Override
    public HousekeepingDetails getDetails() {
        return this.housekeepingDetails;
    }

    /**
     * Returns true if both clients have the same identity and data fields.
     * This defines a stronger notion of equality between two clients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Client)) {
            return false;
        }

        Client otherPerson = (Client) other;
        return this.getName().equals(otherPerson.getName())
                && this.getPhone().equals(otherPerson.getPhone())
                && this.getEmail().equals(otherPerson.getEmail())
                && this.getAddress().equals(otherPerson.getAddress())
                && this.getTags().equals(otherPerson.getTags())
                && this.getArea().equals(otherPerson.getArea());
    }

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public int compareTo(Client other) {
        if (this.hasHousekeepingDetails() && other.hasHousekeepingDetails()) {
            return this.housekeepingDetails.compareTo(other.housekeepingDetails);
        } else if (this.hasHousekeepingDetails()) {
            return -1; // this client has housekeeping details but the other does not, so this client is less.
        } else if (other.hasHousekeepingDetails()) {
            return 1; // the other client has housekeeping details but this client does not, so this client is greater.
        } else {
            return 0; // neither client has housekeeping details, so they are considered equal
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", this.getName())
                .add("phone", this.getPhone())
                .add("email", this.getEmail())
                .add("hub", this.getAddress())
                .add("tags", this.getTags())
                .add("housekeepingDetails", this.housekeepingDetails)
                .toString();
    }
}
