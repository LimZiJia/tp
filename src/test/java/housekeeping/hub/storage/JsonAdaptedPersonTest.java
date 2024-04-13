package housekeeping.hub.storage;

import static housekeeping.hub.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static housekeeping.hub.testutil.Assert.assertThrows;
import static housekeeping.hub.testutil.TypicalPersons.BENSON;
import static housekeeping.hub.testutil.TypicalPersons.BOB;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import housekeeping.hub.commons.exceptions.IllegalValueException;
import housekeeping.hub.model.person.Address;
import housekeeping.hub.model.person.Email;
import housekeeping.hub.model.person.HousekeepingDetails;
import housekeeping.hub.model.person.Name;
import housekeeping.hub.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_AREA = BENSON.getArea().toString();
    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedClient client = new JsonAdaptedClient(BENSON);
        assertEquals(BENSON, client.toModelType());

        JsonAdaptedHousekeeper housekeeper = new JsonAdaptedHousekeeper(BOB);
        assertEquals(BOB, housekeeper.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;

        JsonAdaptedClient client =
                new JsonAdaptedClient(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null,
                        VALID_AREA);
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);

        JsonAdaptedHousekeeper housekeeper =
                new JsonAdaptedHousekeeper(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_AREA, null);
        assertThrows(IllegalValueException.class, expectedMessage, housekeeper::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());

        JsonAdaptedClient person =
                new JsonAdaptedClient(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null,
                        VALID_AREA);
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);

        JsonAdaptedHousekeeper housekeeper =
                new JsonAdaptedHousekeeper(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_AREA, null);
        assertThrows(IllegalValueException.class, expectedMessage, housekeeper::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;

        JsonAdaptedClient person =
                new JsonAdaptedClient(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null,
                        VALID_AREA);
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);

        JsonAdaptedHousekeeper housekeeper =
                new JsonAdaptedHousekeeper(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_AREA, null);
        assertThrows(IllegalValueException.class, expectedMessage, housekeeper::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());

        JsonAdaptedClient person =
                new JsonAdaptedClient(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null,
                        VALID_AREA);
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);

        JsonAdaptedHousekeeper housekeeper =
                new JsonAdaptedHousekeeper(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_AREA, null);
        assertThrows(IllegalValueException.class, expectedMessage, housekeeper::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;

        JsonAdaptedClient person =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null,
                        VALID_AREA);
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);

        JsonAdaptedHousekeeper housekeeper =
                new JsonAdaptedHousekeeper(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_AREA, null);
        assertThrows(IllegalValueException.class, expectedMessage, housekeeper::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());

        JsonAdaptedClient person =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS, null,
                        VALID_AREA);
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);

        JsonAdaptedHousekeeper housekeeper =
                new JsonAdaptedHousekeeper(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS,
                        VALID_AREA, null);
        assertThrows(IllegalValueException.class, expectedMessage, housekeeper::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;

        JsonAdaptedClient person =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS, null,
                        VALID_AREA);
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);

        JsonAdaptedHousekeeper housekeeper =
                new JsonAdaptedHousekeeper(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS,
                        VALID_AREA, null);
        assertThrows(IllegalValueException.class, expectedMessage, housekeeper::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());

        JsonAdaptedClient person =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS, null,
                        VALID_AREA);
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);

        JsonAdaptedHousekeeper housekeeper =
                new JsonAdaptedHousekeeper(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS,
                        VALID_AREA, null);
        assertThrows(IllegalValueException.class, expectedMessage, housekeeper::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedClient person =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags, null,
                        VALID_AREA);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullDetails_throwsIllegalValueException() {
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, HousekeepingDetails.class.getSimpleName());

        JsonAdaptedClient person =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null,
                        VALID_AREA);
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
