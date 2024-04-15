package housekeeping.hub.logic;

import java.nio.file.Path;

import housekeeping.hub.commons.core.GuiSettings;
import housekeeping.hub.logic.commands.CommandResult;
import housekeeping.hub.logic.commands.exceptions.CommandException;
import housekeeping.hub.logic.parser.exceptions.ParseException;
import housekeeping.hub.model.ReadOnlyAddressBook;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Housekeeper;
import javafx.collections.ObservableList;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see housekeeping.hub.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of clients */
    ObservableList<Client> getFilteredClientList();

    /** Returns an unmodifiable view of the filtered list of housekeepers */
    ObservableList<Housekeeper> getFilteredHousekeeperList();

    /**
     * Returns the user prefs' hub book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
