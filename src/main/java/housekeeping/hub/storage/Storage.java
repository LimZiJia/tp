package housekeeping.hub.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import housekeeping.hub.commons.exceptions.DataLoadingException;
import housekeeping.hub.model.ReadOnlyAddressBook;
import housekeeping.hub.model.ReadOnlyUserPrefs;
import housekeeping.hub.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
