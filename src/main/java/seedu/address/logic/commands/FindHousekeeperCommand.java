package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ContainsKeywordsPredicate;

public class FindHousekeeperCommand extends FindCommand {
    public FindHousekeeperCommand(ContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredHousekeeperList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_HOUSEKEEPERS_LISTED_OVERVIEW, model.getFilteredHousekeeperList().size()));
    }
}
