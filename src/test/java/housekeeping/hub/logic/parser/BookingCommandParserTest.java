package housekeeping.hub.logic.parser;

import static housekeeping.hub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static housekeeping.hub.logic.parser.CommandParserTestUtil.assertParseFailure;
import static housekeeping.hub.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import housekeeping.hub.logic.commands.BookingCommand;
import housekeeping.hub.logic.commands.EditHousekeepingDetailsCommand;
import housekeeping.hub.logic.parser.exceptions.ParseException;
import housekeeping.hub.model.person.BookingSearchPredicate;
import housekeeping.hub.model.person.HousekeepingDetails;

public class BookingCommandParserTest {

    // Housekeeper add commands (invalid)
    private static final String INVALID_HOUSEKEEPER_ADD_MISSING_INDEX = "housekeeper add 2024-03-02 am";
    private static final String INVALID_HOUSEKEEPER_ADD_MISSING_DATE = "housekeeper add 1 am";
    private static final String INVALID_HOUSEKEEPER_ADD_MISSING_TIME = "housekeeper add 1 2024-03-02";
    private static final String INVALID_HOUSEKEEPER_ADD_MISSING_DATETIME = "housekeeper add 1";
    private static final String INVALID_HOUSEKEEPER_ADD_MISSING_PARAMETERS = "housekeeper add";

    private static final String INVALID_HOUSEKEEPER_ADD_ZERO_INDEX = "housekeeper add 0 2024-03-02 am";
    private static final String INVALID_HOUSEKEEPER_ADD_NEGATIVE_INDEX = "housekeeper add -20 2024-03-02 am";
    private static final String INVALID_HOUSEKEEPER_ADD_INVALID_MONTH = "housekeeper add 1 2024-33-02 am";
    private static final String INVALID_HOUSEKEEPER_ADD_INVALID_DAY = "housekeeper add 1 2024-03-99 am";
    private static final String INVALID_HOUSEKEEPER_ADD_INVALID_DATE = "housekeeper add 1 2024-02-31 am";
    private static final String INVALID_HOUSEKEEPER_ADD_INVALID_TIME = "housekeeper add 1 2024-03-02 ps";

    // Housekeeper delete commands (invalid)
    private static final String INVALID_HOUSEKEEPER_DELETE_MISSING_ONE_INDEX = "housekeeper delete 1";
    private static final String INVALID_HOUSEKEEPER_DELETE_MISSING_BOTH_INDEX = "housekeeper delete";

    private static final String INVALID_HOUSEKEEPER_DELETE_ZERO_HOUSEKEEPER_INDEX = "housekeeper delete 0 1";
    private static final String INVALID_HOUSEKEEPER_DELETE_ZERO_BOOKING_INDEX = "housekeeper delete 1 0";
    private static final String INVALID_HOUSEKEEPER_DELETE_NEGATIVE_HOUSEKEEPER_INDEX =
            "booking housekeeper delete -9 1";
    private static final String INVALID_HOUSEKEEPER_DELETE_NEGATIVE_BOOKING_INDEX = "housekeeper delete 1 -33";

    // Housekeeper list commands (invalid)
    private static final String INVALID_HOUSEKEEPER_LIST_MISSING_INDEX = "housekeeper list";
    private static final String INVALID_HOUSEKEEPER_LIST_ZERO_INDEX = "housekeeper list 0";
    private static final String INVALID_HOUSEKEEPER_LIST_NEGATIVE_INDEX = "housekeeper list -43";

    // Housekeeper search commands (invalid)
    private static final String INVALID_HOUSEKEEPER_SEARCH_MISSING_AREA = "housekeeper search 2023-02-12 pm";
    private static final String INVALID_HOUSEKEEPER_SEARCH_MISSING_DATE = "housekeeper search west pm";
    private static final String INVALID_HOUSEKEEPER_SEARCH_MISSING_TIME = "housekeeper search west 2023-02-12";
    private static final String INVALID_HOUSEKEEPER_SEARCH_MISSING_PARAMETERS = "housekeeper search";

