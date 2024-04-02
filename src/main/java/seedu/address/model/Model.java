package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Client;
import seedu.address.model.person.Housekeeper;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_CLIENTS = unused -> true;
    Predicate<Person> PREDICATE_SHOW_ALL_HOUSEKEEPERS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a client with the same identity as {@code client} exists in the address book.
     */
    boolean hasClient(Client client);

    /**
     * Returns true if a housekeeper with the same identity as {@code housekeeper} exists in the address book.
     */
    boolean hasHousekeeper(Housekeeper housekeeper);

    /**
     * Deletes the given client.
     * The client must exist in the address book.
     */
    void deleteClient(Client target);

    /**
     * Deletes the given housekeeper.
     * The housekeeper must exist in the address book.
     */
    void deleteHousekeeper(Housekeeper target);

    /**
     * Adds the given client.
     * {@code client} must not already exist in the address book.
     */
    void addClient(Client client);

    /**
     * Adds the given housekeeper.
     * {@code housekeeper} must not already exist in the address book.
     */
    void addHousekeeper(Housekeeper housekeeper);

    /**
     * Replaces the given client {@code target} with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the address book.
     */
    void setClient(Client target, Client editedClient);

    /**
     * Replaces the given housekeeper {@code target} with {@code editedHousekeeper}.
     * {@code target} must exist in the address book.
     * The housekeeper identity of {@code editedHousekeeper} must not be the same as another existing housekeeper in the address book.
     */
    void setHousekeeper(Housekeeper target, Housekeeper editedHousekeeper);

    /**
     * Returns an unmodifiable view of the filtered client list
     */
    ObservableList<Client> getFilteredClientList();

    /**
     * Returns an unmodifiable view of the filtered housekeeper list
     */
    ObservableList<Housekeeper> getFilteredHousekeeperList();

    /**
     * Updates the filter of the filtered client list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClientList(Predicate<? extends Person> predicate);

    /**
     * Updates and sorts the filter of the filtered client list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateAndSortFilteredClientList(Predicate<Client> predicate);

    /**
     * Updates the filter of the filtered housekeeper list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredHousekeeperList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered housekeeper list to filter by the given {@code housekeeperPredicate}.
     * @throws NullPointerException if {@code housekeeperPredicate} is null.
     */
    void updateFilteredHousekeeperListWithHousekeeperPredicate(Predicate<Housekeeper> housekeeperPredicate);
}
