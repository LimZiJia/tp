package housekeeping.hub.model;

import javafx.collections.ObservableList;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Housekeeper;

/**
 * Unmodifiable view of an hub book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    //ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the clients list.
     * This list will not contain any duplicate clients.
     */
    ObservableList<Client> getClientList();

    /**
     * Returns an unmodifiable view of the housekeepers list.
     * This list will not contain any duplicate housekeepers.
     */
    ObservableList<Housekeeper> getHousekeeperList();

}
