package seedu.address.ui;

import seedu.address.model.person.Housekeeper;

public class HousekeeperCard extends PersonCard {
    private static final String FXML = "HousekeeperListCard.fxml";
    public HousekeeperCard(Housekeeper housekeeper, int displayedIndex) {
        super(housekeeper, displayedIndex, FXML);
    }
}
