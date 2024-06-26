package housekeeping.hub.logic.commands;

import housekeeping.hub.commons.core.index.Index;
import housekeeping.hub.commons.util.ToStringBuilder;

/**
 * Deletes a person identified using it's displayed index from the hub book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the client or housekeeper identified by the index number used in the displayed client or "
            + "housekeeper list.\n"
            + "Parameters: TYPE(client or housekeeper) INDEX(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " client 1";

    protected final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
