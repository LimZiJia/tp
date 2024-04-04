package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClientAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showHousekeeperAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Client;
import seedu.address.model.person.Housekeeper;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Test for deleting client
        Client clientToDelete = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(INDEX_FIRST_PERSON);

        String expectedMessageClient = String.format(DeleteClientCommand.MESSAGE_DELETE_CLIENT_SUCCESS,
                Messages.formatClient(clientToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(clientToDelete);

        assertCommandSuccess(deleteClientCommand, model, expectedMessageClient, expectedModel);

        // Test for deleting housekeeper
        Housekeeper housekeeperToDelete = model.getFilteredHousekeeperList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteHousekeeperCommand deleteHousekeeperCommand = new DeleteHousekeeperCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteHousekeeperCommand.MESSAGE_DELETE_HOUSEKEEPER_SUCCESS,
                Messages.formatHousekeeper(housekeeperToDelete));

        ModelManager expectedModelHousekeeper = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelHousekeeper.deleteHousekeeper(housekeeperToDelete);

        assertCommandSuccess(deleteHousekeeperCommand, model, expectedMessage, expectedModelHousekeeper);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        // Test for deleting client
        Index outOfBoundIndexClient = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(outOfBoundIndexClient);

        assertCommandFailure(deleteClientCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);

        // Test for deleting housekeeper
        Index outOfBoundIndexHousekeeper = Index.fromOneBased(model.getFilteredHousekeeperList().size() + 1);
        DeleteHousekeeperCommand deleteHousekeeperCommand = new DeleteHousekeeperCommand(outOfBoundIndexHousekeeper);

        assertCommandFailure(deleteHousekeeperCommand, model, Messages.MESSAGE_INVALID_HOUSEKEEPER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Test for deleting housekeeper
        showClientAtIndex(model, INDEX_FIRST_PERSON);

        Client clientToDelete = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(INDEX_FIRST_PERSON);

        String expectedClientMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_CLIENT_SUCCESS,
                Messages.formatClient(clientToDelete));

        expectedModel.deleteClient(clientToDelete);
        showNoClient(expectedModel);

        assertCommandSuccess(deleteClientCommand, model, expectedClientMessage, expectedModel);

        // Test for deleting housekeeper
        showHousekeeperAtIndex(model, INDEX_FIRST_PERSON);

        Housekeeper housekeeperToDelete = model.getFilteredHousekeeperList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteHousekeeperCommand deleteHousekeeperCommand = new DeleteHousekeeperCommand(INDEX_FIRST_PERSON);

        String expectedHousekeeperMessage = String.format(DeleteHousekeeperCommand.MESSAGE_DELETE_HOUSEKEEPER_SUCCESS,
                Messages.formatHousekeeper(housekeeperToDelete));

        expectedModel.deleteHousekeeper(housekeeperToDelete);
        showNoHousekeeper(expectedModel);

        assertCommandSuccess(deleteHousekeeperCommand, model, expectedHousekeeperMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // Test for deleting client
        showClientAtIndex(model, INDEX_FIRST_PERSON);

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientList().size());

        DeleteClientCommand deleteCommand = new DeleteClientCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);

        // Test for deleting housekeeper
        showHousekeeperAtIndex(model, INDEX_FIRST_PERSON);

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getHousekeeperList().size());

        DeleteHousekeeperCommand deleteHousekeeperCommand = new DeleteHousekeeperCommand(outOfBoundIndex);

        assertCommandFailure(deleteHousekeeperCommand, model, Messages.MESSAGE_INVALID_HOUSEKEEPER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        // Test for deleting client
        DeleteClientCommand deleteFirstClientCommand = new DeleteClientCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondClientCommand = new DeleteClientCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstClientCommand.equals(deleteFirstClientCommand));

        // same values -> returns true
        DeleteClientCommand deleteFirstClientCommandCopy = new DeleteClientCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstClientCommand.equals(deleteFirstClientCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstClientCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstClientCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstClientCommand.equals(deleteSecondClientCommand));

        // Test for deleting housekeeper
        DeleteHousekeeperCommand deleteFirstHousekeeperCommand = new DeleteHousekeeperCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondHousekeeperCommand = new DeleteHousekeeperCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstHousekeeperCommand.equals(deleteFirstHousekeeperCommand));

        // same values -> returns true
        DeleteHousekeeperCommand deleteFirstHousekeeperCommandCopy = new DeleteHousekeeperCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstHousekeeperCommand.equals(deleteFirstHousekeeperCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstHousekeeperCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstHousekeeperCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstHousekeeperCommand.equals(deleteSecondHousekeeperCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        // Test for deleting client
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(targetIndex);
        String expected = DeleteClientCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteClientCommand.toString());

        // Test for deleting housekeeper
        DeleteHousekeeperCommand deleteHousekeeperCommand = new DeleteHousekeeperCommand(targetIndex);
        expected = DeleteHousekeeperCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteHousekeeperCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered client list to show no one.
     */
    private void showNoClient(Model model) {
        model.updateFilteredClientList(p -> false);

        assertTrue(model.getFilteredClientList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered housekeeper list to show no one.
     */
    private void showNoHousekeeper(Model model) {
        model.updateFilteredHousekeeperList(p -> false);

        assertTrue(model.getFilteredHousekeeperList().isEmpty());
    }
}
