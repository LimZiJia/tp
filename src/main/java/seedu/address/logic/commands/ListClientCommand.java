package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.TypePredicate;

public class ListClientCommand extends ListCommand {
    public ListClientCommand(TypePredicate predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClientList(predicate);
        return new CommandResult(MESSAGE_SUCCESS + "all " + predicate.getType() + "s");
    }
}
