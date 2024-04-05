package housekeeping.hub.model;

import java.nio.file.Path;

import housekeeping.hub.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();
}
