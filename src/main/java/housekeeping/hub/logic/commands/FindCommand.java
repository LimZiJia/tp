package housekeeping.hub.logic.commands;

import static java.util.Objects.requireNonNull;

import housekeeping.hub.commons.util.ToStringBuilder;
import housekeeping.hub.model.person.ContainsKeywordsPredicate;

/**
 * Finds and lists all persons in hub book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
abstract public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all clients or housekeepers filtered by "
            + "the specified attribute(s). Valid attributes: {name, phone, hub, area}\n"
            + "Parameters: TYPE(client or housekeeper) PREFIX/KEYWORD [PREFIX/KEYWORDS...] (optional)\n"
            + "Example: " + COMMAND_WORD + " client n/alice p/90274629";

    public static final String MESSAGE_NOT_FOUND = "At least one field to find must be provided.";

    protected final ContainsKeywordsPredicate predicate;

    public FindCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

