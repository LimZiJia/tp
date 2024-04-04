package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.person.Type.preambleIsAllowed;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddClientCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddHousekeeperCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.Address;
import seedu.address.model.person.BookingList;
import seedu.address.model.person.Area;
import seedu.address.model.person.Client;
import seedu.address.model.person.Email;
import seedu.address.model.person.HousekeepingDetails;
import seedu.address.model.person.Housekeeper;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Type;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_DETAILS, PREFIX_AREA);               

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_AREA)
                || !preambleIsAllowed(argMultimap.getPreamble())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_AREA);
        String type = ParserUtil.parseType(argMultimap.getPreamble());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Area area = ParserUtil.parseArea(argMultimap.getValue(PREFIX_AREA).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        HousekeepingDetails details;
        BookingList bookingList = new BookingList();

        try {
            details = ParserUtil.parseHousekeepingDetails(argMultimap.getValue(PREFIX_DETAILS));
        } catch (ParseException e) {
            throw new ParseException(e.getMessage());
        }

        System.out.println(details);
        switch (type) {
        case "client":
            Client client = new Client(name, phone, email, address, tagList, details, area);
            return new AddClientCommand(client);
        case "housekeeper":
            if (!details.isEmpty()) {
                System.out.println(details);
                throw new ParseException(AddHousekeeperCommand.MESSAGE_NO_HOUSEKEEPING_DETAILS);
            }
            Housekeeper housekeeper = new Housekeeper(name, phone, email, address, tagList, area, bookingList);
            return new AddHousekeeperCommand(housekeeper);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
