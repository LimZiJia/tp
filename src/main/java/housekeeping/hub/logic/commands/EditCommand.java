package housekeeping.hub.logic.commands;

import static java.util.Objects.requireNonNull;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_EMAIL;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_NAME;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_PHONE;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import housekeeping.hub.commons.core.index.Index;
import housekeeping.hub.commons.util.CollectionUtil;
import housekeeping.hub.commons.util.ToStringBuilder;
import housekeeping.hub.model.person.Address;
import housekeeping.hub.model.person.BookingList;
import housekeeping.hub.model.person.Area;
import housekeeping.hub.model.person.Email;
import housekeeping.hub.model.person.HousekeepingDetails;
import housekeeping.hub.model.person.Name;
import housekeeping.hub.model.person.Person;
import housekeeping.hub.model.person.Phone;
import housekeeping.hub.model.tag.Tag;

/**
 * Edits the details of an existing person in the hub book.
 */
abstract public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the client or housekeeper "
            + "identified by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "TYPE(client or housekeeper) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " client 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    protected final Index index;
    protected final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    abstract protected Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor);

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
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private BookingList bookingList;
        private HousekeepingDetails details;
        private Area area;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setBookingList(toCopy.bookingList);
            setDetails(toCopy.details);
            setArea(toCopy.area);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, area, bookingList);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setBookingList(BookingList bookingList) {
            this.bookingList = bookingList;
        }

        public Optional<BookingList> getBookingList() {
            return Optional.ofNullable(bookingList);
        }

        public void setDetails(HousekeepingDetails details) {
            this.details = details;
        }

        public Optional<HousekeepingDetails> getDetails() {
            return Optional.ofNullable(details);
        }

        public void setArea(Area area) {
            this.area = area;
        }

        public Optional<Area> getArea() {
            return Optional.ofNullable(area);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(area, otherEditPersonDescriptor.area)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(bookingList, otherEditPersonDescriptor.bookingList);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("hub", address)
                    .add("tags", tags)
                    .add("area", area)
                    .add("booking list", bookingList)
                    .toString();
        }
    }
}
