package seedu.address.logic.commands.students;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Sorts all students in TAble in alphabetical order and lists them.
 * In the event there are multiple students with the same full name, the matric number will be used to sort the group
 * of students with the same name in ascending order.
 */
public class SortStudentCommand extends Command {

    public static final String COMMAND_WORD = "sortStudent";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortStudent();
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(Messages.MESSAGE_STUDENTS_SORTED_OVERVIEW,
                model.getFilteredStudentList().size()), false, false, true, false);
    }
}
