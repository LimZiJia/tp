package seedu.address.logic.parser;


/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_AREA = new Prefix("ar/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final String[] ALLOWED_PREAMBLES_TYPE = new String[] {"client", "housekeeper"};
    public static final String[] ALLOWED_PREAMBLES_AREA = new String[] {"north", "northeast", "east", "southeast"
            , "south", "southwest", "west", "northwest"};
}
