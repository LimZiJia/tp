package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Area;
import seedu.address.model.person.Email;
import seedu.address.model.person.HousekeepingDetails;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Type;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code String type} into a {@code type}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code type} is invalid.
     */
    public static Type parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (!Type.isValidType(trimmedType)) {
            throw new ParseException(Type.MESSAGE_CONSTRAINTS);
        }
        return new Type(trimmedType);
    }

    /**
     * Parses a {@code String area} into an {@code Area}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code area} is invalid.
     */
    public static Area parseArea(String area) throws ParseException {
        requireNonNull(area);
        String trimmedArea = area.trim();
        if (!Area.isValidArea(trimmedArea)) {
            throw new ParseException(Area.MESSAGE_CONSTRAINTS);
        }
        return new Area(trimmedArea);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    public static LocalDate parseLastHousekeepingDate(String lHD) throws ParseException {
        requireNonNull(lHD);
        return LocalDate.parse(lHD);
    }

    public static Period parsePreferredInterval(String pI) throws ParseException {
        requireNonNull(pI);
        String trimmedPI = pI.trim();
        String[] splitPI = trimmedPI.split("\\s+");
        Period period;
        int quantity = Integer.parseInt(splitPI[0]);
        switch (splitPI[1]) {
            case "days":
                period = Period.ofDays(quantity);
                break;
            case "weeks":
                period = Period.ofWeeks(quantity);
                break;
            case "months":
                period = Period.ofMonths(quantity);
                break;
            case "years":
                period = Period.ofYears(quantity);
                break;
            default:
                throw new ParseException(HousekeepingDetails.MESSAGE_CONSTRAINTS);
        }
        return period;
    }

    public static HousekeepingDetails parseHousekeepingDetails(Optional<String> details) throws ParseException {
        if (details.isEmpty()) {
            return HousekeepingDetails.empty;
        }
        String trimmedDetails = details.get().trim();
        if (!HousekeepingDetails.isValidHousekeepingDetailsUser(trimmedDetails)) {
            throw new ParseException(HousekeepingDetails.MESSAGE_CONSTRAINTS);
        }

        String[] s = trimmedDetails.split(" ");
        LocalDate date = LocalDate.parse(s[0]);
        Period period;
        int quantity = Integer.parseInt(s[1]);

        switch (s[2]) {
        case "days":
            period = Period.ofDays(quantity);
            break;
        case "weeks":
            period = Period.ofWeeks(quantity);
            break;
        case "months":
            period = Period.ofMonths(quantity);
            break;
        case "years":
            period = Period.ofYears(quantity);
            break;
        default:
            throw new ParseException(HousekeepingDetails.MESSAGE_CONSTRAINTS);
        }

        return new HousekeepingDetails(date, period);
    }
}
