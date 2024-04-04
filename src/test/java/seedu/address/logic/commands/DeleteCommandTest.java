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
    public void execute_validIndexUnfilteredClientList_success() {
        Client personToDelete = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteClientCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_CLIENT_SUCCESS,
                Messages.formatClient(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredHousekeeperList_success() {
        Housekeeper personToDelete = model.getFilteredHousekeeperList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteHousekeeperCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteHousekeeperCommand.MESSAGE_DELETE_HOUSEKEEPER_SUCCESS,
                Messages.formatHousekeeper(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteHousekeeper(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredClientList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        DeleteCommand deleteCommand = new DeleteClientCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndegetFilteredHousekeeperList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredHousekeeperList().size() + 1);
        DeleteCommand deleteCommand = new DeleteHousekeeperCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredClientList_success() {
        showClientAtIndex(model, INDEX_FIRST_PERSON);

        Client personToDelete = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteClientCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_CLIENT_SUCCESS,
                Messages.formatClient(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(personToDelete);
        showNoClient(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredHousekeeperList_success() {
        showHousekeeperAtIndex(model, INDEX_FIRST_PERSON);

        Housekeeper personToDelete = model.getFilteredHousekeeperList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteHousekeeperCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteHousekeeperCommand.MESSAGE_DELETE_HOUSEKEEPER_SUCCESS,
                Messages.formatHousekeeper(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteHousekeeper(personToDelete);
        showNoHousekeeper(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredClientList_throwsCommandException() {
        showClientAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientList().size());

        DeleteCommand deleteCommand = new DeleteClientCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredHousekeeperList_throwsCommandException() {
        showHousekeeperAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getHousekeeperList().size());

        DeleteCommand deleteCommand = new DeleteHousekeeperCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equalsClient() {
        DeleteCommand deleteFirstClientCommand = new DeleteClientCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteClientCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstClientCommand.equals(deleteFirstClientCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteClientCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstClientCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstClientCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstClientCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstClientCommand.equals(deleteSecondCommand));
    }

    @Test
    public void equalsHousekeeper() {
        DeleteCommand deleteFirstHousekeeperCommand = new DeleteHousekeeperCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteHousekeeperCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstHousekeeperCommand.equals(deleteFirstHousekeeperCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteHousekeeperCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstHousekeeperCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstHousekeeperCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstHousekeeperCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstHousekeeperCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethodClient() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteClientCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void toStringMethodHousekeeper() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteHousekeeperCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s client filtered list to show no one.
     */
    private void showNoClient(Model model) {
        model.updateFilteredClientList(p -> false);

        assertTrue(model.getFilteredClientList().isEmpty());
    }

    /**
     * Updates {@code model}'s housekeeper filtered list to show no one.
     */
    private void showNoHousekeeper(Model model) {
        model.updateFilteredHousekeeperList(p -> false);

        assertTrue(model.getFilteredHousekeeperList().isEmpty());
    }
}
