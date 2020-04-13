package seedu.address.logic.commands.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRICNUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRICNUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalStudents.CARL;
import static seedu.address.testutil.TypicalStudents.getTypicalStudentTAble;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.students.FindStudentMatricNumberCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.reminder.ReminderTAble;
import seedu.address.model.student.MatricNumberContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindStudentCommand}.
 */
public class FindStudentMatricNumberCommandTest {
    private Model model = new ModelManager(getTypicalStudentTAble(), new UserPrefs(), new ConsultTAble(),
        new TutorialTAble(), new ModTAble(), new ReminderTAble());
    private Model expectedModel = new ModelManager(getTypicalStudentTAble(), new UserPrefs(), new ConsultTAble(),
        new TutorialTAble(), new ModTAble(), new ReminderTAble());

    @Test
    public void equals() {
        MatricNumberContainsKeywordsPredicate firstPredicate =
                new MatricNumberContainsKeywordsPredicate(Collections.singletonList(VALID_MATRICNUMBER_AMY));
        MatricNumberContainsKeywordsPredicate secondPredicate =
                new MatricNumberContainsKeywordsPredicate(Collections.singletonList(VALID_MATRICNUMBER_BOB));

        FindStudentMatricNumberCommand findFirstCommand = new FindStudentMatricNumberCommand(firstPredicate);
        FindStudentMatricNumberCommand findSecondCommand = new FindStudentMatricNumberCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindStudentMatricNumberCommand findFirstCommandCopy = new FindStudentMatricNumberCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 0);
        MatricNumberContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindStudentMatricNumberCommand command = new FindStudentMatricNumberCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_oneKeyword_oneStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        MatricNumberContainsKeywordsPredicate predicate = preparePredicate("A3333333C");
        FindStudentMatricNumberCommand command = new FindStudentMatricNumberCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredStudentList());
    }

    @Test
    public void execute_oneKeyword_noStudentsFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 0);
        MatricNumberContainsKeywordsPredicate predicate = preparePredicate("A3456789C");
        FindStudentMatricNumberCommand command = new FindStudentMatricNumberCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private MatricNumberContainsKeywordsPredicate preparePredicate(String userInput) {
        return new MatricNumberContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