    private static final String INVALID_HOUSEKEEPER_SEARCH_INVALID_AREA =
            "housekeeper search southpole 2023-02-12 pm";
    private static final String INVALID_HOUSEKEEPER_SEARCH_INVALID_MONTH =
            "housekeeper search west 2023-56-12 pm";
    private static final String INVALID_HOUSEKEEPER_SEARCH_INVALID_DAY =
            "housekeeper search west 2023-02-88 pm";
    private static final String INVALID_HOUSEKEEPER_SEARCH_INVALID_DATE =
            "housekeeper search west 2023-11-31 pm";
    private static final String INVALID_HOUSEKEEPER_SEARCH_INVALID_TIME =
            "housekeeper search west 2023-02-12 as";

    // Client add commands (invalid)
    private static final String INVALID_CLIENT_ADD_MISSING_INDEX = "client add 2024-05-11 pm";
    private static final String INVALID_CLIENT_ADD_MISSING_DATE = "client add 1 pm";
    private static final String INVALID_CLIENT_ADD_MISSING_TIME = "client add 1 2024-05-11";
    private static final String INVALID_CLIENT_ADD_MISSING_DATETIME = "client add 1";
    private static final String INVALID_CLIENT_ADD_MISSING_PARAMETERS = "client add";

    private static final String INVALID_CLIENT_ADD_ZERO_INDEX = "client add 0 2024-05-11 pm";
    private static final String INVALID_CLIENT_ADD_NEGATIVE_INDEX = "client add -43 2024-05-11 pm";
    private static final String INVALID_CLIENT_ADD_INVALID_MONTH = "client add 1 2024-53-11 pm";
    private static final String INVALID_CLIENT_ADD_INVALID_DAY = "client add 1 2024-05-38 pm";
    private static final String INVALID_CLIENT_ADD_INVALID_DATE = "client add 1 2024-02-30 pm";
    private static final String INVALID_CLIENT_ADD_INVALID_TIME = "client add 1 2024-05-11 LS";

    // Client delete commands (invalid)
    private static final String INVALID_CLIENT_DELETE_MISSING_INDEX = "client delete";
    private static final String INVALID_CLIENT_DELETE_ZERO_INDEX = "client delete 0";
    private static final String INVALID_CLIENT_DELETE_NEGATIVE_INDEX = "client delete -500";

    // Client set commands (invalid)
    private static final String INVALID_CLIENT_SET_MISSING_INDEX = "client set 2024-05-11 15 days";
    private static final String INVALID_CLIENT_SET_MISSING_DATE = "client set 3 15 days";
    private static final String INVALID_CLIENT_SET_MISSING_NUMBER = "client set 3 2024-05-11 days";
    private static final String INVALID_CLIENT_SET_MISSING_INTERVAL = "client set 3 2024-05-11 15";
    private static final String INVALID_CLIENT_SET_MISSING_ALL_PARAMETERS = "client set";

    private static final String INVALID_CLIENT_SET_ZERO_INDEX = "client set 0 2024-05-11 15 days";
    private static final String INVALID_CLIENT_SET_NEGATIVE_INDEX = "client set -55 2024-05-11 15 days";
    private static final String INVALID_CLIENT_SET_INVALID_MONTH = "client set 3 2024-72-11 15 days";
    private static final String INVALID_CLIENT_SET_INVALID_DAY = "client set 3 2024-05-83 15 days";
    private static final String INVALID_CLIENT_SET_INVALID_DATE = "client set 3 2024-02-31 15 days";
    private static final String INVALID_CLIENT_SET_NEGATIVE_NUMBER = "client set 3 2024-05-11 -170 days";
    private static final String INVALID_CLIENT_SET_INVALID_INTERVAL = "client set 3 2024-05-11 15 abcdef";

    // Client remove commands (invalid)
    private static final String INVALID_CLIENT_REMOVE_MISSING_INDEX = "client remove";
    private static final String INVALID_CLIENT_REMOVE_ZERO_INDEX = "client remove 0";
    private static final String INVALID_CLIENT_REMOVE_NEGATIVE_INDEX = "client remove -534";

    // Client edit last housekeeping date commands (invalid)
    private static final String INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_MISSING_INDEX =
            "client edit lhd/2024-05-12";
    private static final String INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_MISSING_PREFIX =
            "client edit 2 2024-05-12";
    private static final String INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_MISSING_DATE =
            "client edit 2 lhd/";
    private static final String INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_MISSING_ALL_PARAMETERS =
            "client edit";


