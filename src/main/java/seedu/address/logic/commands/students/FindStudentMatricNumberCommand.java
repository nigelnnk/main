package seedu.address.logic.commands.students;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.student.MatricNumberContainsKeywordsPredicate;

/**
 * Finds and lists the students in TAble whose matric number matches the argument keyword given.
 * Keyword matching is case insensitive.
 */
public class FindStudentMatricNumberCommand extends Command {

    public static final String COMMAND_WORD = "findStudentMatricNumber";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose matric numbers matches the"
            + " specified matric number (case-insensitive) given and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_MATRIC_NUMBER
            + "KEYWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MATRIC_NUMBER + "A0123456X";


    private final MatricNumberContainsKeywordsPredicate predicate;

    public FindStudentMatricNumberCommand(MatricNumberContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()),
                false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindStudentMatricNumberCommand // instanceof handles nulls
                && predicate.equals(((FindStudentMatricNumberCommand) other).predicate)); // state check
    }
}
