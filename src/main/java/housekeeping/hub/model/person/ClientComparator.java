package housekeeping.hub.model.person;

import java.util.Comparator;

/**
 * Compares two clients based predicted next housekeeping date.
 */
public class ClientComparator implements Comparator<Client> {
    @Override
    public int compare(Client c1, Client c2) {
        return c1.compareTo(c2);
    }
}
