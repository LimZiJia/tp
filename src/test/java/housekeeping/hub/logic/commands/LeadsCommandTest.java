package housekeeping.hub.logic.commands;

import static housekeeping.hub.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;

import housekeeping.hub.model.Model;
import housekeeping.hub.model.ModelManager;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.HousekeepingDetails;
import housekeeping.hub.testutil.ClientBuilder;

public class LeadsCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        LeadsCommand leadsCommand = new LeadsCommand();
        assertThrows(NullPointerException.class, () -> leadsCommand.execute(null));
    }

    @Test
    public void execute_validModel_success() {
        Client clientOne = new ClientBuilder().withName("ClientOne").withPhone("85355255").withEmail("one@email.com")
                .withAddress("123, Jurong West Ave 6, #08-111").withArea("west")
                .withDetails(new HousekeepingDetails(LocalDate.parse("2021-11-10"),
                        Period.ofMonths(Integer.parseInt("1")))).build();
        Client clientTwo = new ClientBuilder().withName("ClientTwo").withPhone("85355255").withEmail("two@email.com")
                .withAddress("123, Jurong West Ave 6, #08-111").withArea("west")
                .withDetails(new HousekeepingDetails(LocalDate.parse("2021-10-10"),
                        Period.ofMonths(Integer.parseInt("1")))).build();
        model.addClient(clientOne);
        model.addClient(clientTwo);

        assertEquals(model.getFilteredClientList().get(0), clientOne);
        assertEquals(model.getFilteredClientList().get(1), clientTwo);

        LeadsCommand leadsCommand = new LeadsCommand();
        leadsCommand.execute(model);

        assertEquals(model.getFilteredClientList().get(0), clientTwo);
        assertEquals(model.getFilteredClientList().get(1), clientOne);
    }
}
