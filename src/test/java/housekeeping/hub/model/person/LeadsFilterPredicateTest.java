package housekeeping.hub.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;

import housekeeping.hub.testutil.ClientBuilder;

class LeadsFilterPredicateTest {
    // The combination here gives a predicted housekeeping date that is in the past. Client should get notification.
    private static final LocalDate DEFAULT_DATE = LocalDate.parse("2023-04-01");
    private static final Period DEFAULT_PERIOD = Period.ofMonths(1);

    @Test
    void test_clientWithoutHousekeepingDetails_returnFalse() {
        LeadsFilterPredicate predicate = new LeadsFilterPredicate();

        HousekeepingDetails housekeepingDetails = new HousekeepingDetails();
        ClientBuilder clientBuilder = new ClientBuilder().withDetails(housekeepingDetails);
        Client client = clientBuilder.build();

        assertFalse(predicate.test(client));
    }

    @Test
    void test_clientWithoutBookingDate_returnsTrue() {
        LeadsFilterPredicate predicate = new LeadsFilterPredicate();

        HousekeepingDetails housekeepingDetails = new HousekeepingDetails(DEFAULT_DATE, DEFAULT_PERIOD);
        ClientBuilder clientBuilder = new ClientBuilder().withDetails(housekeepingDetails);
        Client client = clientBuilder.build();

        assertTrue(predicate.test(client));
    }

    @Test
    void test_clientWithBookingDateAfterToday_returnsFalse() {
        LeadsFilterPredicate predicate = new LeadsFilterPredicate();

        Period period = Period.ofMonths(1);
        LocalDate date = LocalDate.now().plus(period); // Create a date one month from now
        HousekeepingDetails housekeepingDetails = new HousekeepingDetails(date, DEFAULT_PERIOD);
        ClientBuilder clientBuilder = new ClientBuilder().withDetails(housekeepingDetails);
        Client client = clientBuilder.build();

        assertFalse(predicate.test(client));
    }

    @Test
    void test_clientWithBookingDateToday_returnsFalse() {
        LeadsFilterPredicate predicate = new LeadsFilterPredicate();

        LocalDate date = LocalDate.now(); // Create a date that is today
        HousekeepingDetails housekeepingDetails = new HousekeepingDetails(date, DEFAULT_PERIOD);
        ClientBuilder clientBuilder = new ClientBuilder().withDetails(housekeepingDetails);
        Client client = clientBuilder.build();

        assertFalse(predicate.test(client));
    }

    @Test
    void test_clientWithBookingDateBeforeToday_returnsTrue() {
        LeadsFilterPredicate predicate = new LeadsFilterPredicate();

        Period period = Period.ofMonths(1);
        LocalDate date = LocalDate.now().minus(period); // Create a date that is before today
        HousekeepingDetails housekeepingDetails = new HousekeepingDetails(date, DEFAULT_PERIOD);
        ClientBuilder clientBuilder = new ClientBuilder().withDetails(housekeepingDetails);
        Client client = clientBuilder.build();

        assertTrue(predicate.test(client));
    }

    @Test
    void test_clientWithPredictedHousekeepingDateBeforeToday_returnsTrue() {
        LeadsFilterPredicate predicate = new LeadsFilterPredicate();

        HousekeepingDetails housekeepingDetails = new HousekeepingDetails(DEFAULT_DATE, DEFAULT_PERIOD);
        ClientBuilder clientBuilder = new ClientBuilder().withDetails(housekeepingDetails);
        Client client = clientBuilder.build();

        assertTrue(predicate.test(client));
    }

    @Test
    void test_clientWithPredictedHousekeepingDateToday_returnsTrue() {
        LeadsFilterPredicate predicate = new LeadsFilterPredicate();

        LocalDate date = LocalDate.now();
        Period period = Period.ZERO;
        HousekeepingDetails housekeepingDetails = new HousekeepingDetails(date, period);
        ClientBuilder clientBuilder = new ClientBuilder().withDetails(housekeepingDetails);
        Client client = clientBuilder.build();

        assertTrue(predicate.test(client));
    }

    @Test
    void test_clientWithPredictedHousekeepingDateAfterTodat_returnsFalse() {
        LeadsFilterPredicate predicate = new LeadsFilterPredicate();

        LocalDate date = LocalDate.now();
        Period period = Period.ofMonths(1);
        HousekeepingDetails housekeepingDetails = new HousekeepingDetails(date, period);
        ClientBuilder clientBuilder = new ClientBuilder().withDetails(housekeepingDetails);
        Client client = clientBuilder.build();

        assertFalse(predicate.test(client));
    }
}
