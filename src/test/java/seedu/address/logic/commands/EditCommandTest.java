package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClientAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Client;
import seedu.address.model.person.Housekeeper;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.HousekeeperBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Client editedClient = new ClientBuilder().build();
        Housekeeper editedHousekeeper = new HousekeeperBuilder().build();
        EditPersonDescriptor descriptorClient = new EditPersonDescriptorBuilder(editedClient).build();
        EditPersonDescriptor descriptorHousekeeper = new EditPersonDescriptorBuilder(editedHousekeeper).build();
        EditCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON, descriptorClient);
        EditCommand editHousekeeperCommand = new EditHousekeeperCommand(INDEX_FIRST_PERSON, descriptorHousekeeper);

        String expectedMessageClient =
                String.format(EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS, Messages.formatClient(editedClient));
        String expectedMessageHousekeeper =
                String.format(EditHousekeeperCommand.MESSAGE_EDIT_HOUSEKEEPER_SUCCESS,
                        Messages.formatHousekeeper(editedHousekeeper));

        Model expectedModelClient = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModelClient.setClient(model.getFilteredClientList().get(0), editedClient);

        Model expectedModelHousekeeper = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModelHousekeeper.setHousekeeper(model.getFilteredHousekeeperList().get(0), editedHousekeeper);


        assertCommandSuccess(editClientCommand, model, expectedMessageClient, expectedModelClient);

        assertCommandSuccess(editClientCommand, model, expectedMessageHousekeeper, expectedModelHousekeeper);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastClient = Index.fromOneBased(model.getFilteredClientList().size());
        Client lastClient = model.getFilteredClientList().get(indexLastClient.getZeroBased());

        Index indexLastHousekeeper = Index.fromOneBased(model.getFilteredHousekeeperList().size());
        Housekeeper lastHousekeeper = model.getFilteredHousekeeperList().get(indexLastHousekeeper.getZeroBased());

        ClientBuilder clientInList = new ClientBuilder(lastClient);
        Client editedClient = clientInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        HousekeeperBuilder housekeeperInList = new HousekeeperBuilder(lastHousekeeper);
        Housekeeper editedHousekeeper = housekeeperInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptorC = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommandClient = new EditClientCommand(indexLastClient, descriptorC);

        EditPersonDescriptor descriptorH = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommandHousekeeper = new EditHousekeeperCommand(indexLastHousekeeper, descriptorH);

        String expectedMessageH =
                String.format(EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS, Messages.formatClient(editedClient));
        String expectedMessageC =
                String.format(EditHousekeeperCommand.MESSAGE_EDIT_HOUSEKEEPER_SUCCESS, Messages.formatHousekeeper(editedHousekeeper));

        Model expectedModelClient = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModelClient.setClient(lastClient, editedClient);
        Model expectedModelHousekeeper = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModelHousekeeper.setHousekeeper(lastHousekeeper, editedHousekeeper);

        assertCommandSuccess(editCommandClient, model, expectedMessageC, expectedModelClient);
        assertCommandSuccess(editCommandHousekeeper, model, expectedMessageH, expectedModelHousekeeper);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditClientCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Client editedPerson = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage =
                String.format(EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS, Messages.formatClient(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showClientAtIndex(model, INDEX_FIRST_PERSON);

        Client personInFilteredList = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
        Client editedPerson = new ClientBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditClientCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage =
                String.format(EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS, Messages.formatClient(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClient(model.getFilteredClientList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Client firstPerson = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditClientCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditClientCommand.MESSAGE_DUPLICATE_CLIENT);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showClientAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getClientList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditClientCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditClientCommand.MESSAGE_DUPLICATE_CLIENT);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditClientCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showClientAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientList().size());

        EditCommand editCommand = new EditClientCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditClientCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditClientCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditClientCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditClientCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditClientCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
