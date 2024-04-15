package housekeeping.hub.model.person;

import java.util.function.Predicate;

import housekeeping.hub.commons.util.StringUtil;
import housekeeping.hub.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Housekeeper}'s {@code Area} matches the specified area and that they do not have a booking
 * at the specified date and time.
 */
public class BookingSearchPredicate implements Predicate<Housekeeper> {
    private final String area;
    private final String bookingToSearch;

    /**
     * Constructs a BookingSearchPredicate with the given area and booked date and time.
     *
     * @param area area to test
     * @param bookingToSearch booked date and time to test
     */
    public BookingSearchPredicate(String area, String bookingToSearch) {
        this.area = area;
        this.bookingToSearch = bookingToSearch;
    }

    public String getArea() {
        return area;
    }

    public String getBookingToSearch() {
        return bookingToSearch;
    }

    @Override
    public boolean test(Housekeeper housekeeper) {
        boolean hasArea = StringUtil.containsWordIgnoreCase(housekeeper.getArea().toString(), area);
        return !housekeeper.hasDuplicateBooking(bookingToSearch) && hasArea;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookingSearchPredicate)) {
            return false;
        }

        BookingSearchPredicate otherBookingSearchPredicate = (BookingSearchPredicate) other;
        boolean isSameBooking = bookingToSearch.equals(otherBookingSearchPredicate.bookingToSearch);
        return isSameBooking;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("booking to search", bookingToSearch).toString();
    }
}
