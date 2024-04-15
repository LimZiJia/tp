package housekeeping.hub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import housekeeping.hub.commons.core.index.Index;
import housekeeping.hub.logic.Messages;
import housekeeping.hub.logic.commands.exceptions.CommandException;
import housekeeping.hub.model.Model;
import housekeeping.hub.model.person.Client;

/**
 * Deletes a client identified using it's displayed index from the address book.
 */
public class DeleteClientCommand extends DeleteCommand {
    public static final String MESSAGE_DELETE_CLIENT_SUCCESS = "Deleted Client: %1$s";

    public DeleteClientCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteClient(clientToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CLIENT_SUCCESS, Messages.formatClient(clientToDelete)));
    }
}
