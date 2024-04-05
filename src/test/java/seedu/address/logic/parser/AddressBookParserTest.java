package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.ALLOWED_PREAMBLES_TYPE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddClientCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteClientCommand;
import seedu.address.logic.commands.DeleteHousekeeperCommand;
import seedu.address.logic.commands.EditClientCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditHousekeeperCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Client;
import seedu.address.model.person.Housekeeper;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.HousekeeperBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Client client = new ClientBuilder().build();
        AddClientCommand command = (AddClientCommand) parser.parseCommand(PersonUtil.getAddClientCommand(client));
        assertEquals(new AddClientCommand(client), command);
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
        List<String> address = Arrays.asList("Clementi", "Jurong");
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
