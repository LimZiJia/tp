package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Client;
import seedu.address.model.person.Housekeeper;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList<Client> clients;
    private final UniquePersonList<Housekeeper> housekeepers;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        clients = new UniquePersonList<Client>();
        housekeepers = new UniquePersonList<Housekeeper>();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the client list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setPersons(clients);
    }

    /**
     * Replaces the contents of the housekeeper list with {@code housekeepers}.
     * {@code housekeepers} must not contain duplicate housekeepers.
     */
    public void setHousekeepers(List<Housekeeper> housekeepers) {
        this.housekeepers.setPersons(housekeepers);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setClients(newData.getClientList());
        setHousekeepers(newData.getHousekeeperList());
    }

    //// person-level operations

    /**
     * Returns true if a client with the same identity as {@code client} exists in the address book.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.contains(client);
    }

    /**
     * Returns true if a housekeeper with the same identity as {@code housekeeper} exists in the address book.
     */
    public boolean hasHousekeeper(Housekeeper housekeeper) {
        requireNonNull(housekeeper);
        return housekeepers.contains(housekeeper);
    }

    /**
     * Adds a client to the address book.
     * The client must not already exist in the address book.
     */
    public void addClient(Client client) {
        clients.add(client);
    }

    /**
     * Adds a housekeeper to the address book.
     * The housekeeper must not already exist in the address book.
     */
    public void addHousekeeper(Housekeeper housekeeper) {
        housekeepers.add(housekeeper);
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the address book.
     */
    public void setClient(Client target, Client editedClient) {
        requireNonNull(editedClient);

        clients.setPerson(target, editedClient);
    }

    /**
     * Replaces the given housekeeper {@code target} in the list with {@code editedHousekeeper}.
     * {@code target} must exist in the address book.
     * The housekeeper identity of {@code editedHousekeeper} must not be the same as another existing housekeeper in the address book.
     */
    public void setHousekeeper(Housekeeper target, Housekeeper editedHousekeeper) {
        requireNonNull(editedHousekeeper);

        housekeepers.setPerson(target, editedHousekeeper);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        if (key.isClient()) {
            clients.remove((Client) key);
        } else {
            housekeepers.remove((Housekeeper) key);
        }
    }

    /**
     * Removes {@code client} from this {@code AddressBook}.
     * {@code client} must exist in the address book.
     */
    public void removeClient(Client client) {
        clients.remove(client);
    }

    /**
     * Removes {@code housekeeper} from this {@code AddressBook}.
     * {@code housekeeper} must exist in the address book.
     */
    public void removeHousekeeper(Housekeeper housekeeper) {
        housekeepers.remove(housekeeper);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clients", clients)
                .add("housekeepers", housekeepers)
                //.add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Housekeeper> getHousekeeperList() {
        return housekeepers.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return clients.equals(otherAddressBook.clients)
                && housekeepers.equals(otherAddressBook.housekeepers);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + clients.hashCode();
        result = 31 * result + housekeepers.hashCode();
        return result;
    }
}
