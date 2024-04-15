package housekeeping.hub.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;

class HousekeepingDetailsTest {
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_NULL = new HousekeepingDetails();
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_NO_BOOKING_DATE =
            new HousekeepingDetails("2024-01-30 P2M null P0D");
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE =
            new HousekeepingDetails("2024-01-30 P2M 2024-01-01 am P0D");
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_DIFFERENT_PERIOD =
            new HousekeepingDetails("2024-01-30 P2M 2024-01-01 am P1D");
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_DIFFERENT_BOOKING_DATE =
            new HousekeepingDetails("2024-01-30 P2M 2024-05-02 am P0D");
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_DIFFERENT_SESSION =
            new HousekeepingDetails("2024-01-30 P2M 2024-01-01 pm P0D");

    static final HousekeepingDetails HOUSEKEEPING_DETAILS_DIFFERENT_LAST_HOUSEKEEPING_DATE =
            new HousekeepingDetails("2024-01-31 P2M 2024-01-01 am P0D");
    @Test
    public void test_storageStringConstructor() {
        // Empty details
        assertTrue(HOUSEKEEPING_DETAILS_NULL.getBooking() == null);
        assertTrue(HOUSEKEEPING_DETAILS_NULL.getDeferment() == null);
        assertTrue(HOUSEKEEPING_DETAILS_NULL.getLastHousekeepingDate() == null);
        assertTrue(HOUSEKEEPING_DETAILS_NULL.getPreferredInterval() == null);

        // Details with no booking date
        assertTrue(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE.getBooking() == null);

        // Details with booking date
        assertEquals("2024-01-01 am", HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.getBooking().toString());
        assertEquals("2024-01-30", HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.getLastHousekeepingDate().toString());
        assertEquals("P2M", HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.getPreferredInterval().toString());
        assertEquals("P0D", HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.getDeferment().toString());
    }

