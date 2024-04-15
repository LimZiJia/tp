package housekeeping.hub.logic.commands;

import static java.util.Objects.requireNonNull;

import housekeeping.hub.model.Model;
import housekeeping.hub.model.person.TypePredicate;

/**
 * Lists all clients in the housekeeping hub to the user.
 */
public class ListHousekeeperCommand extends ListCommand {
    public ListHousekeeperCommand() {
        super(new TypePredicate("housekeeper"));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredHousekeeperList(predicate);
        return new CommandResult(MESSAGE_SUCCESS + "all " + predicate.getType() + "s");
    }
}
