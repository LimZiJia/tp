package housekeeping.hub.logic.parser;

import static housekeeping.hub.logic.Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX;
import static housekeeping.hub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static housekeeping.hub.logic.Messages.MESSAGE_INVALID_HOUSEKEEPER_DISPLAYED_INDEX;

import housekeeping.hub.commons.core.index.Index;
import housekeeping.hub.logic.commands.DeleteClientCommand;
import housekeeping.hub.logic.commands.DeleteCommand;
import housekeeping.hub.logic.commands.DeleteHousekeeperCommand;
import housekeeping.hub.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            String[] splitArgs = args.trim().split(" ");

            // check that input is valid
            if (splitArgs.length < 2 || splitArgs.length > 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            String type = ParserUtil.parseType(splitArgs[0]);
            Index index;

            if (type.equals("client")) {
                try {
                    if (Integer.parseInt(splitArgs[1]) <= 0) {
                        throw new ParseException(MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
                    }
                } catch (NumberFormatException e) {
                    throw new ParseException(MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
                }
                index = ParserUtil.parseIndex(splitArgs[1]);
                return new DeleteClientCommand(index);
            } else if (type.equals("housekeeper")) {
                try {
                    if (Integer.parseInt(splitArgs[1]) <= 0) {
                        throw new ParseException(MESSAGE_INVALID_HOUSEKEEPER_DISPLAYED_INDEX);
                    }
                } catch (NumberFormatException e) {
                    throw new ParseException(MESSAGE_INVALID_HOUSEKEEPER_DISPLAYED_INDEX);
                }
                index = ParserUtil.parseIndex(splitArgs[1]);
                return new DeleteHousekeeperCommand(index);
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }
    }

}
