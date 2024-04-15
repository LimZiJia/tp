package housekeeping.hub.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HousekeepingDetailsTest {
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_NULL = new HousekeepingDetails();
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_NO_BOOKING_DATE =
            new HousekeepingDetails("2024-01-30 P2M null P0D");
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_WITH_BOOKING_DATE =
            new HousekeepingDetails("2024-01-30 P2M 2024-01-01 am P0D");
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_DIFFERENT_PERIOD =
            new HousekeepingDetails("2024-01-30 P2M 2024-01-01 am P1D");
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_DIFFERENT_BOOKING_DATE =
            new HousekeepingDetails("2024-01-30 P2M 2024-01-02 am P0D");
    static final HousekeepingDetails HOUSEKEEPING_DETAILS_DIFFERENT_SESSION =
            new HousekeepingDetails("2024-01-30 P2M 2024-01-01 pm P0D");
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
    public void test_Equals() {
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
}