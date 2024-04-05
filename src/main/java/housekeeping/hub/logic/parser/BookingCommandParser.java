package housekeeping.hub.logic.parser;

import static java.util.Objects.requireNonNull;
import static housekeeping.hub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.Period;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import housekeeping.hub.commons.core.index.Index;
import housekeeping.hub.logic.commands.BookingCommand;
import housekeeping.hub.logic.parser.exceptions.ParseException;
import housekeeping.hub.model.person.BookingSearchPredicate;
import housekeeping.hub.model.person.HousekeepingDetails;

public class BookingCommandParser implements Parser<BookingCommand> {
    private static final Pattern PATTERN_TYPE = Pattern.compile("^(client|housekeeper).*");
    private static final Pattern PATTERN_ADD = Pattern.compile(
            ".*add\\s+(\\d+)\\s+(\\d{4}-\\d{2}-\\d{2}\\s+(am|pm))");
    private static final Pattern PATTERN_DELETE_HOUSEKEEPER = Pattern.compile(".*delete\\s+(\\d+)\\s+(\\d+)");
    private static final Pattern PATTERN_DELETE_CLIENT = Pattern.compile(".*delete\\s+(\\d+)");
    private static final Pattern PATTERN_LIST = Pattern.compile(".*list\\s+(\\d+)");
    private static final Pattern PATTERN_SEARCH = Pattern.compile(
            ".*search\\s+(\\w+)\\s+(\\d{4}-\\d{2}-\\d{2})\\s+(\\w{2})");
    private static final Pattern PATTERN_SET = Pattern.compile(
            ".*set\\s+(\\d+)\\s+(\\d{4}-\\d{2}-\\d{2}\\s+\\d+\\s+(days|weeks|months|years))");

