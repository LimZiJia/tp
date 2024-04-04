package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.TypePredicate;

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
