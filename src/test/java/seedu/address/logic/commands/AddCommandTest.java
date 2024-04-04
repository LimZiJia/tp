package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Client;
import seedu.address.model.person.Housekeeper;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.HousekeeperBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddClientCommand(null));

        assertThrows(NullPointerException.class, () -> new AddHousekeeperCommand(null));
    }

    @Test
    public void execute_clientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Client validClient = new ClientBuilder().build();

        CommandResult commandResult = new AddClientCommand(validClient).execute(modelStub);

        assertEquals(String.format(AddClientCommand.MESSAGE_SUCCESS, Messages.formatClient(validClient)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validClient), modelStub.clientsAdded);
    }

    @Test
    public void execute_housekeeperAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Housekeeper validHousekeeper = new HousekeeperBuilder().build();

        CommandResult commandResult = new AddHousekeeperCommand(validHousekeeper).execute(modelStub);

        assertEquals(String.format(AddHousekeeperCommand.MESSAGE_SUCCESS, Messages.formatHousekeeper(validHousekeeper)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validHousekeeper), modelStub.housekeepersAdded);
    }

    @Test
    public void execute_duplicateClient_throwsCommandException() {
        Client validClient = new ClientBuilder().build();
        Housekeeper validHousekeeper = new HousekeeperBuilder().build();
        AddClientCommand addClientCommand = new AddClientCommand(validClient);
        ModelStub modelStub = new ModelStubWithPerson(validClient, validHousekeeper);

        assertThrows(CommandException.class, AddClientCommand.MESSAGE_DUPLICATE_CLIENT,
                () -> addClientCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateHousekeeper_throwsCommandException() {
        Client validClient = new ClientBuilder().build();
        Housekeeper validHousekeeper = new HousekeeperBuilder().build();
        AddHousekeeperCommand addHousekeeperCommand = new AddHousekeeperCommand(validHousekeeper);
        ModelStub modelStub = new ModelStubWithPerson(validClient, validHousekeeper);

        assertThrows(CommandException.class, AddHousekeeperCommand.MESSAGE_DUPLICATE_HOUSEKEEPER,
                () -> addHousekeeperCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Client alice = new ClientBuilder().withName("Alice").build();
        Housekeeper bob = new HousekeeperBuilder().withName("Bob").build();
        AddClientCommand addAliceCommand = new AddClientCommand(alice);
        AddHousekeeperCommand addBobCommand = new AddHousekeeperCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddClientCommand addAliceCommandCopy = new AddClientCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // same object -> returns true
        assertTrue(addBobCommand.equals(addBobCommand));

        // same values -> returns true
        AddHousekeeperCommand addBobCommandCopy = new AddHousekeeperCommand(bob);
        assertTrue(addBobCommand.equals(addBobCommandCopy));

        // different types -> returns false
        assertFalse(addBobCommand.equals(1));

        // null -> returns false
        assertFalse(addBobCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddClientCommand addClientCommand = new AddClientCommand(ALICE);
        String expected = AddClientCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addClientCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addHousekeeper(Housekeeper housekeeper) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasHousekeeper(Housekeeper housekeeper) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteHousekeeper(Housekeeper housekeeper) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClient(Client target, Client editedClient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setHousekeeper(Housekeeper target, Housekeeper editedHousekeeper) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Housekeeper> getFilteredHousekeeperList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClientList(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredHousekeeperList(Predicate<Housekeeper> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single client and housekeeper.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Client client;
        private final Housekeeper housekeeper;

        ModelStubWithPerson(Client client, Housekeeper housekeeper) {
            requireNonNull(client);
            requireNonNull(housekeeper);
            this.client = client;
            this.housekeeper = housekeeper;
        }

        @Override
        public boolean hasClient(Client client) {
            requireNonNull(client);
            return this.client.isSamePerson(client);
        }

        @Override
        public boolean hasHousekeeper(Housekeeper housekeeper) {
            requireNonNull(housekeeper);
            return this.housekeeper.isSamePerson(housekeeper);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Client> clientsAdded = new ArrayList<>();
        final ArrayList<Housekeeper> housekeepersAdded = new ArrayList<>();

        @Override
        public boolean hasClient(Client client) {
            requireNonNull(client);
            return clientsAdded.stream().anyMatch(client::isSamePerson);
        }

        @Override
        public boolean hasHousekeeper(Housekeeper housekeeper) {
            requireNonNull(housekeeper);
            return housekeepersAdded.stream().anyMatch(housekeeper::isSamePerson);
        }

        @Override
        public void addClient(Client client) {
            requireNonNull(client);
            clientsAdded.add(client);
        }

        @Override
        public void addHousekeeper(Housekeeper housekeeper) {
            requireNonNull(housekeeper);
            housekeepersAdded.add(housekeeper);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
