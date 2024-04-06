package housekeeping.hub.ui;

import housekeeping.hub.model.person.Housekeeper;

/**
 * An UI component that displays information of a {@code Housekeeper}.
 */
public class HousekeeperCard extends PersonCard {
    private static final String FXML = "HousekeeperListCard.fxml";
    public HousekeeperCard(Housekeeper housekeeper, int displayedIndex) {
        super(housekeeper, displayedIndex, FXML);
    }
}
