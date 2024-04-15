package housekeeping.hub.model.person;

import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static housekeeping.hub.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static housekeeping.hub.testutil.Assert.assertThrows;
import static housekeeping.hub.testutil.TypicalPersons.ALICE;
import static housekeeping.hub.testutil.TypicalPersons.BENSON;
import static housekeeping.hub.testutil.TypicalPersons.BOB;
import static housekeeping.hub.testutil.TypicalPersons.ELLE;
import static housekeeping.hub.testutil.TypicalPersons.HOON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import housekeeping.hub.model.person.exceptions.DuplicatePersonException;
import housekeeping.hub.model.person.exceptions.PersonNotFoundException;
import housekeeping.hub.testutil.ClientBuilder;

public class UniquePersonListTest {

    private final UniquePersonList<Client> uniqueClientList = new UniquePersonList<>();
    private final UniquePersonList<Housekeeper> uniqueHousekeeperList = new UniquePersonList<>();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueClientList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueClientList.add(ALICE);
        assertTrue(uniqueClientList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueClientList.add(ALICE);
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueClientList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueClientList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueClientList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueClientList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueClientList.add(ALICE);
        uniqueClientList.setPerson(ALICE, ALICE);
        UniquePersonList<Client> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniqueClientList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueClientList.add(ALICE);
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueClientList.setPerson(ALICE, editedAlice);
        UniquePersonList<Client> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniqueClientList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueClientList.add(ALICE);
        uniqueClientList.setPerson(ALICE, HOON);
        UniquePersonList<Client> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(HOON);
        assertEquals(expectedUniquePersonList, uniqueClientList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueClientList.add(ALICE);
        uniqueClientList.add(HOON);
        assertThrows(DuplicatePersonException.class, () -> uniqueClientList.setPerson(ALICE, HOON));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueClientList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueClientList.add(ALICE);
        uniqueClientList.remove(ALICE);
        UniquePersonList<Client> expectedUniquePersonList = new UniquePersonList<>();
        assertEquals(expectedUniquePersonList, uniqueClientList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setPersons((UniquePersonList<Client>) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueClientList.add(ALICE);
        UniquePersonList<Client> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(HOON);
        uniqueClientList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniqueClientList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setPersons((List<Client>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueClientList.add(ALICE);
        List<Client> personList = Collections.singletonList(HOON);
        uniqueClientList.setPersons(personList);
        UniquePersonList<Client> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(HOON);
        assertEquals(expectedUniquePersonList, uniqueClientList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Client> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueClientList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueClientList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueClientList.asUnmodifiableObservableList().toString(), uniqueClientList.toString());
    }

    @Test
    public void personsAreUnique_listWithDuplicatePersons_returnsFalse() {
        List<Client> listWithDuplicateClients = Arrays.asList(ALICE, ALICE);
        assertFalse(uniqueClientList.personsAreUnique(listWithDuplicateClients));

        List<Housekeeper> listWithDuplicateHousekeepers = Arrays.asList(BOB, BOB);
        assertFalse(uniqueHousekeeperList.personsAreUnique(listWithDuplicateHousekeepers));
    }

    @Test
    public void personsAreUnique_listWithNoDuplicatePersons_returnsTrue() {
        List<Client> listWithNoDuplicateClients = Arrays.asList(ALICE, HOON);
        assertTrue(uniqueClientList.personsAreUnique(listWithNoDuplicateClients));

        List<Housekeeper> listWithNoDuplicateHousekeepers = Arrays.asList(BOB, ELLE);
        assertTrue(uniqueHousekeeperList.personsAreUnique(listWithNoDuplicateHousekeepers));
    }

    @Test
    public void equals_listsWithSamePersons_returnsTrue() {
        UniquePersonList<Client> uniqueClientListOne = new UniquePersonList<>();
        uniqueClientListOne.add(ALICE);
        UniquePersonList<Client> uniqueClientListTwo = new UniquePersonList<>();
        uniqueClientListTwo.add(ALICE);
        assertEquals(uniqueClientListOne, uniqueClientListTwo);

        UniquePersonList<Housekeeper> uniqueHousekeeperListOne = new UniquePersonList<>();
        uniqueHousekeeperListOne.add(BOB);
        UniquePersonList<Housekeeper> uniqueHousekeeperListTwo = new UniquePersonList<>();
        uniqueHousekeeperListTwo.add(BOB);
        assertEquals(uniqueHousekeeperListOne, uniqueHousekeeperListTwo);
    }

    @Test
    public void equals_sameLists_returnsTrue() {
        UniquePersonList<Client> uniqueClientList = new UniquePersonList<>();
        assertTrue(uniqueClientList.equals(uniqueClientList));

        UniquePersonList<Housekeeper> uniqueHousekeeperList = new UniquePersonList<>();
        assertTrue(uniqueHousekeeperList.equals(uniqueHousekeeperList));
    }

    @Test
    public void equals_differentListsOrNull_returnsFalse() {
        assertFalse(uniqueClientList.equals(null));
        assertFalse(uniqueHousekeeperList.equals(null));

        UniquePersonList<Client> uniqueClientListOne = new UniquePersonList<>();
        UniquePersonList<Client> uniqueClientListTwo = new UniquePersonList<>();
        uniqueClientListOne.add(BENSON);
        uniqueClientListTwo.add(ALICE);
        assertFalse(uniqueClientListOne.equals(uniqueClientListTwo));

        UniquePersonList<Housekeeper> uniqueHousekeeperListOne = new UniquePersonList<>();
        UniquePersonList<Housekeeper> uniqueHousekeeperListTwo = new UniquePersonList<>();
        uniqueHousekeeperListOne.add(ELLE);
        uniqueHousekeeperListTwo.add(BOB);
        assertFalse(uniqueHousekeeperList.equals(uniqueHousekeeperListTwo));
    }
}