    private static final String INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_ZERO_INDEX =
            "client edit 0 lhd/2024-05-12";
    private static final String INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_NEGATIVE_INDEX =
            "client edit -32 lhd/2024-05-12";
    private static final String INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_INVALID_PREFIX =
            "client edit 2 lasthkpdate/2024-05-12";
    private static final String INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_INVALID_MONTH =
            "client edit 2 lhd/2024-37-12";
    private static final String INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_INVALID_DAY =
            "client edit 2 lhd/2024-05-56";
    private static final String INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_INVALID_DATE =
            "client edit 2 lhd/2024-02-30";

    // Client edit preferred interval commands (invalid)
    private static final String INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_MISSING_INDEX = "client edit pi/2 weeks";
    private static final String INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_MISSING_PREFIX = "client edit 1 2 weeks";
    private static final String INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_MISSING_NUMBER = "client edit 1 pi/weeks";
    private static final String INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_MISSING_INTERVAL = "client edit 1 pi/2";

    private static final String INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_ZERO_INDEX = "client edit 0 pi/2 weeks";
    private static final String INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_NEGATIVE_INDEX = "client edit -87 pi/2 weeks";
    private static final String INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_INVALID_PREFIX = "client edit 1 prefInt/2 weeks";
    private static final String INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_NEGATIVE_NUMBER = "client edit 1 pi/-30 weeks";
    private static final String INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_INVALID_INTERVAL = "client edit 1 pi/2 year";

    // Client edit booking date commands (invalid)
    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_MISSING_INDEX = "client edit bd/2024-01-17 pm";
    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_MISSING_PREFIX = "client edit 7 2024-01-17 pm";
    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_MISSING_DATE = "client edit 7 bd/pm";
    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_MISSING_TIME = "client edit 7 bd/2024-01-17";
    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_MISSING_DATETIME = "client edit 7 bd/";

    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_ZERO_INDEX = "client edit 0 bd/2024-01-17 pm";
    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_NEGATIVE_INDEX = "client edit -77 bd/2024-01-17 pm";
    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_INVALID_PREFIX = "client edit 7 bookD/2024-01-17 pm";
    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_INVALID_MONTH = "client edit 7 bd/2024-77-17 pm";
    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_INVALID_DAY = "client edit 7 bd/2024-01-90 pm";
    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_INVALID_DATE = "client edit 7 bd/2024-02-31 pm";
    private static final String INVALID_CLIENT_EDIT_BOOKING_DATE_INVALID_TIME = "client edit 7 bd/2024-01-17 morning";

    // Client edit deferment commands (invalid)
    private static final String INVALID_CLIENT_EDIT_DEFERMENT_MISSING_INDEX = "client edit d/1 months";
    private static final String INVALID_CLIENT_EDIT_DEFERMENT_MISSING_PREFIX = "client edit 10 1 months";
    private static final String INVALID_CLIENT_EDIT_DEFERMENT_MISSING_NUMBER = "client edit 10 d/months";
    private static final String INVALID_CLIENT_EDIT_DEFERMENT_MISSING_INTERVAL = "client edit 10 d/1";
    private static final String INVALID_TYPE = "abc list 1";
    private static final String INVALID_ACTION_WORD = "client adding 1 2023-02-03 pm";

