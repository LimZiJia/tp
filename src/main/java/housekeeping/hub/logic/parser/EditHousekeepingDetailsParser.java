package housekeeping.hub.logic.parser;

import static housekeeping.hub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_BD;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_DEFERMENT;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_LHD;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_PI;
import static java.util.Objects.requireNonNull;

import housekeeping.hub.commons.core.index.Index;
import housekeeping.hub.logic.commands.EditHousekeepingDetailsCommand;
import housekeeping.hub.logic.commands.EditHousekeepingDetailsCommand.EditHousekeepingDetailsDescriptor;
import housekeeping.hub.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditHousekeepingDetailsParser implements Parser<EditHousekeepingDetailsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditHousekeepingDetailsCommand
     * and returns an EditHousekeepingDetailsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditHousekeepingDetailsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LHD, PREFIX_PI, PREFIX_BD,
                PREFIX_DEFERMENT);
        Index index;
        try {
            String[] splitArgs = argMultimap.getPreamble().trim().split("edit ");
            index = ParserUtil.parseIndex(splitArgs[1]);
        } catch (Exception pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditHousekeepingDetailsCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LHD, PREFIX_PI, PREFIX_BD, PREFIX_DEFERMENT);

        EditHousekeepingDetailsDescriptor editPersonDescriptor = new EditHousekeepingDetailsDescriptor();

        if (argMultimap.getValue(PREFIX_LHD).isPresent()) {
            editPersonDescriptor.setLastHousekeepingDate(ParserUtil
                    .parseLastHousekeepingDate(argMultimap.getValue(PREFIX_LHD).get()));
        }
        if (argMultimap.getValue(PREFIX_PI).isPresent()) {
            editPersonDescriptor.setPreferredInterval(ParserUtil
                    .parsePreferredInterval(argMultimap.getValue(PREFIX_PI).get()));
        }
        if (argMultimap.getValue(PREFIX_BD).isPresent()) {
            editPersonDescriptor.setBooking(ParserUtil
                    .parseBooking(argMultimap.getValue(PREFIX_BD).get()));
        }
        if (argMultimap.getValue(PREFIX_DEFERMENT).isPresent()) {
            editPersonDescriptor.setDeferment(ParserUtil
                    .parsePreferredInterval(argMultimap.getValue(PREFIX_DEFERMENT).get()));
        }
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditHousekeepingDetailsCommand.MESSAGE_NOT_EDITED);
        }
        return new EditHousekeepingDetailsCommand(index, editPersonDescriptor);
    }

}
