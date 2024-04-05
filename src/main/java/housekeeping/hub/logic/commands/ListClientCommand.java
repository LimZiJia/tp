package housekeeping.hub.logic.commands;

import static java.util.Objects.requireNonNull;

import housekeeping.hub.model.Model;
import housekeeping.hub.model.person.TypePredicate;

public class ListClientCommand extends ListCommand {
    public ListClientCommand() {
        super(new TypePredicate("client"));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClientList(predicate);
        return new CommandResult(MESSAGE_SUCCESS + "all " + predicate.getType() + "s");
    }
}
