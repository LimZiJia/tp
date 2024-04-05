package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_HOUSEKEEPERS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Area;
import seedu.address.model.person.Email;
import seedu.address.model.person.Housekeeper;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Type;
import seedu.address.model.person.BookingList;
import seedu.address.model.tag.Tag;

public class EditHousekeeperCommand extends EditCommand {
    public static final String MESSAGE_EDIT_HOUSEKEEPER_SUCCESS = "Edited Housekeeper: %1$s";
    public static final String MESSAGE_DUPLICATE_HOUSEKEEPER = "This housekeeper already exists in the address book.";

    public EditHousekeeperCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        super(index, editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Housekeeper> lastShownList = model.getFilteredHousekeeperList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_HOUSEKEEPER_DISPLAYED_INDEX);
        }

        Housekeeper personToEdit = lastShownList.get(index.getZeroBased());
        Housekeeper editedHousekeeper = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedHousekeeper) && model.hasHousekeeper(editedHousekeeper)) {
            throw new CommandException(MESSAGE_DUPLICATE_HOUSEKEEPER);
        }

        model.setHousekeeper(personToEdit, editedHousekeeper);
        model.updateFilteredHousekeeperList(PREDICATE_SHOW_ALL_HOUSEKEEPERS);
        return new CommandResult(String.format(MESSAGE_EDIT_HOUSEKEEPER_SUCCESS,
                Messages.formatHousekeeper(editedHousekeeper)));
    }

    /**
     * Creates and returns a {@code Housekeeper} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    @Override
    protected Housekeeper createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;
        Housekeeper housekeeperToEdit = (Housekeeper) personToEdit; // to use getBookingList()

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Area updatedArea = editPersonDescriptor.getArea().orElse(personToEdit.getArea());
        BookingList updatedBookingList = editPersonDescriptor.getBookingList()
                .orElse(housekeeperToEdit.getBookingList());

        return new Housekeeper(
                updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedArea, updatedBookingList);
    }
}
