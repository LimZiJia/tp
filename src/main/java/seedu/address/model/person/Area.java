package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.ALLOWED_PREAMBLES_AREA;

/**
 * Represents a Person's area in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidArea(String)}
 */
public class Area {

    public static final String MESSAGE_CONSTRAINTS = "Area can only take [north, northeast, east, southeast, south,"
            + " southwest, west, northwest] and it should not be blank";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code area}.
     *
     * @param area A valid area.
     */
    public Area(String area) {
        requireNonNull(area);
        checkArgument(isValidArea(area), MESSAGE_CONSTRAINTS);
        value = area;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidArea(String test) {
        return test.matches(VALIDATION_REGEX) && preambleIsAllowed(test);
    }

    /**
     * Checks if a given command uses a preamble that is allowed. (we define preamble as AREA)
     * @param preamble
     * @return true if the preamble is allowed and no if it is not.
     */
    public static boolean preambleIsAllowed(String preamble) {
        for (String s : ALLOWED_PREAMBLES_AREA) {
            if (s.equals(preamble)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Area)) {
            return false;
        }

        Area otherArea = (Area) other;
        return value.equals(otherArea.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
