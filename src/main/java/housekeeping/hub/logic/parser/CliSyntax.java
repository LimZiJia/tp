package housekeeping.hub.logic.parser;


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
    public static final Prefix PREFIX_DETAILS = new Prefix("d/");
    public static final Prefix PREFIX_LHD = new Prefix("lhd/");
    public static final Prefix PREFIX_PI = new Prefix("pi/");
    public static final Prefix PREFIX_BD = new Prefix("bd/");
    public static final Prefix PREFIX_DEFERMENT = new Prefix("d/");
    public static final String[] ALLOWED_PREAMBLES = new String[] {"client", "housekeeper"};
    public static final String[] ALLOWED_PREAMBLES_TYPE = new String[] {"client", "housekeeper"};
    public static final String[] ALLOWED_PREAMBLES_AREA = new String[] {"north", "northeast", "east", "southeast",
        "south", "southwest", "west", "northwest"};
}
