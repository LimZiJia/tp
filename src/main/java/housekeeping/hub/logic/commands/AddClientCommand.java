package housekeeping.hub.logic.commands;

import static java.util.Objects.requireNonNull;

import housekeeping.hub.logic.Messages;
import housekeeping.hub.logic.commands.exceptions.CommandException;
import housekeeping.hub.model.Model;
import housekeeping.hub.model.person.Client;

/**
 * Adds a client to the address book.
 */
public class AddClientCommand extends AddCommand {

    public static final String MESSAGE_SUCCESS = "New client added: %1$s";
    public static final String MESSAGE_DUPLICATE_CLIENT = "This client already exists in the hub book";

    public AddClientCommand(Client client) {
        super(client);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasClient((Client) toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }

        model.addClient((Client) toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatClient((Client) toAdd)));
    }
}
