package housekeeping.hub.testutil;

import housekeeping.hub.model.AddressBook;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Housekeeper;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Client} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withClient(Client client) {
        addressBook.addClient(client);
        return this;
    }

    /**
     * Adds a new {@code Housekeeper} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withHousekeeper(Housekeeper housekeeper) {
        addressBook.addHousekeeper(housekeeper);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
