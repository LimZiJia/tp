package housekeeping.hub.logic.commands;

import static java.util.Objects.requireNonNull;

import housekeeping.hub.logic.Messages;
import housekeeping.hub.model.Model;
import housekeeping.hub.model.person.ContainsKeywordsPredicate;

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
