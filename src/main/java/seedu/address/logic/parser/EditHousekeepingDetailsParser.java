package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LHD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PI;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditHousekeepingDetailsCommand;
import seedu.address.logic.commands.EditHousekeepingDetailsCommand.EditHousekeepingDetailsDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditHousekeepingDetailsParser implements Parser<EditHousekeepingDetailsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditHousekeepingDetailsCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditHousekeepingDetailsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LHD, PREFIX_PI);
        Index index;
        try {
            String[] splitArgs = argMultimap.getPreamble().trim().split("edit ");
            index = ParserUtil.parseIndex(splitArgs[1]);
        } catch (Exception pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditHousekeepingDetailsCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LHD, PREFIX_PI);

        EditHousekeepingDetailsDescriptor editPersonDescriptor = new EditHousekeepingDetailsDescriptor();

        if (argMultimap.getValue(PREFIX_LHD).isPresent()) {
            editPersonDescriptor.setLastHousekeepingDate(ParserUtil
                    .parseLastHousekeepingDate(argMultimap.getValue(PREFIX_LHD).get()));
        }
        if (argMultimap.getValue(PREFIX_PI).isPresent()) {
            editPersonDescriptor.setPreferredInterval(ParserUtil
                    .parsePreferredInterval(argMultimap.getValue(PREFIX_PI).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditHousekeepingDetailsCommand.MESSAGE_NOT_EDITED);
        }

        return new EditHousekeepingDetailsCommand(index, editPersonDescriptor);
    }

}
