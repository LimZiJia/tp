package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindClientCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindClientCommand expectedFindCommand =
                new FindClientCommand(new ContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"),
                        Arrays.asList("Clementi", "Jurong"), Arrays.asList("west", "east")));
        assertParseSuccess(parser, "client n/Alice Bob a/Clementi Jurong ar/west east", expectedFindCommand);

        // multiple whitespaces between keywords
        //assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

}
