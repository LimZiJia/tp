package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_HOUSEKEEPERS;

import java.time.format.DateTimeParseException;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.BookingList;
import seedu.address.model.person.Housekeeper;

/**
 * Encapsulates booking actions (add, delete, list) for a housekeeper.
 */
public class BookingCommand extends Command {

    public static final String COMMAND_WORD = "booking";
    public static final String ACTION_WORD_ADD = "add";
    public static final String ACTION_WORD_DELETE = "delete";
    public static final String ACTION_WORD_LIST = "list";
    public static final String MESSAGE_INVALID_ACTION = "Invalid action. Action words include {add, delete, list}.";

    public static final String MESSAGE_USAGE = "Please refer to the command formats:\n\n" + COMMAND_WORD + " " + ACTION_WORD_ADD
            + ": adds a booking for the housekeeper identified "
            + "by the index number used in the displayed housekeeper list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[DATE] [TIME]\n"
            + "Example: " + COMMAND_WORD + " " + ACTION_WORD_ADD + " 1 2024-05-12 am\n\n"
            + COMMAND_WORD + " " + ACTION_WORD_DELETE
            + ": deletes the specified booking for the housekeeper identified "
            + "by the index numbers used in the booking list and displayed housekeeper list respectively.\n"
            + "Parameters: HOUSEKEEPER_INDEX (must be a positive integer) BOOKING_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + ACTION_WORD_DELETE + " 1 1\n\n"
            + COMMAND_WORD + " " + ACTION_WORD_LIST
            + ": lists all bookings for the housekeeper identified "
            + "by the index number used in the displayed housekeeper list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + ACTION_WORD_LIST + " 1\n";

    private String actionWord;
    protected Index housekeeperIndex;
    private int bookingToDeleteIndex;
    private String bookedDateAndTime;

    /**
     * Constructs a BookingCommand for the "add" action.
     *
     * @param actionWord "add"
     * @param index of housekeeper to add booking to
     * @param bookedDateAndTime in the form of a string
     */
    public BookingCommand(String actionWord, Index index, String bookedDateAndTime) {
        requireNonNull(index);
        this.actionWord = actionWord;
        this.housekeeperIndex = index;
        this.bookedDateAndTime = bookedDateAndTime;
    }

    /**
     * Constructs a BookingCommand for the "delete" action.
     *
     * @param actionWord "delete"
     * @param index of housekeeper to delete booking from
     * @param bookingToDeleteIndex of booking to delete
     */
    public BookingCommand(String actionWord, Index index, int bookingToDeleteIndex) {
        requireNonNull(index);
        this.actionWord = actionWord;
        this.housekeeperIndex = index;
        this.bookingToDeleteIndex = bookingToDeleteIndex;
    }

    /**
     * Constructs a BookingCommand for the "list" action.
     *
     * @param actionWord "list"
     * @param index of housekeeper whose bookings to list
     */
    public BookingCommand(String actionWord, Index index) {
        requireNonNull(index);
        this.actionWord = actionWord;
        this.housekeeperIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        switch (actionWord) {
        case "add":
            requireNonNull(model);
            List<Housekeeper> lastShownListAdd = model.getFilteredHousekeeperList();

            if (housekeeperIndex.getZeroBased() >= lastShownListAdd.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            try {
                Housekeeper housekeeperToAddBooking = lastShownListAdd.get(housekeeperIndex.getZeroBased());
                if (housekeeperToAddBooking.hasDuplicateBooking(bookedDateAndTime)) {
                    throw new CommandException(housekeeperToAddBooking.getName() + " " + BookingList.MESSAGE_DUPLICATE);
                }
                String addResult = housekeeperToAddBooking.addBooking(bookedDateAndTime);

                // edit housekeeper with updated booking list
                EditCommand.EditPersonDescriptor editHousekeeperDescriptor = new EditCommand.EditPersonDescriptor();
                editHousekeeperDescriptor.setBookingList(housekeeperToAddBooking.getBookingList());
                EditHousekeeperCommand command = new EditHousekeeperCommand(housekeeperIndex, editHousekeeperDescriptor);
                Housekeeper editedHousekeeper = command.createEditedPerson(housekeeperToAddBooking, editHousekeeperDescriptor);

                model.setPerson(housekeeperToAddBooking, editedHousekeeper);
                model.updateFilteredHousekeeperList(PREDICATE_SHOW_ALL_HOUSEKEEPERS);

                return new CommandResult(String.format(addResult, Messages.format(housekeeperToAddBooking)));
            } catch (DateTimeParseException e) {
                throw new CommandException(e.getMessage());
            } catch (IllegalArgumentException e) {
                throw new CommandException(e.getMessage());
            }
        case "delete":
            requireNonNull(model);
            List<Housekeeper> lastShownListDelete = model.getFilteredHousekeeperList();

            if (housekeeperIndex.getZeroBased() >= lastShownListDelete.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Housekeeper housekeeperToDeleteBooking = lastShownListDelete.get(housekeeperIndex.getZeroBased());
            if (!housekeeperToDeleteBooking.isValidDeleteIndex(bookingToDeleteIndex)) {
                throw new CommandException((BookingList.MESSAGE_INVALID_DELETE));
            }

            if (bookingToDeleteIndex == 0) {
                throw new CommandException(BookingList.MESSAGE_INVALID_DELETE);
            }

            String deleteResult = housekeeperToDeleteBooking.deleteBooking(bookingToDeleteIndex);
            return new CommandResult(deleteResult);
        case "list":
            requireNonNull(model);
            List<Housekeeper> lastShownListList = model.getFilteredHousekeeperList();

            if (housekeeperIndex.getZeroBased() >= lastShownListList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Housekeeper housekeeperToListBooking = lastShownListList.get(housekeeperIndex.getZeroBased());
            String listResult = housekeeperToListBooking.listBooking();
            return new CommandResult(listResult);
        default:
            throw new CommandException(MESSAGE_INVALID_ACTION);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookingCommand)) {
            return false;
        }

        BookingCommand otherBookingCommand = (BookingCommand) other;
        return actionWord.equals(otherBookingCommand.actionWord)
                && housekeeperIndex.equals(otherBookingCommand.housekeeperIndex)
                && (bookingToDeleteIndex == otherBookingCommand.bookingToDeleteIndex)
                && bookedDateAndTime.equals(otherBookingCommand.bookedDateAndTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("actionWord", actionWord)
                .add("housekeeperIndex", housekeeperIndex)
                .add("bookingToDeleteIndex", bookingToDeleteIndex)
                .add("bookedDateAndTime", bookedDateAndTime)
                .toString();
    }
}
