package seedu.address.logic.commands.students;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.student.MatricNumber;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentTAble;

/**
 * Clears the StudentTAble.
 */
public class ClearStudentCommand extends Command {

    public static final String COMMAND_WORD = "clearStudent";
    public static final String MESSAGE_SUCCESS = "All students have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();
        for (int i = 0; i < lastShownList.size(); i++) {
            Student studentToDelete = lastShownList.get(i);
            MatricNumber matricNumberToDelete = studentToDelete.getMatricNumber();
            List<Tutorial> currentTutorialList = model.getFilteredTutorialList();
            List<Consult> currentConsultList = model.getFilteredConsultList();
            for (int j = 0; j < currentTutorialList.size(); j++) {
                ArrayList<Student> enrolledStudents = currentTutorialList.get(j).getEnrolledStudents();
                int checker = enrolledStudents.indexOf(studentToDelete);
                if (checker != -1) {
                    model.deleteTutorialStudent(currentTutorialList.get(j), studentToDelete);
                }
            }
            List<Consult> consultsToBeDeleted = new ArrayList<>();
            for (int j = 0; j < currentConsultList.size(); j++) {
                if (matricNumberToDelete.equals(currentConsultList.get(j).getMatricNumber())) {
                    consultsToBeDeleted.add(currentConsultList.get(j));
                }
            }
            for (int j = 0; j < consultsToBeDeleted.size(); j++) {
                model.deleteConsult(consultsToBeDeleted.get(j));
            }
        }
        model.setStudentTAble(new StudentTAble());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
