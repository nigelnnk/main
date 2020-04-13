package seedu.address.logic.parser.student;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.commands.CommandTestUtil.MATRICNUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRICNUMBER_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.students.FindStudentMatricNumberCommand;
import seedu.address.logic.parser.students.FindStudentMatricNumberCommandParser;
import seedu.address.model.student.MatricNumberContainsKeywordsPredicate;

public class FindStudentMatricNumberCommandParserTest {

    private FindStudentMatricNumberCommandParser parser = new FindStudentMatricNumberCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindStudentMatricNumberCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        MatricNumberContainsKeywordsPredicate predicate =
                new MatricNumberContainsKeywordsPredicate(Arrays.asList(VALID_MATRICNUMBER_AMY));
        FindStudentMatricNumberCommand expectedFindCommand =
                new FindStudentMatricNumberCommand(predicate);
        assertParseSuccess(parser, MATRICNUMBER_DESC_AMY, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + MATRICNUMBER_DESC_AMY + " \n \t  \t",
                expectedFindCommand);
        assertParseFailure(parser, MATRICNUMBER_DESC_AMY + MATRICNUMBER_DESC_AMY,
                String.format(MESSAGE_REPEATED_PREFIXES, FindStudentMatricNumberCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindStudentMatricNumberCommand.MESSAGE_USAGE));
    }

}
