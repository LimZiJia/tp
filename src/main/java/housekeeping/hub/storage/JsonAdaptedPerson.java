package housekeeping.hub.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import housekeeping.hub.commons.exceptions.IllegalValueException;
import housekeeping.hub.model.person.Person;

/**
 * Jackson-friendly version of {@link Person}.
 */
abstract class JsonAdaptedPerson {

    protected static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    protected final String name;
    protected final String phone;
    protected final String email;
    protected final String address;
    protected final List<JsonAdaptedTag> tags = new ArrayList<>();
    protected final String area;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("hub") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("area") String area) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.area = area;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        area = source.getArea().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    abstract public Person toModelType() throws IllegalValueException;
}
