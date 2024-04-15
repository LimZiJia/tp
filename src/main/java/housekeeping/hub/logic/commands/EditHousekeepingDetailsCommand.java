package housekeeping.hub.logic.commands;

import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_LHD;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_PI;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import housekeeping.hub.commons.core.index.Index;
import housekeeping.hub.commons.util.CollectionUtil;
import housekeeping.hub.commons.util.ToStringBuilder;
import housekeeping.hub.logic.Messages;
import housekeeping.hub.logic.commands.exceptions.CommandException;
import housekeeping.hub.model.Model;
import housekeeping.hub.model.person.Address;
import housekeeping.hub.model.person.Area;
import housekeeping.hub.model.person.Booking;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Email;
import housekeeping.hub.model.person.HousekeepingDetails;
import housekeeping.hub.model.person.Name;
import housekeeping.hub.model.person.Person;
import housekeeping.hub.model.person.Phone;
import housekeeping.hub.model.tag.Tag;

/**
 * Edits the details of an existing person in the hub book.
 */
public class EditHousekeepingDetailsCommand extends BookingCommand {

    public static final String COMMAND_WORD = "booking client edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the booking details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PI + "PREFERRED INTERVAL] "
            + "[" + PREFIX_LHD + "LAST HOUSEKEEPING DATE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PI + "1 months "
            + PREFIX_LHD + "2024-01-02";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the hub book.";

    protected final Index index;
    protected final EditHousekeepingDetailsDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editHousekeepingDetailsDescriptor details to edit the person with
     */
    public EditHousekeepingDetailsCommand(Index index,
                                          EditHousekeepingDetailsDescriptor editHousekeepingDetailsDescriptor) {
        requireNonNull(index);
        requireNonNull(editHousekeepingDetailsDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditHousekeepingDetailsDescriptor(editHousekeepingDetailsDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client personToEdit = lastShownList.get(index.getZeroBased());
        if (!personToEdit.hasHousekeepingDetails()) {
            throw new CommandException(NO_DETAILS_MESSAGE_CONSTRAINT);
        }
        Client editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasClient(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setClient(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.formatClient(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editHousekeepingDetailsDescriptor}.
     */
    protected Client createEditedPerson(
            Person personToEdit,
            EditHousekeepingDetailsCommand.EditHousekeepingDetailsDescriptor editHousekeepingDetailsDescriptor) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();
        Area updatedArea = personToEdit.getArea();
        LocalDate updatedLastHousekeepingDate = editHousekeepingDetailsDescriptor.getLastHousekeepingDate()
                .orElse(personToEdit.getDetails().getLastHousekeepingDate());
        Period updatedPreferredInterval = editHousekeepingDetailsDescriptor.getPreferredInterval()
                .orElse(personToEdit.getDetails().getPreferredInterval());
        Period updatedDeferment = editHousekeepingDetailsDescriptor.getDeferment()
                .orElse(personToEdit.getDetails().getDeferment());
        Booking updatedBooking = editHousekeepingDetailsDescriptor.getBooking()
                .orElse(personToEdit.getDetails().getBooking());
        HousekeepingDetails updatedDetails =
                new HousekeepingDetails(updatedLastHousekeepingDate, updatedPreferredInterval);
        updatedDetails.addDeferment(updatedDeferment);
        updatedDetails.setBooking(updatedBooking);

        return new Client(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedDetails, updatedArea);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditHousekeepingDetailsCommand)) {
            return false;
        }

        EditHousekeepingDetailsCommand otherEditCommand = (EditHousekeepingDetailsCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the booking details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditHousekeepingDetailsDescriptor {
        private LocalDate lastHousekeepingDate;
        private Period preferredInterval;
        private Period deferment;

        private Booking booking;
        public EditHousekeepingDetailsDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditHousekeepingDetailsDescriptor(EditHousekeepingDetailsDescriptor toCopy) {
            setLastHousekeepingDate(toCopy.lastHousekeepingDate);
            setPreferredInterval(toCopy.preferredInterval);
            setDeferment(toCopy.deferment);
            setBooking(toCopy.booking);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(lastHousekeepingDate, preferredInterval, booking, deferment);
        }

        public void setLastHousekeepingDate(LocalDate lHD) {
            lastHousekeepingDate = lHD;
        }

        public Optional<LocalDate> getLastHousekeepingDate() {
            return Optional.ofNullable(lastHousekeepingDate);
        }

        public void setPreferredInterval(Period preferredInterval) {
            this.preferredInterval = preferredInterval;
        }

        public Optional<Period> getPreferredInterval() {
            return Optional.ofNullable(preferredInterval);
        }

        public void setDeferment(Period deferment) {
            this.deferment = deferment;
        }

        public Optional<Period> getDeferment() {
            return Optional.ofNullable(deferment);
        }
        public void setBooking(Booking booking) {
            this.booking = booking;
        }

        public Optional<Booking> getBooking() {
            return Optional.ofNullable(booking);
        }


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditHousekeepingDetailsDescriptor)) {
                return false;
            }

            EditHousekeepingDetailsDescriptor otherEditPersonDescriptor = (EditHousekeepingDetailsDescriptor) other;
            return Objects.equals(lastHousekeepingDate, otherEditPersonDescriptor.lastHousekeepingDate)
                    && Objects.equals(deferment, otherEditPersonDescriptor.deferment)
                    && Objects.equals(booking, otherEditPersonDescriptor.booking)
                    && Objects.equals(preferredInterval, otherEditPersonDescriptor.preferredInterval);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("last housekeeping date", lastHousekeepingDate)
                    .add("preferred interval", preferredInterval)
                    .add("booking date", booking)
                    .add("deferment", deferment)
                    .toString();
        }
    }
}
