package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywordsN;
    private final List<String> keywordsAd;
    private final List<String> keywordsAr;

    public ContainsKeywordsPredicate(List<String> keywordsN, List<String> keywordsAd, List<String> keywordsAr) {
        this.keywordsN = keywordsN;
        this.keywordsAd = keywordsAd;
        this.keywordsAr = keywordsAr;
    }

    @Override
    public boolean test(Person person) {
        boolean hasNameKeyword = true;
        boolean hasAddressKeyword = true;
        boolean hasAreaKeyword = true;
        if (keywordsN.get(0) != "") {
            hasNameKeyword = keywordsN.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        }
        if (keywordsAd.get(0) != "") {
            hasAddressKeyword = keywordsAd.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().toString(), keyword));
        }
        if (keywordsAr.get(0) != "") {
            hasAreaKeyword = keywordsAr.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getArea().toString(), keyword));
        }
        return hasNameKeyword && hasAddressKeyword && hasAreaKeyword;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContainsKeywordsPredicate)) {
            return false;
        }

        ContainsKeywordsPredicate otherContainsKeywordsPredicate = (ContainsKeywordsPredicate) other;
        boolean isSameName =  keywordsN.equals(otherContainsKeywordsPredicate.keywordsN);
        boolean isSameAddress =  keywordsAd.equals(otherContainsKeywordsPredicate.keywordsAd);
        boolean isSameArea =  keywordsAr.equals(otherContainsKeywordsPredicate.keywordsAr);
        return isSameName && isSameAddress && isSameArea;

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywordsN).toString();
    }
}
