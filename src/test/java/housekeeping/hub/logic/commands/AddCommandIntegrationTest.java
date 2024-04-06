package housekeeping.hub.logic.commands;

import static housekeeping.hub.logic.commands.CommandTestUtil.assertCommandFailure;
import static housekeeping.hub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static housekeeping.hub.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import housekeeping.hub.logic.Messages;
import housekeeping.hub.model.Model;
import housekeeping.hub.model.ModelManager;
import housekeeping.hub.model.UserPrefs;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.testutil.ClientBuilder;
import housekeeping.hub.testutil.HousekeeperBuilder;

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
                String.format(AddHousekeeperCommand.MESSAGE_SUCCESS, Messages.formatHousekeeper(validHousekeeper)),
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
