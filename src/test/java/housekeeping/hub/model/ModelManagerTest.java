package housekeeping.hub.model;

import static housekeeping.hub.model.Model.PREDICATE_SHOW_ALL_CLIENTS;
import static housekeeping.hub.testutil.Assert.assertThrows;
import static housekeeping.hub.testutil.TypicalPersons.ALICE;
import static housekeeping.hub.testutil.TypicalPersons.BENSON;
import static housekeeping.hub.testutil.TypicalPersons.BOB;
import static housekeeping.hub.testutil.TypicalPersons.ELLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import housekeeping.hub.commons.core.GuiSettings;
import housekeeping.hub.model.person.ContainsKeywordsPredicate;
import housekeeping.hub.model.person.exceptions.DuplicatePersonException;
import housekeeping.hub.model.person.exceptions.PersonNotFoundException;
import housekeeping.hub.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("hub/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/hub/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("hub/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasClient(null));
    }

    @Test
    public void hasHousekeeper_nullHousekeeper_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasHousekeeper(null));
    }

    @Test
    public void hasClient_clientNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasClient(ALICE));
    }

    @Test
    public void hasHousekeeper_housekeeperNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasHousekeeper(BOB));
    }

    @Test
    public void hasClient_clientInAddressBook_returnsTrue() {
        modelManager.addClient(ALICE);
        assertTrue(modelManager.hasClient(ALICE));
    }

    @Test
    public void hasHousekeeper_housekeeperInAddressBook_returnsTrue() {
        modelManager.addHousekeeper(BOB);
        assertTrue(modelManager.hasHousekeeper(BOB));
    }

    @Test
    public void getFilteredClientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredClientList().remove(0));
    }

    @Test
    public void getFilteredHousekeeperList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredHousekeeperList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withClient(ALICE).withClient(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        final List<String> splitAr = new ArrayList<>();
        splitAr.add("");
        final List<String> splitAd = new ArrayList<>();
        splitAd.add("");
        modelManager.updateFilteredClientList(new ContainsKeywordsPredicate(Arrays.asList(keywords), splitAd, splitAr));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    @Test
    public void addClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addClient(null));
    }

    @Test
    public void addClient_duplicateClient_throwsDuplicatePersonException() {
        modelManager.addClient(ALICE);
        assertThrows(DuplicatePersonException.class, () -> modelManager.addClient(ALICE));
    }

    @Test
    public void addClient_uniqueClient_clientAdded() {
        modelManager.addClient(ALICE);
        assertTrue(modelManager.getFilteredClientList().contains(ALICE));
    }

    @Test
    public void addHousekeeper_nullHousekeeper_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addHousekeeper(null));
    }

    @Test
    public void addHousekeeper_duplicateHousekeeper_throwsDuplicatePersonException() {
        modelManager.addHousekeeper(BOB);
        assertThrows(DuplicatePersonException.class, () -> modelManager.addHousekeeper(BOB));
    }

    @Test
    public void addHousekeeper_uniqueHousekeeper_housekeeperAdded() {
        modelManager.addHousekeeper(BOB);
        assertTrue(modelManager.getFilteredHousekeeperList().contains(BOB));
    }

    @Test
    public void deleteClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteClient(null));
    }

    @Test
    public void deleteClient_clientNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.deleteClient(ALICE));
    }

    @Test
    public void deleteClient_existingClient_clientDeleted() {
        modelManager.addClient(ALICE);
        modelManager.deleteClient(ALICE);
        assertFalse(modelManager.getFilteredClientList().contains(ALICE));
    }

    @Test
    public void deleteHousekeeper_nullHousekeeper_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteHousekeeper(null));
    }

    @Test
    public void deleteHousekeeper_housekeeperNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.deleteHousekeeper(BOB));
    }

    @Test
    public void deleteHousekeeper_existingHousekeeper_housekeeperDeleted() {
        modelManager.addHousekeeper(BOB);
        modelManager.deleteHousekeeper(BOB);
        assertFalse(modelManager.getFilteredHousekeeperList().contains(BOB));
    }

    @Test
    public void setClient_nullTargetClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setClient(null, ALICE));
    }

    @Test
    public void setClient_nullEditedClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setClient(ALICE, null));
    }

    @Test
    public void setClient_targetClientNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.setClient(ALICE, ALICE));
    }

    @Test
    public void setClient_editedClientIsDuplicate_throwsDuplicatePersonException() {
        modelManager.addClient(ALICE);
        modelManager.addClient(BENSON);
        assertThrows(DuplicatePersonException.class, () -> modelManager.setClient(ALICE, BENSON));
    }

    @Test
    public void setClient_targetInAddressBookAndEditedClientHasDifferentIdentityFields_clientEdited() {
        modelManager.addClient(ALICE);
        modelManager.setClient(ALICE, BENSON);
        assertFalse(modelManager.getFilteredClientList().contains(ALICE));
        assertTrue(modelManager.getFilteredClientList().contains(BENSON));
    }

    @Test
    public void setHousekeeper_nullTargetHousekeeper_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setHousekeeper(null, BOB));
    }

    @Test
    public void setHousekeeper_nullEditedHousekeeper_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setHousekeeper(BOB, null));
    }

    @Test
    public void setHousekeeper_targetHousekeeperNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.setHousekeeper(BOB, BOB));
    }

    @Test
    public void setHousekeeper_editedHousekeeperIsDuplicate_throwsDuplicatePersonException() {
        modelManager.addHousekeeper(BOB);
        modelManager.addHousekeeper(ELLE);
        assertThrows(DuplicatePersonException.class, () -> modelManager.setHousekeeper(BOB, ELLE));
    }

    @Test
    public void setHousekeeper_targetInAddressBookAndEditedHousekeeperHasDifferentIdentityFields_housekeeperEdited() {
        modelManager.addHousekeeper(BOB);
        modelManager.setHousekeeper(BOB, ELLE);
        assertFalse(modelManager.getFilteredHousekeeperList().contains(BOB));
        assertTrue(modelManager.getFilteredHousekeeperList().contains(ELLE));
    }
}
