package housekeeping.hub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import housekeeping.hub.commons.core.index.Index;
import housekeeping.hub.logic.Messages;
import housekeeping.hub.logic.commands.exceptions.CommandException;
import housekeeping.hub.model.Model;
import housekeeping.hub.model.person.Housekeeper;

/**
 * Deletes a housekeeper identified using it's displayed index from the address book.
 */
public class DeleteHousekeeperCommand extends DeleteCommand {
    public static final String MESSAGE_DELETE_HOUSEKEEPER_SUCCESS = "Deleted Housekeeper: %1$s";

    public DeleteHousekeeperCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Housekeeper> lastShownList = model.getFilteredHousekeeperList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_HOUSEKEEPER_DISPLAYED_INDEX);
        }

        Housekeeper housekeeperToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteHousekeeper(housekeeperToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_HOUSEKEEPER_SUCCESS,
                Messages.formatHousekeeper(housekeeperToDelete)));
    }
}
