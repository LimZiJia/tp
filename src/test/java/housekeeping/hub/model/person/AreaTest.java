package housekeeping.hub.model.person;

import static housekeeping.hub.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AreaTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Area(null));
    }

    @Test
    public void constructor_invalidArea_throwsIllegalArgumentException() {
        String invalidArea = "central";
        assertThrows(IllegalArgumentException.class, () -> new Area(invalidArea));
    }

    @Test
    public void preambleIsAllowed() {
        assertFalse(Area.preambleIsAllowed("central"));
        assertFalse(Area.preambleIsAllowed("Northwest"));
        assertFalse(Area.preambleIsAllowed("NORTH"));
        assertFalse(Area.preambleIsAllowed("NORTHWEST"));

        assertTrue(Area.preambleIsAllowed("north"));
        assertTrue(Area.preambleIsAllowed("northeast"));
        assertTrue(Area.preambleIsAllowed("northwest"));
        assertTrue(Area.preambleIsAllowed("southeast"));
        assertTrue(Area.preambleIsAllowed("south"));
        assertTrue(Area.preambleIsAllowed("southwest"));
        assertTrue(Area.preambleIsAllowed("west"));
        assertTrue(Area.preambleIsAllowed("east"));
    }

    @Test
    public void isValidArea() {
        // null area
        assertThrows(NullPointerException.class, () -> Area.isValidArea(null));

        // invalid areas
        assertFalse(Area.isValidArea("")); // empty string
        assertFalse(Area.isValidArea("North")); // Capital letter
        assertFalse(Area.isValidArea("central")); // not in the list of allowed areas

        // valid areas
        assertTrue(Area.isValidArea("north"));
        assertTrue(Area.isValidArea("northeast"));
        assertTrue(Area.isValidArea("east"));
        assertTrue(Area.isValidArea("southeast"));
        assertTrue(Area.isValidArea("south"));
        assertTrue(Area.isValidArea("southwest"));
        assertTrue(Area.isValidArea("west"));
        assertTrue(Area.isValidArea("northwest"));
    }

    @Test
    public void equals() {
        Area area = new Area("north");

        // same values -> returns true
        assertTrue(area.equals(new Area("north")));

        // same object -> returns true
        assertTrue(area.equals(area));

        // null -> returns false
        assertFalse(area.equals(null));

        // different types -> returns false
        assertFalse(area.equals(5.0f));

        // different values -> returns false
        assertFalse(area.equals(new Area("south")));
    }
}