    private static final Pattern PATTERN_REMOVE = Pattern.compile(".*remove\\s+(\\d+)");
    private static final Pattern PATTERN_EDIT = Pattern.compile(".*edit\\s+(.*)");
    private static final Pattern PATTERN_DEFERMENT = Pattern.compile(
            ".*defer\\s+(\\d+)\\s+(\\d+\\s+(days|weeks|months|years))");
    private static final String CLIENT = "client";
    private static final String HOUSEKEEPER = "housekeeper";
    private static final String ADD_COMMAND = "add";
    private static final String DELETE_COMMAND = "delete";
    private static final String LIST_COMMAND = "list";
    private static final String DEFERMENT_COMMAND = "defer";
    private static final String SEARCH_COMMAND = "search";
    private static final String EDIT_LAST_HOUSEKEEPING_DATE_COMMAND = "last";
    private static final String EDIT_PREFERRED_INTERVAL_COMMAND = "interval";
    private static final String SET_HOUSEKEEPING_DETAILS_COMMAND = "set";
    private static final String REMOVE_HOUSEKEEPING_DETAILS_COMMAND = "remove";
    @Override
    public BookingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Matcher addMatcher = PATTERN_ADD.matcher(args.trim());
        Matcher deleteHousekeeperMatcher = PATTERN_DELETE_HOUSEKEEPER.matcher(args.trim());
        Matcher deleteClientMatcher = PATTERN_DELETE_CLIENT.matcher(args.trim());
        Matcher listMatcher = PATTERN_LIST.matcher(args.trim());
        Matcher searchMatcher = PATTERN_SEARCH.matcher(args.trim());
        Matcher typeMatcher = PATTERN_TYPE.matcher(args.trim());
        Matcher setMatcher = PATTERN_SET.matcher(args.trim());
        Matcher removeMatcher = PATTERN_REMOVE.matcher(args.trim());
        Matcher editMatcher = PATTERN_EDIT.matcher(args.trim());
        Matcher deferMatcher = PATTERN_DEFERMENT.matcher(args.trim());
        if (!typeMatcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookingCommand.MESSAGE_USAGE));
        } else if (typeMatcher.group(1).equals("client")) {
            return clientBookingCommandParser(addMatcher, deleteClientMatcher, listMatcher, setMatcher, removeMatcher,
                    editMatcher, deferMatcher, args);
        } else if (typeMatcher.group(1).equals("housekeeper")) {
            return housekeeperBookingCommandParser(addMatcher, deleteHousekeeperMatcher, listMatcher, searchMatcher);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookingCommand.MESSAGE_USAGE));
        }
    }

    private static BookingCommand clientBookingCommandParser
            (Matcher addMatcher, Matcher deleteMatcher, Matcher listMatcher,
             Matcher setMatcher, Matcher removeMatcher, Matcher editMatcher,
             Matcher deferMatcher, String args) throws ParseException {
        if (setMatcher.matches()) {
            Index clientIndex = ParserUtil.parseIndex(setMatcher.group(1));
            // Date and period is not really optional since it is guaranteed by the regex.
            HousekeepingDetails housekeepingDetails =
                    ParserUtil.parseHousekeepingDetails(Optional.of(setMatcher.group(2)));
            return new BookingCommand(CLIENT, SET_HOUSEKEEPING_DETAILS_COMMAND, clientIndex, housekeepingDetails);
        } else if (removeMatcher.matches()) {
            Index clientIndex = ParserUtil.parseIndex(removeMatcher.group(1));
            return new BookingCommand(CLIENT, REMOVE_HOUSEKEEPING_DETAILS_COMMAND, clientIndex);
        } else if (editMatcher.matches()) {
            return new EditHousekeepingDetailsParser().parse(args);
        } else if (deferMatcher.matches()) {
            Period deferment = ParserUtil.parsePreferredInterval(deferMatcher.group(2));
            Index clientIndex = ParserUtil.parseIndex(deferMatcher.group(1));
            return new BookingCommand(CLIENT, DEFERMENT_COMMAND, clientIndex, deferment);
        } else if (addMatcher.matches()) {
            Index clientIndex = ParserUtil.parseIndex(addMatcher.group(1));
            String bookedDateAndTime = addMatcher.group(2);
            return new BookingCommand(CLIENT, ADD_COMMAND, clientIndex, bookedDateAndTime);
        } else if (deleteMatcher.matches()) {
            Index clientIndex = ParserUtil.parseIndex(deleteMatcher.group(1));
            return new BookingCommand(CLIENT, DELETE_COMMAND, clientIndex);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookingCommand.MESSAGE_USAGE));
        }
    }

    private static BookingCommand housekeeperBookingCommandParser
            (Matcher addMatcher, Matcher deleteMatcher, Matcher listMatcher,
             Matcher searchMatcher) throws ParseException {
        if (addMatcher.matches()) {
            Index housekeeperIndex = ParserUtil.parseIndex(addMatcher.group(1));
            String bookedDateAndTime = addMatcher.group(2);
            return new BookingCommand(HOUSEKEEPER, ADD_COMMAND, housekeeperIndex, bookedDateAndTime);
        } else if (deleteMatcher.matches()) {
            Index housekeeperIndex = ParserUtil.parseIndex(deleteMatcher.group(1));
            int bookingToDeleteIndex = Integer.parseInt(deleteMatcher.group(2));
            return new BookingCommand(HOUSEKEEPER, DELETE_COMMAND, housekeeperIndex, bookingToDeleteIndex);
        } else if (listMatcher.matches()) {
            Index housekeeperIndex = ParserUtil.parseIndex(listMatcher.group(1));
            return new BookingCommand(HOUSEKEEPER, LIST_COMMAND, housekeeperIndex);
        } else if (searchMatcher.matches()){
            String area = searchMatcher.group(1);
            String bookedDateAndTime = searchMatcher.group(2) + " " + searchMatcher.group(3);
            BookingSearchPredicate bookingSearchPredicate = new BookingSearchPredicate(area, bookedDateAndTime);
            return new BookingCommand(HOUSEKEEPER, SEARCH_COMMAND, bookingSearchPredicate);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookingCommand.MESSAGE_USAGE));
        }
    }
}