    private static final String INVALID_CLIENT_EDIT_DEFERMENT_ZERO_INDEX = "client edit 0 d/1 months";
    private static final String INVALID_CLIENT_EDIT_DEFERMENT_NEGATIVE_INDEX = "client edit -10 d/1 months";
    private static final String INVALID_CLIENT_EDIT_DEFERMENT_INVALID_PREFIX = "client edit 10 deferBy/1 months";
    private static final String INVALID_CLIENT_EDIT_DEFERMENT_NEGATIVE_NUMBER = "client edit 10 d/-901 months";
    private static final String INVALID_CLIENT_EDIT_DEFERMENT_INVALID_INTERVAL = "client edit 10 d/1 decades";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookingCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditHousekeepingDetailsCommand.MESSAGE_USAGE);


    // Invalid format commands
    private static final String MISSING_TYPE = "list 1";
    private static final String MISSING_ACTION_WORD = "housekeeper 1";
    private static final String MISSING_PARAMETERS = "";

    // Client commands (valid)
    private static final String VALID_CLIENT_ADD = "client add 1 2024-05-11 pm";
    private static final String VALID_CLIENT_DELETE = "client delete 3";
    private static final String VALID_CLIENT_SET = "client set 3 2024-05-11 15 days";
    private static final String VALID_CLIENT_REMOVE = "client remove 3";
    private static final String VALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE = "client edit 2 lhd/2024-05-12";
    private static final String VALID_CLIENT_EDIT_PREFERRED_INTERVAL = "client edit 1 pi/2 weeks";
    private static final String VALID_CLIENT_EDIT_BOOKING_DATE = "client edit 7 bd/2024-01-17 pm";
    private static final String VALID_CLIENT_EDIT_DEFERMENT = "client edit 10 d/1 months";

    // Housekeeper commands (valid)
    private static final String VALID_HOUSEKEEPER_ADD = "housekeeper add 1 2024-03-02 am";
    private static final String VALID_HOUSEKEEPER_DELETE = "housekeeper delete 1 1";
    private static final String VALID_HOUSEKEEPER_LIST = "housekeeper list 1";
    private static final String VALID_HOUSEKEEPER_SEARCH = "housekeeper search west 2023-02-12 pm";

    private BookingCommandParser parser = new BookingCommandParser();

    @Test
    public void parse_housekeeperValidCommands_success() throws ParseException {
        assertParseSuccess(parser, VALID_HOUSEKEEPER_ADD, new BookingCommand("housekeeper", "add",
                ParserUtil.parseIndex("1"), "2024-03-02 am"));
        assertParseSuccess(parser, VALID_HOUSEKEEPER_DELETE, new BookingCommand("housekeeper", "delete",
                ParserUtil.parseIndex("1"), 1));
        assertParseSuccess(parser, VALID_HOUSEKEEPER_LIST, new BookingCommand("housekeeper", "list",
                ParserUtil.parseIndex("1")));
        assertParseSuccess(parser, VALID_HOUSEKEEPER_SEARCH, new BookingCommand("housekeeper", "search",
                new BookingSearchPredicate("west", "2023-02-12 pm")));
    }

    @Test
    public void parse_housekeeperAdd_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_HOUSEKEEPER_ADD_MISSING_INDEX, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_ADD_MISSING_DATE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_ADD_MISSING_TIME, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_ADD_MISSING_DATETIME, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_ADD_MISSING_PARAMETERS, MESSAGE_INVALID_FORMAT);

        // test invalid parameters
        assertParseFailure(parser, INVALID_HOUSEKEEPER_ADD_ZERO_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_ADD_NEGATIVE_INDEX, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_ADD_INVALID_TIME, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_housekeeperDelete_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_HOUSEKEEPER_DELETE_MISSING_ONE_INDEX, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_DELETE_MISSING_BOTH_INDEX, MESSAGE_INVALID_FORMAT);

        // test invalid parameters
        assertParseFailure(parser, INVALID_HOUSEKEEPER_DELETE_ZERO_HOUSEKEEPER_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_DELETE_NEGATIVE_HOUSEKEEPER_INDEX, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_DELETE_NEGATIVE_BOOKING_INDEX, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_housekeeperList_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_HOUSEKEEPER_LIST_MISSING_INDEX, MESSAGE_INVALID_FORMAT);

        // test invalid parameters
        assertParseFailure(parser, INVALID_HOUSEKEEPER_LIST_ZERO_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_LIST_NEGATIVE_INDEX, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_housekeeperSearch_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_HOUSEKEEPER_SEARCH_MISSING_AREA, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_SEARCH_MISSING_DATE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_SEARCH_MISSING_TIME, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_HOUSEKEEPER_SEARCH_MISSING_PARAMETERS, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_clientAdd_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_CLIENT_ADD_MISSING_INDEX, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_CLIENT_ADD_MISSING_DATE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_CLIENT_ADD_MISSING_TIME, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_CLIENT_ADD_MISSING_DATETIME, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_CLIENT_ADD_MISSING_PARAMETERS, MESSAGE_INVALID_FORMAT);

        // test invalid parameters
        assertParseFailure(parser, INVALID_CLIENT_ADD_ZERO_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, INVALID_CLIENT_ADD_NEGATIVE_INDEX, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_CLIENT_ADD_INVALID_TIME, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_clientDelete_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_CLIENT_DELETE_MISSING_INDEX, MESSAGE_INVALID_FORMAT);

        // test invalid parameters
        assertParseFailure(parser, INVALID_CLIENT_DELETE_ZERO_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, INVALID_CLIENT_DELETE_NEGATIVE_INDEX, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_clientSet_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_CLIENT_SET_MISSING_INDEX, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_CLIENT_SET_MISSING_DATE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_CLIENT_SET_MISSING_NUMBER, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_CLIENT_SET_MISSING_INTERVAL, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_CLIENT_SET_MISSING_ALL_PARAMETERS, MESSAGE_INVALID_FORMAT);

        // test invalid parameters
        assertParseFailure(parser, INVALID_CLIENT_SET_ZERO_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, INVALID_CLIENT_SET_NEGATIVE_INDEX, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_CLIENT_SET_INVALID_INTERVAL, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_clientRemove_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_CLIENT_REMOVE_MISSING_INDEX, MESSAGE_INVALID_FORMAT);

        // test invalid parameters
        assertParseFailure(parser, INVALID_CLIENT_REMOVE_ZERO_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, INVALID_CLIENT_REMOVE_NEGATIVE_INDEX, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_clientEditLastHousekeepingDate_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_MISSING_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_MISSING_PREFIX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_MISSING_DATE,
                HousekeepingDetails.MESSAGE_CONSTRAINTS);
        assertParseFailure(
                parser, INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_MISSING_ALL_PARAMETERS,
                MESSAGE_INVALID_FORMAT);

        // test invalid parameters
        assertParseFailure(parser, INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_ZERO_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_NEGATIVE_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_LAST_HOUSEKEEPING_DATE_INVALID_PREFIX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
    }

    @Test
    public void parse_clientEditPreferredInterval_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_MISSING_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_MISSING_PREFIX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);

        // test invalid parameters
        assertParseFailure(parser, INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_ZERO_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_NEGATIVE_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_INVALID_PREFIX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_PREFERRED_INTERVAL_INVALID_INTERVAL,
                HousekeepingDetails.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_clientEditBookingDate_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_CLIENT_EDIT_BOOKING_DATE_MISSING_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_BOOKING_DATE_MISSING_PREFIX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_BOOKING_DATE_MISSING_DATE,
                HousekeepingDetails.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_BOOKING_DATE_MISSING_TIME,
                HousekeepingDetails.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_BOOKING_DATE_MISSING_DATETIME,
                HousekeepingDetails.MESSAGE_CONSTRAINTS);

        // test invalid parameters
        assertParseFailure(parser, INVALID_CLIENT_EDIT_BOOKING_DATE_ZERO_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_BOOKING_DATE_NEGATIVE_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_BOOKING_DATE_INVALID_PREFIX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_BOOKING_DATE_INVALID_TIME,
                HousekeepingDetails.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_clientEditDeferment_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, INVALID_CLIENT_EDIT_DEFERMENT_MISSING_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_DEFERMENT_MISSING_PREFIX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);

        // test invalid parameters
        assertParseFailure(parser, INVALID_CLIENT_EDIT_DEFERMENT_ZERO_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_DEFERMENT_NEGATIVE_INDEX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_DEFERMENT_INVALID_PREFIX,
                MESSAGE_INVALID_FORMAT_EDIT_HOUSEKEEPING_DETAILS_COMMAND);
        assertParseFailure(parser, INVALID_CLIENT_EDIT_DEFERMENT_INVALID_INTERVAL,
                HousekeepingDetails.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidFormatCommands_throwParseException() throws ParseException {
        // test missing parameters
        assertParseFailure(parser, MISSING_ACTION_WORD, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, MISSING_TYPE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, MISSING_PARAMETERS, MESSAGE_INVALID_FORMAT);

        // test invalid parameters
        assertParseFailure(parser, INVALID_TYPE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_ACTION_WORD, MESSAGE_INVALID_FORMAT);
    }
}