    @Test
    public void test_makeStoredDetailsReadable() {

        // Empty details
        assertEquals(HousekeepingDetails.NO_DETAILS_PROVIDED, HousekeepingDetails.makeStoredDetailsReadable("null"));

        // Details with no booking date
        assertEquals("Last housekeeping: 2024-02-28\n"
                        + "Preferred interval: 14 days\n"
                        + "Booking date: No booking",
                HousekeepingDetails.makeStoredDetailsReadable("2024-02-28 P14D null P0D"));

        // Details with booking date
        assertEquals("Last housekeeping: 2024-01-30\n"
                        + "Preferred interval: 2 months\n"
                        + "Booking date: 2024-01-01 am",
                HousekeepingDetails.makeStoredDetailsReadable("2024-01-30 P2M 2024-01-01 am P0D"));

        assertEquals("Last housekeeping: 2024-01-30\n"
                        + "Preferred interval: 2 weeks\n"
                        + "Booking date: 2024-01-01 am",
                HousekeepingDetails.makeStoredDetailsReadable("2024-01-30 P2W 2024-01-01 am P0D"));

        assertEquals("Last housekeeping: 2024-01-30\n"
                        + "Preferred interval: 2 years\n"
                        + "Booking date: 2024-01-01 pm",
                HousekeepingDetails.makeStoredDetailsReadable("2024-01-30 P2Y 2024-01-01 pm P0D"));

        assertEquals("Last housekeeping: 2025-01-30\n"
                        + "Preferred interval: 2 weeks\n"
                        + "Booking date: 2024-01-01 pm",
                HousekeepingDetails.makeStoredDetailsReadable("2025-01-30 P2W 2024-01-01 pm P0D"));

        // Invalid details
        assertEquals("Invalid housekeeping details format", HousekeepingDetails.makeStoredDetailsReadable(""));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadable("2024-01-30 P2M"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadable("2024-01-30 P2M 2024-01-01 am"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadable("P2M 2024-01-30 2024-01-01 am P0D"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadable("2024-01-30 P2W 2024-01-01 zm P0D"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadable("2024-01-30 P2W 2024-01-01 zm P0Z"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadable("202401-30 P2W 2024-01-01 zm P0D"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadable("202401-30 P2W nul P0D"));
    }

    @Test
    public void test_makeStoredDetailsReadableWithDeferment() {

        // Empty details
        assertEquals(HousekeepingDetails.NO_DETAILS_PROVIDED,
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("null"));

        // Details with no booking date
        assertEquals("Last housekeeping: 2024-02-28, "
                        + "Preferred interval: 14 days, "
                        + "Booking date: No booking, "
                        + "Deferment: 1 days",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("2024-02-28 P14D null P1D"));

        // Details with booking date
        assertEquals("Last housekeeping: 2024-01-30, "
                        + "Preferred interval: 2 months, "
                        + "Booking date: 2024-01-01 am, "
                        + "Deferment: 2 weeks",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("2024-01-30 P2M 2024-01-01 am P2W"));

        assertEquals("Last housekeeping: 2024-01-30, "
                        + "Preferred interval: 2 weeks, "
                        + "Booking date: 2024-01-01 am, "
                        + "Deferment: 3 months",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("2024-01-30 P2W 2024-01-01 am P3M"));

        assertEquals("Last housekeeping: 2024-01-30, "
                        + "Preferred interval: 2 years, "
                        + "Booking date: 2024-01-01 pm, "
                        + "Deferment: 4 years",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("2024-01-30 P2Y 2024-01-01 pm P4Y"));

        assertEquals("Last housekeeping: 2025-01-30, "
                        + "Preferred interval: 2 weeks, "
                        + "Booking date: 2024-01-01 pm, "
                        + "Deferment: 0 days",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("2025-01-30 P2W 2024-01-01 pm P0D"));

        // Invalid details
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment(""));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("2024-01-30 P2M"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("2024-01-30 P2M 2024-01-01 am"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("P2M 2024-01-30 2024-01-01 am P0D"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("2024-01-30 P2W 2024-01-01 zm P0D"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("2024-01-30 P2W 2024-01-01 zm P0Z"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("202401-30 P2W 2024-01-01 zm P0D"));
        assertEquals("Invalid housekeeping details format",
                HousekeepingDetails.makeStoredDetailsReadableWithDeferment("202401-30 P2W nul P0D"));
    }

    @Test
    public void test_isEmpty() {
        assertTrue(HOUSEKEEPING_DETAILS_NULL.isEmpty());
        assertFalse(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE.isEmpty());
        assertFalse(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.isEmpty());
    }

    @Test
    public void test_hasBookingDate() {
        assertFalse(HOUSEKEEPING_DETAILS_NULL.hasBooking());
        assertFalse(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE.hasBooking());
        assertFalse(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.hasBooking());

        String tomorrowDate = LocalDate.now().plusDays(1).toString();
        String formattedDetails = String.format("2024-01-30 P2M %s am P0D", tomorrowDate);
        HousekeepingDetails housekeepingDetailsWithBooking = new HousekeepingDetails(formattedDetails);
        assertTrue(housekeepingDetailsWithBooking.hasBooking());
    }

    @Test
    public void test_setBooking() {
        HousekeepingDetails housekeepingDetails = new HousekeepingDetails();
        Booking booking = new Booking("2024-01-01 am");
        housekeepingDetails.setBooking(booking);
        assertEquals(booking, housekeepingDetails.getBooking());
    }

    @Test
    public void test_deleteBooking() {
        HousekeepingDetails housekeepingDetails = new HousekeepingDetails("2024-01-30 P2Y 2024-01-01 pm P0D");
        housekeepingDetails.deleteBooking();
        assertEquals(null, housekeepingDetails.getBooking());
    }

    @Test
    public void test_addDeferment() {
        HousekeepingDetails housekeepingDetails = new HousekeepingDetails("2024-01-30 P2Y 2024-01-01 pm P0D");
        housekeepingDetails.addDeferment(Period.ofDays(1));
        assertEquals(Period.ofDays(1), housekeepingDetails.getDeferment());
    }

    @Test
    public void test_getNextHousekeepingDate() {
        // Empty details
        assertEquals(LocalDate.MAX, HOUSEKEEPING_DETAILS_NULL.getNextHousekeepingDate());

        // Details with no booking date
        assertEquals(LocalDate.parse("2024-03-30"),
                HOUSEKEEPING_DETAILS_NO_BOOKING_DATE.getNextHousekeepingDate());

        // Details with booking date
        assertEquals(LocalDate.parse("2024-03-30"),
                HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.getNextHousekeepingDate());
    }

    @Test
    public void test_getDefermentToString() {
        HousekeepingDetails housekeepingDetails = new HousekeepingDetails("2024-01-30 P2Y 2024-01-01 pm P0D");

        assertEquals("0 days", housekeepingDetails.getDefermentToReadableString());

        housekeepingDetails.addDeferment(Period.ofWeeks(1));
        assertEquals("7 days", housekeepingDetails.getDefermentToReadableString());

        housekeepingDetails = new HousekeepingDetails("2024-01-30 P2Y 2024-01-01 pm P0D");
        housekeepingDetails.addDeferment(Period.ofMonths(2));
        assertEquals("2 months", housekeepingDetails.getDefermentToReadableString());

        housekeepingDetails = new HousekeepingDetails("2024-01-30 P2Y 2024-01-01 pm P0D");
        housekeepingDetails.addDeferment(Period.ofYears(3));
        assertEquals("3 years", housekeepingDetails.getDefermentToReadableString());
    }

    @Test void test_compareTo() {
        // Identity
        assertTrue(HOUSEKEEPING_DETAILS_NULL.compareTo(HOUSEKEEPING_DETAILS_NULL) == 0);
        assertTrue(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE.compareTo(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE) == 0);
        assertTrue(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.compareTo(
                HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE) == 0);

        // Same predicted date
        assertTrue(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE.compareTo(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE) == 0);

        // No housekeeping details will be max value
        assertTrue(HOUSEKEEPING_DETAILS_NULL.compareTo(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE) > 0);
        assertTrue(HOUSEKEEPING_DETAILS_NULL.compareTo(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE) > 0);

        // Different period
        assertTrue(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.compareTo(HOUSEKEEPING_DETAILS_DIFFERENT_PERIOD) < 0);

        // Different booking date
        assertTrue(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.compareTo(
                HOUSEKEEPING_DETAILS_DIFFERENT_LAST_HOUSEKEEPING_DATE) < 0);
    }

    @Test
    public void test_equals() {
        // Different types -> returns false
        assertFalse(HOUSEKEEPING_DETAILS_NULL.equals(5));

        // null -> returns false
        assertFalse(HOUSEKEEPING_DETAILS_NULL.equals(null));

        // Details should equal themselves
        assertTrue(HOUSEKEEPING_DETAILS_NULL.equals(HOUSEKEEPING_DETAILS_NULL));
        assertTrue(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE.equals(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE));
        assertTrue(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.equals(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE));

        // Different detail objects with same fields should be equal
        assertTrue(HOUSEKEEPING_DETAILS_NULL.equals(new HousekeepingDetails()));
        assertTrue(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE.equals(new HousekeepingDetails("2024-01-30 P2M null P0D")));
        assertTrue(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.equals(
                new HousekeepingDetails("2024-01-30 P2M 2024-01-01 am P0D")));

        // Different fields should not be equal
        assertFalse(HOUSEKEEPING_DETAILS_NULL.equals(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE));
        assertFalse(HOUSEKEEPING_DETAILS_NULL.equals(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE));
        assertFalse(HOUSEKEEPING_DETAILS_NO_BOOKING_DATE.equals(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE));

        // Different period should not be equal
        assertFalse(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.equals(HOUSEKEEPING_DETAILS_DIFFERENT_PERIOD));

        // Different booking date should not be equal
        assertFalse(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.equals(HOUSEKEEPING_DETAILS_DIFFERENT_BOOKING_DATE));

        // Different session should not be equal
        assertFalse(HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.equals(HOUSEKEEPING_DETAILS_DIFFERENT_SESSION));
    }

    @Test
    public void test_toString() {
        assertEquals("null", HOUSEKEEPING_DETAILS_NULL.toString());
        assertEquals("2024-01-30 P2M null P0D", HOUSEKEEPING_DETAILS_NO_BOOKING_DATE.toString());
        assertEquals("2024-01-30 P2M 2024-01-01 am P0D", HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE.toString());
    }
}
