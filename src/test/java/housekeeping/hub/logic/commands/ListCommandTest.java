package housekeeping.hub.logic.commands;

import static housekeeping.hub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static housekeeping.hub.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import housekeeping.hub.model.Model;
import housekeeping.hub.model.ModelManager;
import housekeeping.hub.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_clientListIsFiltered_showsClientList() {
        assertCommandSuccess(new ListClientCommand(), model, ListCommand.MESSAGE_SUCCESS
                + "all clients", expectedModel);
    }

    @Test
    public void execute_housekeeperListIsFiltered_showsHousekeeperList() {
        assertCommandSuccess(new ListHousekeeperCommand(), model, ListCommand.MESSAGE_SUCCESS
                + "all housekeepers", expectedModel);
    }
}
