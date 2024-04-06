package housekeeping.hub.logic.commands;

import housekeeping.hub.model.person.TypePredicate;

/**
 * Lists all persons in the hub book to the user.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Show list of the contacts with the given type.\n"
            + "Parameters: TYPE\n"
            + "Example: " + COMMAND_WORD + " housekeeper";
    protected final TypePredicate predicate;

    public ListCommand(TypePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherListCommand = (ListCommand) other;
        return predicate.equals(otherListCommand.predicate);
    }
}
