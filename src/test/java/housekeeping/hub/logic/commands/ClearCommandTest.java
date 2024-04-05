package housekeeping.hub.logic.commands;

import static housekeeping.hub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static housekeeping.hub.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import housekeeping.hub.model.AddressBook;
import housekeeping.hub.model.Model;
import housekeeping.hub.model.ModelManager;
import housekeeping.hub.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
