package housekeeping.hub.logic.commands;

import static housekeeping.hub.logic.commands.CommandTestUtil.*;
import static housekeeping.hub.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static housekeeping.hub.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static housekeeping.hub.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import housekeeping.hub.model.person.Booking;
import org.junit.jupiter.api.Test;

import housekeeping.hub.commons.core.index.Index;
import housekeeping.hub.logic.Messages;
import housekeeping.hub.logic.commands.EditHousekeepingDetailsCommand.EditHousekeepingDetailsDescriptor;
import housekeeping.hub.model.AddressBook;
import housekeeping.hub.model.Model;
import housekeeping.hub.model.ModelManager;
import housekeeping.hub.model.UserPrefs;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.model.person.Person;
import housekeeping.hub.testutil.ClientBuilder;
import housekeeping.hub.testutil.EditHousekeepingDetailsDescriptorBuilder;
import housekeeping.hub.testutil.HousekeeperBuilder;

import java.time.LocalDate;
import java.time.Period;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditHousekeepingDetailsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Client editedClient = new ClientBuilder(model.getFilteredClientList().get(0)).build();
        EditHousekeepingDetailsDescriptor descriptorClient = new EditHousekeepingDetailsDescriptorBuilder(editedClient).build();
        EditHousekeepingDetailsCommand editHDCommand = new EditHousekeepingDetailsCommand(INDEX_FIRST_PERSON, descriptorClient);

        String expectedMessageClient =
                String.format(EditHousekeepingDetailsCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.formatClient(editedClient));

        Model expectedModelClient = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModelClient.setClient(model.getFilteredClientList().get(0), editedClient);


        assertCommandSuccess(editHDCommand, model, expectedMessageClient, expectedModelClient);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastClient = Index.fromOneBased(model.getFilteredClientList().size());
        Client lastClient = model.getFilteredClientList().get(indexLastClient.getZeroBased());

        ClientBuilder clientInList = new ClientBuilder(lastClient);
        Client editedClient = clientInList.withLastHousekeepingDate(LocalDate.parse(VALID_LHD_BOB))
                .withPI(Period.parse(VALID_PI_BOB)).build();


        EditHousekeepingDetailsDescriptor descriptorC =
                new EditHousekeepingDetailsDescriptorBuilder().withLastHousekeepingDate(LocalDate.parse(VALID_LHD_BOB))
                        .withPreferredInterval(Period.parse(VALID_PI_BOB)).build();
        EditHousekeepingDetailsCommand editCommandClient =
                new EditHousekeepingDetailsCommand(indexLastClient, descriptorC);

        String expectedMessageC =
                String.format(EditHousekeepingDetailsCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                        Messages.formatClient(editedClient));

        Model expectedModelClient = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModelClient.setClient(lastClient, editedClient);

        assertCommandSuccess(editCommandClient, model, expectedMessageC, expectedModelClient);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditHousekeepingDetailsCommand editCommand =
                new EditHousekeepingDetailsCommand(INDEX_FIRST_PERSON, new EditHousekeepingDetailsDescriptor());
        Client editedPerson = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage =
                String.format(EditHousekeepingDetailsCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.formatClient(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Client personInFilteredList = model.getFilteredClientList().get(INDEX_FIRST_PERSON.getZeroBased());
        Client editedPerson = new ClientBuilder(personInFilteredList).withBooking(new Booking(VALID_BOOKING_BOB)).build();
        EditHousekeepingDetailsCommand editCommand = new EditHousekeepingDetailsCommand(INDEX_FIRST_PERSON,
                new EditHousekeepingDetailsDescriptorBuilder().withBookingDate(new Booking(VALID_BOOKING_BOB)).build());

        String expectedMessage =
                String.format(EditHousekeepingDetailsCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.formatClient(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClient(model.getFilteredClientList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        EditHousekeepingDetailsDescriptor descriptor = new EditHousekeepingDetailsDescriptorBuilder()
                .withDeferment(Period.parse(VALID_DEFERMENT_BOB)).build();
        EditHousekeepingDetailsCommand editCommand = new EditHousekeepingDetailsCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of hub book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showClientAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of hub book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientList().size());

        EditHousekeepingDetailsCommand editCommand = new EditHousekeepingDetailsCommand(outOfBoundIndex,
                new EditHousekeepingDetailsDescriptorBuilder()
                        .withPreferredInterval(Period.parse(VALID_PI_BOB)).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditHousekeepingDetailsCommand standardCommand =
                new EditHousekeepingDetailsCommand(INDEX_FIRST_PERSON, DESCC_BOB);

        // same values -> returns true
        EditHousekeepingDetailsDescriptor  copyDescriptor = new EditHousekeepingDetailsDescriptor(DESCC_BOB);
        EditHousekeepingDetailsCommand commandWithSameValues = new EditHousekeepingDetailsCommand(INDEX_FIRST_PERSON,
                copyDescriptor);
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
        EditHousekeepingDetailsDescriptor editPersonDescriptor = new EditHousekeepingDetailsDescriptor();
        EditHousekeepingDetailsCommand editCommand = new EditHousekeepingDetailsCommand(index, editPersonDescriptor);
        String expected = EditHousekeepingDetailsCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
