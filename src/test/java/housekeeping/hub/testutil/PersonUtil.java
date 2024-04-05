package housekeeping.hub.testutil;

import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_AREA;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_DETAILS;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_EMAIL;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_NAME;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_PHONE;
import static housekeeping.hub.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import housekeeping.hub.logic.commands.AddClientCommand;
import housekeeping.hub.logic.commands.AddHousekeeperCommand;
import housekeeping.hub.logic.commands.EditCommand.EditPersonDescriptor;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.model.person.HousekeepingDetails;
import housekeeping.hub.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {
    public static String getAddClientCommand(Client client) {
        return AddClientCommand.COMMAND_WORD + " client " + getClientDetails(client);
    }

    public static String getAddHousekeeperCommand(Housekeeper housekeeper) {
        return AddHousekeeperCommand.COMMAND_WORD + " housekeeper " + getHousekeeperDetails(housekeeper);
    }

    /**
     * Returns the part of command string for the given {@code client}'s details.
     */
    public static String getClientDetails(Client client) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + client.getName().fullName + " ");
        sb.append(PREFIX_PHONE + client.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + client.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + client.getAddress().value + " ");
        sb.append(PREFIX_AREA + client.getArea().value + " ");
        client.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        //sb.append(PREFIX_DETAILS + client.getDetails().toString());
        sb.append(PREFIX_DETAILS + "2021-10-10 1 days");

        /*String[] hD = HousekeepingDetails.makeStoredDetailsReadable(client.getDetails().toString()).split("\n");
        String lHD = hD[0].split(" ")[2];
        String pI = hD[1].split(" ")[2] + " " + hD[1].split(" ")[3];
        sb.append(PREFIX_DETAILS + lHD + " " + pI);*/

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code housekeeper}'s details.
     */
    public static String getHousekeeperDetails(Housekeeper housekeeper) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + housekeeper.getName().fullName + " ");
        sb.append(PREFIX_PHONE + housekeeper.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + housekeeper.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + housekeeper.getAddress().value + " ");
        sb.append(PREFIX_AREA + housekeeper.getArea().value + " ");
        housekeeper.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        sb.append(PREFIX_DETAILS + housekeeper.getDetails().toString());

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getArea().ifPresent(area -> sb.append(PREFIX_AREA).append(area.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }

        //descriptor.getDetails().ifPresent(details -> sb.append(PREFIX_DETAILS).append(details.toString()));
        return sb.toString();
    }

}
