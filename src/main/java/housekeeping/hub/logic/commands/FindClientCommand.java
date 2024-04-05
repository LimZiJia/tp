package housekeeping.hub.logic.commands;

import static java.util.Objects.requireNonNull;

import housekeeping.hub.logic.Messages;
import housekeeping.hub.model.Model;
import housekeeping.hub.model.person.ContainsKeywordsPredicate;

/**
 * Finds and lists all clients in the housekeeping hub with matching criteria.
 */
public class FindClientCommand extends FindCommand {
    public FindClientCommand(ContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClientList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CLIENTS_LISTED_OVERVIEW, model.getFilteredClientList().size()));
    }
}
