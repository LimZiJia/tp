package housekeeping.hub.logic.parser;

import static housekeeping.hub.logic.parser.CommandParserTestUtil.assertParseFailure;
import static housekeeping.hub.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static housekeeping.hub.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import housekeeping.hub.logic.Messages;
import housekeeping.hub.logic.commands.DeleteClientCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "client 1", new DeleteClientCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "client a", String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
    }
}
