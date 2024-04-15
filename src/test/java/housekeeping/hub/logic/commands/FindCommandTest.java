package housekeeping.hub.logic.commands;

import static housekeeping.hub.logic.Messages.MESSAGE_CLIENTS_LISTED_OVERVIEW;
import static housekeeping.hub.logic.Messages.MESSAGE_HOUSEKEEPERS_LISTED_OVERVIEW;
import static housekeeping.hub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static housekeeping.hub.testutil.TypicalPersons.ELLE;
import static housekeeping.hub.testutil.TypicalPersons.FIONA;
import static housekeeping.hub.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import housekeeping.hub.model.Model;
import housekeeping.hub.model.ModelManager;
import housekeeping.hub.model.UserPrefs;
import housekeeping.hub.model.person.ContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ContainsKeywordsPredicate firstPredicate =
                new ContainsKeywordsPredicate(Collections.singletonList("first"), Collections.singletonList("Clementi"),
                        Collections.singletonList("north"));

        ContainsKeywordsPredicate secondPredicate =
                new ContainsKeywordsPredicate(Collections.singletonList("second"), Collections.singletonList("Jurong"),
                        Collections.singletonList("east"));

        // Test for FindClientCommand

        FindClientCommand findFirstClientCommand = new FindClientCommand(firstPredicate);
        FindClientCommand findSecondClientCommand = new FindClientCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstClientCommand.equals(findFirstClientCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindClientCommand(firstPredicate);
        assertTrue(findFirstClientCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstClientCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstClientCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstClientCommand.equals(findSecondClientCommand));

        // Test for FindHousekeeperCommand

        FindHousekeeperCommand findFirstHousekeeperCommand = new FindHousekeeperCommand(firstPredicate);
        FindHousekeeperCommand findSecondHousekeeperCommand = new FindHousekeeperCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstHousekeeperCommand.equals(findFirstHousekeeperCommand));

        // same values -> returns true
        FindCommand findFirstHousekeeperCommandCopy = new FindHousekeeperCommand(firstPredicate);
        assertTrue(findFirstHousekeeperCommand.equals(findFirstHousekeeperCommandCopy));

        // different types -> returns false
        assertFalse(findFirstHousekeeperCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstHousekeeperCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstHousekeeperCommand.equals(findSecondHousekeeperCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_CLIENTS_LISTED_OVERVIEW, 0);
        ContainsKeywordsPredicate predicate = preparePredicate("a", "2", "west");
        FindClientCommand clientCommand = new FindClientCommand(predicate);
        expectedModel.updateFilteredClientList(predicate);
        assertCommandSuccess(clientCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredClientList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_HOUSEKEEPERS_LISTED_OVERVIEW, 2);
        ContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz", "", "");
        FindHousekeeperCommand command = new FindHousekeeperCommand(predicate);
        expectedModel.updateFilteredHousekeeperList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA), model.getFilteredHousekeeperList());
    }

    @Test
    public void toStringMethod() {
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Arrays.asList("keyword1"),
                Arrays.asList("keyword2"), Arrays.asList("keyword3"));
        FindClientCommand findCommand = new FindClientCommand(predicate);
        String expected = FindClientCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code name}, {@code hub} and {@code area} into a {@code ContainsKeywordsPredicate}.
     */
    private ContainsKeywordsPredicate preparePredicate(String name, String address, String area) {
        return new ContainsKeywordsPredicate(Arrays.asList(name.split("\\s+")), Arrays.asList(address.split("\\s+")),
                Arrays.asList(area.split("\\s+")));
    }
}
