package housekeeping.hub.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static housekeeping.hub.testutil.Assert.assertThrows;
import static housekeeping.hub.testutil.TypicalPersons.ALICE;
import static housekeeping.hub.testutil.TypicalPersons.BOB;
import static housekeeping.hub.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.model.person.exceptions.DuplicatePersonException;
import housekeeping.hub.testutil.ClientBuilder;
import housekeeping.hub.testutil.HousekeeperBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getClientList());
        assertEquals(Collections.emptyList(), addressBook.getHousekeeperList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two clients with the same identity fields
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        Housekeeper editedBob = new HousekeeperBuilder(BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Client> newClients = Arrays.asList(ALICE, editedAlice);
        List<Housekeeper> newHousekeepers = Arrays.asList(BOB, editedBob);
        AddressBookStub newData = new AddressBookStub(newClients, newHousekeepers);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasClient(null));
    }

    @Test
    public void hasHousekeeper_nullHousekeeper_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasHousekeeper(null));
    }

    @Test
    public void hasClient_clientNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasClient(ALICE));
    }

    @Test
    public void hasHousekeeper_housekeeperNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasHousekeeper(BOB));
    }

    @Test
    public void hasClient_clientInAddressBook_returnsTrue() {
        addressBook.addClient(ALICE);
        assertTrue(addressBook.hasClient(ALICE));
    }

    @Test
    public void hasHousekeeper_housekeeperInAddressBook_returnsTrue() {
        addressBook.addHousekeeper(BOB);
        assertTrue(addressBook.hasHousekeeper(BOB));
    }

    @Test
    public void hasClient_clientWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addClient(ALICE);
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasClient(editedAlice));
    }

    @Test
    public void hasHousekeeper_housekeeperWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addHousekeeper(BOB);
        Housekeeper editedBob = new HousekeeperBuilder(BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasHousekeeper(editedBob));
    }

    @Test
    public void getClientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getClientList().remove(0));
    }

    @Test
    public void getHousekeeperList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getHousekeeperList().remove(0));
    }

    /*@Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getClientList() + "}";
        assertEquals(expected, addressBook.toString());
    }*/

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Client> clients = FXCollections.observableArrayList();
        private final ObservableList<Housekeeper> housekeepers = FXCollections.observableArrayList();

        AddressBookStub(Collection<Client> clients, Collection<Housekeeper> housekeepers) {
            this.clients.setAll(clients);
            this.housekeepers.setAll(housekeepers);
        }

        @Override
        public ObservableList<Client> getClientList() {
            return clients;
        }

        @Override
        public ObservableList<Housekeeper> getHousekeeperList() {
            return housekeepers;
        }
    }

}
