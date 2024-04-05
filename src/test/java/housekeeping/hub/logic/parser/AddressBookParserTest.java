package housekeeping.hub.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static housekeeping.hub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static housekeeping.hub.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static housekeeping.hub.logic.parser.CliSyntax.ALLOWED_PREAMBLES_TYPE;
import static housekeeping.hub.testutil.Assert.assertThrows;
import static housekeeping.hub.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import housekeeping.hub.logic.commands.AddClientCommand;
import housekeeping.hub.logic.commands.ClearCommand;
import housekeeping.hub.logic.commands.DeleteClientCommand;
import housekeeping.hub.logic.commands.DeleteHousekeeperCommand;
import housekeeping.hub.logic.commands.EditClientCommand;
import housekeeping.hub.logic.commands.EditCommand.EditPersonDescriptor;
import housekeeping.hub.logic.commands.EditHousekeeperCommand;
import housekeeping.hub.logic.commands.ExitCommand;
import housekeeping.hub.logic.commands.HelpCommand;
import housekeeping.hub.logic.commands.ListCommand;
import housekeeping.hub.logic.parser.exceptions.ParseException;
import housekeeping.hub.model.person.Client;
import housekeeping.hub.model.person.Housekeeper;
import housekeeping.hub.testutil.ClientBuilder;
import housekeeping.hub.testutil.EditPersonDescriptorBuilder;
import housekeeping.hub.testutil.HousekeeperBuilder;
import housekeeping.hub.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Client person = new ClientBuilder().build();
        AddClientCommand command = (AddClientCommand) parser.parseCommand(PersonUtil.getAddClientCommand(person));
        assertEquals(new AddClientCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        // Delete client
        DeleteClientCommand deleteClientCommand = (DeleteClientCommand) parser.parseCommand(
                DeleteClientCommand.COMMAND_WORD + " client " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteClientCommand(INDEX_FIRST_PERSON), deleteClientCommand);

        // Delete housekeeper
        DeleteHousekeeperCommand deleteHousekeeperCommand = (DeleteHousekeeperCommand) parser.parseCommand(
                DeleteHousekeeperCommand.COMMAND_WORD + " housekeeper " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteHousekeeperCommand(INDEX_FIRST_PERSON), deleteHousekeeperCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        // Edit client
        Client client = new ClientBuilder().build();
        EditPersonDescriptor clientDescriptor = new EditPersonDescriptorBuilder(client).build();
        EditClientCommand editClientCommand = (EditClientCommand) parser.parseCommand(EditClientCommand.COMMAND_WORD
                + " client " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PersonUtil.getEditPersonDescriptorDetails(clientDescriptor));
        assertEquals(new EditClientCommand(INDEX_FIRST_PERSON, clientDescriptor), editClientCommand);

        // Edit housekeeper
        Housekeeper housekeeper = new HousekeeperBuilder().build();
        EditPersonDescriptor housekeeperDescriptor = new EditPersonDescriptorBuilder(housekeeper).build();
        EditHousekeeperCommand editHousekeeperCommand = (EditHousekeeperCommand) parser.parseCommand(
                EditHousekeeperCommand.COMMAND_WORD + " housekeeper " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PersonUtil.getEditPersonDescriptorDetails(housekeeperDescriptor));
        assertEquals(new EditHousekeeperCommand(INDEX_FIRST_PERSON, housekeeperDescriptor), editHousekeeperCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    /*@Test
    public void parseCommand_find() throws Exception {
        List<String> name = Arrays.asList("foo", "bar");
        List<String> hub = Arrays.asList("Clementi", "Jurong");
        List<String> area = Arrays.asList("west", "east");
        FindClientCommand findClientCommand = (FindClientCommand) parser.parseCommand(
                FindClientCommand.COMMAND_WORD + " client "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindClientCommand(new ContainsKeywordsPredicate(Arrays.asList(keywords[0]),
                Arrays.asList(addressKeywords), Arrays.asList(areaKeywords))), findClientCommand);
    }*/

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(
                ListCommand.COMMAND_WORD + " " + ALLOWED_PREAMBLES_TYPE[0]) instanceof ListCommand);
        assertTrue(parser.parseCommand(
                ListCommand.COMMAND_WORD + " " + ALLOWED_PREAMBLES_TYPE[1]) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " client") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
