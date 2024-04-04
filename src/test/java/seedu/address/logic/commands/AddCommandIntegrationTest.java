package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Client;
import seedu.address.model.person.Housekeeper;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.HousekeeperBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newClient_success() {
        Client validClient = new ClientBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addClient(validClient);

        assertCommandSuccess(new AddClientCommand(validClient), model,
                String.format(AddClientCommand.MESSAGE_SUCCESS, Messages.formatClient(validClient)),
                expectedModel);
    }

    @Test
    public void execute_newHousekeeper_success() {
        Housekeeper validHousekeeper = new HousekeeperBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addHousekeeper(validHousekeeper);

        assertCommandSuccess(new AddHousekeeperCommand(validHousekeeper), model,
                String.format(AddHousekeeperCommand.MESSAGE_SUCCESS, validHousekeeper),
                expectedModel);
    }

    @Test
    public void execute_duplicateClient_throwsCommandException() {
        Client clientInList = model.getAddressBook().getClientList().get(0);
        assertCommandFailure(new AddClientCommand(clientInList), model,
                AddClientCommand.MESSAGE_DUPLICATE_CLIENT);
    }

    @Test
    public void execute_duplicateHousekeeper_throwsCommandException() {
        Housekeeper housekeeperInList = model.getAddressBook().getHousekeeperList().get(0);
        assertCommandFailure(new AddHousekeeperCommand(housekeeperInList), model,
                AddHousekeeperCommand.MESSAGE_DUPLICATE_HOUSEKEEPER);
    }

}
