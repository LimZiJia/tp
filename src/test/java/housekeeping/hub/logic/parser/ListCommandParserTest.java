package housekeeping.hub.logic.parser;

import static housekeeping.hub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static housekeeping.hub.logic.parser.CommandParserTestUtil.assertParseFailure;
import static housekeeping.hub.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import housekeeping.hub.logic.commands.ListClientCommand;
import housekeeping.hub.logic.commands.ListCommand;
import housekeeping.hub.logic.commands.ListHousekeeperCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "clint", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "house keeper", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListCommand() {
        assertParseSuccess(parser, "client", new ListClientCommand());

        assertParseSuccess(parser, "housekeeper", new ListHousekeeperCommand());
    }

}
