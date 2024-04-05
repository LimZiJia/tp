package housekeeping.hub.logic.parser;

import static housekeeping.hub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static housekeeping.hub.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static housekeeping.hub.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.AREA_DESC_AMY;
import static housekeeping.hub.logic.commands.CommandTestUtil.AREA_DESC_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static housekeeping.hub.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static housekeeping.hub.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static housekeeping.hub.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static housekeeping.hub.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static housekeeping.hub.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static housekeeping.hub.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static housekeeping.hub.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static housekeeping.hub.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static housekeeping.hub.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static housekeeping.hub.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static housekeeping.hub.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_EMAIL;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_NAME;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_PHONE;
import static housekeeping.hub.logic.parser.CommandParserTestUtil.assertParseFailure;
import static housekeeping.hub.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static housekeeping.hub.testutil.TypicalPersons.AMY;
import static housekeeping.hub.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import housekeeping.hub.logic.Messages;
import housekeeping.hub.logic.commands.AddClientCommand;
import housekeeping.hub.logic.commands.AddCommand;
import housekeeping.hub.logic.commands.AddHousekeeperCommand;
import housekeeping.hub.model.person.Address;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Email;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.model.person.Name;
import housekeeping.hub.model.person.Phone;
import housekeeping.hub.model.tag.Tag;
import housekeeping.hub.testutil.ClientBuilder;
import housekeeping.hub.testutil.HousekeeperBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Housekeeper expectedHousekeeper = new HousekeeperBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, "housekeeper " + PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + AREA_DESC_BOB,
                new AddHousekeeperCommand(expectedHousekeeper));


        // multiple tags - all accepted
        Client expectedClientMultipleTags = new ClientBuilder(AMY).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, "client " + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + AREA_DESC_AMY
                        + TAG_DESC_FRIEND, new AddClientCommand(expectedClientMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + AREA_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // multiple fields repeated
        assertParseFailure(parser, validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedPersonString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // invalid hub
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // invalid hub
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Client expectedClient = new ClientBuilder(AMY).withTags().build();
        assertParseSuccess(parser, "client " + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        +  AREA_DESC_AMY,
                new AddClientCommand(expectedClient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing hub prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "housekeeper " + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + AREA_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, "housekeeper " + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + AREA_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, "housekeeper " + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + AREA_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, "housekeeper " + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + AREA_DESC_BOB, Address.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, "housekeeper " + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + AREA_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + AREA_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
