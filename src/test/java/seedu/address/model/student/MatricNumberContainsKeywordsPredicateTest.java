package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudentBuilder;

public class MatricNumberContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("A0183936R");
        List<String> secondPredicateKeywordList = Arrays.asList("A0111111X");

        MatricNumberContainsKeywordsPredicate firstPredicate =
                new MatricNumberContainsKeywordsPredicate(firstPredicateKeywordList);
        MatricNumberContainsKeywordsPredicate secondPredicate =
                new MatricNumberContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MatricNumberContainsKeywordsPredicate firstPredicateCopy =
                new MatricNumberContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different student -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matricNumberContainsKeywords_returnsTrue() {

        // Only one matching keyword
        MatricNumberContainsKeywordsPredicate predicate =
                new MatricNumberContainsKeywordsPredicate(Arrays.asList("A0183936R"));
        assertTrue(predicate.test(new StudentBuilder().withMatricNumber("A0183936R").build()));

        // Mixed-case keywords
        predicate = new MatricNumberContainsKeywordsPredicate(Arrays.asList("a0183936r"));
        assertTrue(predicate.test(new StudentBuilder().withMatricNumber("A0183936R").build()));
    }

    @Test
    public void test_matricNumberDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MatricNumberContainsKeywordsPredicate predicate =
                new MatricNumberContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new StudentBuilder().withMatricNumber("A0123456X").build()));

        // Non-matching keyword
        predicate = new MatricNumberContainsKeywordsPredicate(Arrays.asList("A0123456X"));
        assertFalse(predicate.test(new StudentBuilder().withMatricNumber("A0654321X").build()));

        // Keywords match name, email and address, but does not match matric number
        predicate = new MatricNumberContainsKeywordsPredicate(
                Arrays.asList("A0123456A", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new StudentBuilder().withName("Alice").withMatricNumber("A0123456B")
                .withEmail("alice@email.com").build()));
    }
}
