package housekeeping.hub.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.HousekeepingDetails;

public class ClientCard extends PersonCard {
    private static final String FXML = "ClientListCard.fxml";
    @FXML
    private Label details;

    public ClientCard(Client client, int displayedIndex) {
        super(client, displayedIndex, FXML);
        HousekeepingDetails housekeepingDetails = client.getDetails();
        if (housekeepingDetails == null) {
            details.setText(HousekeepingDetails.NO_DETAILS_PROVIDED);
        } else {
            details.setText(HousekeepingDetails.makeStoredDetailsReadable(housekeepingDetails.toString()));
        }
    }

}
