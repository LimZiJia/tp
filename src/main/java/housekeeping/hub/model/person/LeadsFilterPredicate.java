package housekeeping.hub.model.person;

import java.time.LocalDate;
import java.util.function.Predicate;

import housekeeping.hub.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class LeadsFilterPredicate implements Predicate<Client> {

    public LeadsFilterPredicate() {}

    /**
     * Tests that a {@code Client}'s predicted next housekeeping date is <= current date,
     * and they do not have a booking yet. Also, clients without housekeeping details should be tested false.
     */
    @Override
    public boolean test(Client client) {
        LocalDate currentDate = LocalDate.now();
        return !client.hasBooking()
                && client.getNextHousekeepingDate().isBefore(currentDate)
                || client.getNextHousekeepingDate().isEqual(currentDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LeadsFilterPredicate)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
