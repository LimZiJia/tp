package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_HOUSEKEEPERS;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditHousekeepingDetailsCommand extends BookingCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    protected final Index index;
    protected final EditHousekeepingDetailsDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditHousekeepingDetailsCommand(Index index, EditHousekeepingDetailsDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditHousekeepingDetailsDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Client personToEdit = lastShownList.get(index.getZeroBased());
        Client editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    protected Client createEditedPerson
    (Person personToEdit, EditHousekeepingDetailsCommand.EditHousekeepingDetailsDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();
        Type updatedType = personToEdit.getType();
        Area updatedArea = personToEdit.getArea();
        LocalDate updatedLastHousekeepingDate = editPersonDescriptor.getLastHousekeepingDate()
                .orElse(personToEdit.getDetails().getLastHousekeepingDate());
        Period updatedPreferredInterval = editPersonDescriptor.getPreferredInterval()
                .orElse(personToEdit.getDetails().getPreferredInterval());
        HousekeepingDetails updatedDetails =
                new HousekeepingDetails(updatedLastHousekeepingDate, updatedPreferredInterval);
        updatedDetails.addDeferment(personToEdit.getDetails().getDeferment());

        return new Client(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedType,
                updatedDetails, updatedArea);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
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
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditHousekeepingDetailsDescriptor {
        private LocalDate lastHousekeepingDate;
        private Period preferredInterval;
        public EditHousekeepingDetailsDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditHousekeepingDetailsDescriptor(EditHousekeepingDetailsDescriptor toCopy) {
            setLastHousekeepingDate(toCopy.lastHousekeepingDate);
            setPreferredInterval(toCopy.preferredInterval);

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(lastHousekeepingDate, preferredInterval);
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
                    && Objects.equals(preferredInterval, otherEditPersonDescriptor.preferredInterval);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("last housekeeping date", lastHousekeepingDate)
                    .add("preferred interval", preferredInterval)
                    .toString();
        }
    }
}
