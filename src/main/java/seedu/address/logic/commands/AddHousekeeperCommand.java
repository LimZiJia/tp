package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Housekeeper;

public class AddHousekeeperCommand extends AddCommand {
    public static final String MESSAGE_SUCCESS = "New housekeeper added: %1$s";
    public static final String MESSAGE_DUPLICATE_HOUSEKEEPER = "This housekeeper already exists in the address book";
    public static final String MESSAGE_NO_HOUSEKEEPING_DETAILS = "Housekeeper should not have housekeeping details, " +
            "that is specifically for client.";

    public AddHousekeeperCommand(Housekeeper housekeeper) {
        super(housekeeper);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasHousekeeper((Housekeeper) toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_HOUSEKEEPER);
        }

        model.addHousekeeper((Housekeeper) toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatHousekeeper((Housekeeper) toAdd)));
    }
}
