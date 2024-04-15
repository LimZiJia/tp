package housekeeping.hub.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import housekeeping.hub.commons.exceptions.IllegalValueException;
import housekeeping.hub.model.AddressBook;
import housekeeping.hub.model.ReadOnlyAddressBook;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Housekeeper;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    private final List<JsonAdaptedClient> clients = new ArrayList<>();
    private final List<JsonAdaptedHousekeeper> housekeepers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("clients") List<JsonAdaptedClient> clients,
                                       @JsonProperty("housekeepers") List<JsonAdaptedHousekeeper> housekeepers) {
        this.clients.addAll(clients);
        this.housekeepers.addAll(housekeepers);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        clients.addAll(source.getClientList().stream()
                .map(JsonAdaptedClient::new)
                .collect(Collectors.toList()));
        housekeepers.addAll(source.getHousekeeperList().stream()
                .map(JsonAdaptedHousekeeper::new).collect(Collectors.toList()));
    }

    /**
     * Converts this hub book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedClient jsonAdaptedClient: clients) {
            Client client = jsonAdaptedClient.toModelType();
            if (addressBook.hasClient(client)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addClient(client);
        }
        for (JsonAdaptedHousekeeper jsonAdaptedHousekeeper: housekeepers) {
            Housekeeper housekeeper = jsonAdaptedHousekeeper.toModelType();
            if (addressBook.hasHousekeeper(housekeeper)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addHousekeeper(housekeeper);
        }
        return addressBook;
    }

}
