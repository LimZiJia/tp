package housekeeping.hub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import housekeeping.hub.commons.core.index.Index;
import housekeeping.hub.logic.Messages;
import housekeeping.hub.logic.commands.exceptions.CommandException;
import housekeeping.hub.model.Model;
import housekeeping.hub.model.person.Address;
import housekeeping.hub.model.person.Area;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Email;
import housekeeping.hub.model.person.HousekeepingDetails;
import housekeeping.hub.model.person.Name;
import housekeeping.hub.model.person.Person;
import housekeeping.hub.model.person.Phone;
import housekeeping.hub.model.tag.Tag;

/**
 * Edits the details of an existing client in the address book.
 */
public class EditClientCommand extends EditCommand {
    public static final String MESSAGE_EDIT_CLIENT_SUCCESS = "Edited Client: %1$s";
    public static final String MESSAGE_DUPLICATE_CLIENT = "This client already exists in the hub book.";

    public EditClientCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        super(index, editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client personToEdit = lastShownList.get(index.getZeroBased());
        Client editedClient = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedClient) && model.hasClient(editedClient)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }

        model.setClient(personToEdit, editedClient);

        return new CommandResult(String.format(MESSAGE_EDIT_CLIENT_SUCCESS, Messages.formatClient(editedClient)));
    }

    /**
     * Creates and returns a {@code Client} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    @Override
    protected Client createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Area updatedArea = editPersonDescriptor.getArea().orElse(personToEdit.getArea());
        HousekeepingDetails updatedDetails =
                editPersonDescriptor.getDetails().orElse(personToEdit.getDetails());

        return new Client(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedDetails,
                updatedArea);
    }
}
