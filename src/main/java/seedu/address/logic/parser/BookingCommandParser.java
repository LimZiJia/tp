package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BookingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class BookingCommandParser implements Parser<BookingCommand> {
    private static final Pattern PATTERN_ADD = Pattern.compile(
            ".*add\\s+(\\d+)\\s+(\\d{4}-\\d{2}-\\d{2})\\s+(\\w{2})");
    private static final Pattern PATTERN_DELETE = Pattern.compile(".*delete\\s+(\\d+)\\s+(\\d+)");
    private static final Pattern PATTERN_LIST = Pattern.compile(".*list\\s+(\\d+)");
    private static final Pattern PATTERN_TYPE = Pattern.compile("^(client|housekeeper).*");
    private static final String ADD_COMMAND = "add";
    private static final String DELETE_COMMAND = "delete";
    private static final String LIST_COMMAND = "list";
    private static final String EDIT_LAST_HOUSEKEEPING_DATE_COMMAND = "last";
    private static final String EDIT_PREFERRED_INTERVAL_COMMAND = "interval";
    private static final String SET_HOUSEKEEPING_DETAILS_COMMAND = "set";
    private static final String REMOVE_HOUSEKEEPING_DETAILS_COMMAND = "remove";
    @Override
    public BookingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Matcher addMatcher = PATTERN_ADD.matcher(args.trim());
        Matcher deleteMatcher = PATTERN_DELETE.matcher(args.trim());
        Matcher listMatcher = PATTERN_LIST.matcher(args.trim());
        Matcher typeMatcher = PATTERN_TYPE.matcher(args.trim());
        if (!typeMatcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookingCommand.MESSAGE_USAGE));
        } else if (typeMatcher.group(1).equals("client")) {
            return clientBookingCommandParser(addMatcher, deleteMatcher, listMatcher);
        } else if (typeMatcher.group(1).equals("housekeeper")) {
            return housekeeperBookingCommandParser(addMatcher, deleteMatcher, listMatcher);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookingCommand.MESSAGE_USAGE));
        }
    }

    private static BookingCommand clientBookingCommandParser
            (Matcher addMatcher, Matcher deleteMatcher, Matcher listMatcher) throws ParseException {
        // Place holder
        return new BookingCommand("client", "add", Index.fromOneBased(1), "2024-05-12 am");
    }


    private static BookingCommand housekeeperBookingCommandParser
            (Matcher addMatcher, Matcher deleteMatcher, Matcher listMatcher) throws ParseException {
        if (addMatcher.matches()) {
            String actionWord = "add";
            Index housekeeperIndex = ParserUtil.parseIndex(addMatcher.group(1));
            String bookedDateAndTime = addMatcher.group(2) + " " + addMatcher.group(3);
            return new BookingCommand("housekeeper", actionWord, housekeeperIndex, bookedDateAndTime);
        } else if (deleteMatcher.matches()) {
            String actionWord = "delete";
            Index housekeeperIndex = ParserUtil.parseIndex(deleteMatcher.group(1));
            int bookingToDeleteIndex = Integer.parseInt(deleteMatcher.group(2));
            return new BookingCommand("housekeeper", actionWord, housekeeperIndex, bookingToDeleteIndex);
        } else if (listMatcher.matches()) {
            String actionWord = "list";
            Index housekeeperIndex = ParserUtil.parseIndex(listMatcher.group(1));
            return new BookingCommand("housekeeper", actionWord, housekeeperIndex);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookingCommand.MESSAGE_USAGE));
        }
    }
}
