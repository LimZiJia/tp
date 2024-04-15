package housekeeping.hub.logic.commands;

import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_AREA;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_EMAIL;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_NAME;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_PHONE;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_TAG;
import static housekeeping.hub.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import housekeeping.hub.commons.core.index.Index;
import housekeeping.hub.logic.commands.exceptions.CommandException;
import housekeeping.hub.model.AddressBook;
import housekeeping.hub.model.Model;
import housekeeping.hub.model.person.Booking;
import housekeeping.hub.model.person.BookingList;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.ContainsKeywordsPredicate;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.testutil.EditHousekeepingDetailsDescriptorBuilder;
import housekeeping.hub.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_AREA_AMY = "north";
    public static final String VALID_AREA_BOB = "south";
    public static final String VALID_LHD_BOB = "2024-01-01";
    public static final String VALID_PI_BOB = "P2M";
    public static final String VALID_DEFERMENT_BOB = "P1M";
    public static final String VALID_BOOKING_BOB = "2024-01-01 am";
    public static final BookingList VALID_BOOKING_LIST_BOB = new BookingList();


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String AREA_DESC_AMY = " " + PREFIX_AREA + VALID_AREA_AMY;
    public static final String AREA_DESC_BOB = " " + PREFIX_AREA + VALID_AREA_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_AREA_DESC = " " + PREFIX_AREA + "central"; // 'northwest' not allowed in area

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditHousekeepingDetailsCommand.EditHousekeepingDetailsDescriptor DESCC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    static {
        DESCC_BOB = new EditHousekeepingDetailsDescriptorBuilder().withBookingDate(new Booking(VALID_BOOKING_BOB))
                .withDeferment(Period.parse(VALID_DEFERMENT_BOB)).withPreferredInterval(Period.parse(VALID_PI_BOB))
                .withLastHousekeepingDate(LocalDate.parse(VALID_LHD_BOB)).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the hub book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Client> expectedFilteredClientList = new ArrayList<>(actualModel.getFilteredClientList());
        List<Housekeeper> expectedFilteredHousekeeperList = new ArrayList<>(actualModel.getFilteredHousekeeperList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredClientList, actualModel.getFilteredClientList());
        assertEquals(expectedFilteredHousekeeperList, actualModel.getFilteredHousekeeperList());
    }

    /**
     * Updates {@code model}'s filtered client list to show only the client at the given {@code targetIndex} in the
     * {@code model}'s hub book.
     */
    public static void showClientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredClientList().size());

        Client client = model.getFilteredClientList().get(targetIndex.getZeroBased());
        final String[] splitName = client.getName().fullName.split("\\s+");
        final List<String> splitAr = new ArrayList<>();
        splitAr.add("");
        final List<String> splitAd = new ArrayList<>();
        splitAd.add("");
        model.updateFilteredClientList(new ContainsKeywordsPredicate(Arrays.asList(splitName[0]),
                splitAd, splitAr));

        assertEquals(1, model.getFilteredClientList().size());
    }

    /**
     * Updates {@code model}'s filtered housekeeper list to show only the housekeeper at the given {@code targetIndex}
     * in the {@code model}'s hub book.
     */
    public static void showHousekeeperAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredHousekeeperList().size());

        Housekeeper housekeeper = model.getFilteredHousekeeperList().get(targetIndex.getZeroBased());
        final String[] splitName = housekeeper.getName().fullName.split("\\s+");
        final List<String> splitAr = new ArrayList<>();
        splitAr.add("");
        final List<String> splitAd = new ArrayList<>();
        splitAd.add("");
        model.updateFilteredHousekeeperList(new ContainsKeywordsPredicate(Arrays.asList(splitName[0]),
                splitAd, splitAr));

        assertEquals(1, model.getFilteredHousekeeperList().size());
    }
}
