package housekeeping.hub.model;

import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static housekeeping.hub.testutil.Assert.assertThrows;
import static housekeeping.hub.testutil.TypicalPersons.ALICE;
import static housekeeping.hub.testutil.TypicalPersons.BENSON;
import static housekeeping.hub.testutil.TypicalPersons.BOB;
import static housekeeping.hub.testutil.TypicalPersons.IDA;
import static housekeeping.hub.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.model.person.exceptions.DuplicatePersonException;
import housekeeping.hub.model.person.exceptions.PersonNotFoundException;
import housekeeping.hub.testutil.ClientBuilder;
import housekeeping.hub.testutil.HousekeeperBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    public void resetData_withDuplicateClients_throwsDuplicatePersonException() {
        // Two clients with the same identity fields
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Client> newClients = Arrays.asList(ALICE, editedAlice);
        List<Housekeeper> newHousekeepers = Arrays.asList(BOB, IDA);
        AddressBookStub newData = new AddressBookStub(newClients, newHousekeepers);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateHousekeepers_throwsDuplicatePersonException() {
        Housekeeper editedBob = new HousekeeperBuilder(BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Client> newClients = Arrays.asList(ALICE, BENSON);
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

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(addressBook.equals(null));
    }

    @Test
    public void equals_sameAddressBook_returnsTrue() {
        assertTrue(addressBook.equals(addressBook));
    }

    @Test
    public void equals_differentAddressBookWithSameLists_returnsFalse() {
        AddressBook otherAddressBook = new AddressBook();
        assertTrue(addressBook.equals(otherAddressBook));
    }

    @Test
    public void equals_differentAddressBookWithDifferentClients_returnsFalse() {
        AddressBook otherAddressBook = new AddressBook();
        otherAddressBook.addClient(ALICE);
        assertFalse(addressBook.equals(otherAddressBook));
    }

    @Test
    public void equals_differentAddressBookWithDifferentHousekeepers_returnsFalse() {
        AddressBook otherAddressBook = new AddressBook();
        otherAddressBook.addHousekeeper(BOB);
        assertFalse(addressBook.equals(otherAddressBook));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{clients=" + addressBook.getClientList()
                + ", housekeepers=" + addressBook.getHousekeeperList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void setClients_nullClientList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setClients(null));
    }

    @Test
    public void setClients_clientListWithDuplicateClients_throwsDuplicatePersonException() {
        List<Client> listWithDuplicateClients = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> addressBook.setClients(listWithDuplicateClients));
    }

    @Test
    public void setClients_clientListWithUniqueClients_replacesData() {
        List<Client> listWithUniqueClients = Arrays.asList(ALICE, BENSON);
        addressBook.setClients(listWithUniqueClients);
        assertEquals(listWithUniqueClients, addressBook.getClientList());
    }

    @Test
    public void setHousekeepers_nullHousekeeperList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setHousekeepers(null));
    }

    @Test
    public void setHousekeepers_housekeeperListWithDuplicateHousekeepers_throwsDuplicatePersonException() {
        List<Housekeeper> listWithDuplicateHousekeepers = Arrays.asList(BOB, BOB);
        assertThrows(DuplicatePersonException.class, () -> addressBook.setHousekeepers(listWithDuplicateHousekeepers));
    }

    @Test
    public void setHousekeepers_housekeeperListWithUniqueHousekeepers_replacesData() {
        List<Housekeeper> listWithUniqueHousekeepers = Arrays.asList(BOB, IDA);
        addressBook.setHousekeepers(listWithUniqueHousekeepers);
        assertEquals(listWithUniqueHousekeepers, addressBook.getHousekeeperList());
    }

    @Test
    public void addClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.addClient(null));
    }

    @Test
    public void addClient_duplicateClient_throwsDuplicatePersonException() {
        addressBook.addClient(ALICE);
        assertThrows(DuplicatePersonException.class, () -> addressBook.addClient(ALICE));
    }

    @Test
    public void addClient_uniqueClient_clientAdded() {
        addressBook.addClient(ALICE);
        assertTrue(addressBook.hasClient(ALICE));
    }

    @Test
    public void addHousekeeper_nullHousekeeper_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.addHousekeeper(null));
    }

    @Test
    public void addHousekeeper_duplicateHousekeeper_throwsDuplicatePersonException() {
        addressBook.addHousekeeper(BOB);
        assertThrows(DuplicatePersonException.class, () -> addressBook.addHousekeeper(BOB));
    }

    @Test
    public void addHousekeeper_uniqueHousekeeper_housekeeperAdded() {
        addressBook.addHousekeeper(BOB);
        assertTrue(addressBook.hasHousekeeper(BOB));
    }

    @Test
    public void setClient_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setClient(null, ALICE));
    }

    @Test
    public void setClient_nullEditedClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setClient(ALICE, null));
    }

    @Test
    public void setClient_targetNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.setClient(ALICE, ALICE));
    }

    @Test
    public void setClient_editedClientHasSameIdentityFields_throwsDuplicatePersonException() {
        addressBook.addClient(ALICE);
        addressBook.addClient(BENSON);
        Client editedAlice = new ClientBuilder(BENSON).build();
        assertThrows(DuplicatePersonException.class, () -> addressBook.setClient(ALICE, editedAlice));
    }

    @Test
    public void setClient_targetInAddressBookAndEditedClientHasDifferentIdentityFields_clientReplaced() {
        addressBook.addClient(ALICE);
        addressBook.setClient(ALICE, BENSON);
        assertFalse(addressBook.hasClient(ALICE));
        assertTrue(addressBook.hasClient(BENSON));
    }

    @Test
    public void setHousekeeper_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setHousekeeper(null, BOB));
    }

    @Test
    public void setHousekeeper_nullEditedHousekeeper_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setHousekeeper(BOB, null));
    }

    @Test
    public void setHousekeeper_targetNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.setHousekeeper(BOB, BOB));
    }

    @Test
    public void setHousekeeper_editedHousekeeperHasSameIdentityFields_throwsDuplicatePersonException() {
        addressBook.addHousekeeper(BOB);
        addressBook.addHousekeeper(IDA);
        Housekeeper editedBob = new HousekeeperBuilder(IDA).build();
        assertThrows(DuplicatePersonException.class, () -> addressBook.setHousekeeper(BOB, editedBob));
    }

    @Test
    public void setHousekeeper_targetInAddressBookAndEditedHousekeeperHasDifferentIdentityFields_housekeeperReplaced() {
        addressBook.addHousekeeper(BOB);
        addressBook.setHousekeeper(BOB, IDA);
        assertFalse(addressBook.hasHousekeeper(BOB));
        assertTrue(addressBook.hasHousekeeper(IDA));
    }

    @Test
    public void removeClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.removeClient(null));
    }

    @Test
    public void removeClient_clientNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.removeClient(ALICE));
    }

    @Test
    public void removeClient_clientInAddressBook_clientRemoved() {
        addressBook.addClient(ALICE);
        addressBook.removeClient(ALICE);
        assertFalse(addressBook.hasClient(ALICE));
    }

    @Test
    public void removeHousekeeper_nullHousekeeper_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.removeHousekeeper(null));
    }

    @Test
    public void removeHousekeeper_housekeeperNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.removeHousekeeper(BOB));
    }

    @Test
    public void removeHousekeeper_housekeeperInAddressBook_housekeeperRemoved() {
        addressBook.addHousekeeper(BOB);
        addressBook.removeHousekeeper(BOB);
        assertFalse(addressBook.hasHousekeeper(BOB));
    }



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
