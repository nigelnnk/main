package seedu.address.logic.commands.tutorial;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.tutorials.AddTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.consult.ReadOnlyConsult;
import seedu.address.model.event.tutorial.ReadOnlyTutorial;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ReadOnlyMod;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.student.ReadOnlyStudent;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentTAble;
import seedu.address.testutil.TutorialBuilder;

public class AddTutorialCommandTest {

    @Test
    public void constructor_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTutorialCommand(null));
    }


    @Test
    public void execute_tutorialAcceptedByModel_addSuccessful() throws Exception {
        AddTutorialCommandTest.ModelStubAcceptingTutorialAdded modelStub =
                new AddTutorialCommandTest.ModelStubAcceptingTutorialAdded();
        Tutorial validTutorial = new TutorialBuilder().build();

        CommandResult commandResult = new AddTutorialCommand(validTutorial).execute(modelStub);

        assertEquals(String.format(AddTutorialCommand.MESSAGE_SUCCESS, validTutorial),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTutorial), modelStub.tutorialsAdded);
    }


    @Test
    public void execute_duplicateTutorial_throwsCommandException() throws ParseException {
        Tutorial validTutorial = new TutorialBuilder().build();
        AddTutorialCommand addTutorialCommand = new AddTutorialCommand(validTutorial);
        AddTutorialCommandTest.ModelStub modelStub = new AddTutorialCommandTest.ModelStubWithTutorial(validTutorial);

        assertThrows(CommandException.class, AddTutorialCommand.MESSAGE_DUPLICATE_TUTORIAL, ()
            -> addTutorialCommand.execute(modelStub));
    }

    @Test
    public void equals() throws ParseException {
        // have to add builder for modCode, name, day
        Tutorial t1 = new TutorialBuilder().withLocation("SR1").build();
        Tutorial t2 = new TutorialBuilder().withBeginTime("10:00").withEndTime("12:00").build();
        Tutorial t3 = new TutorialBuilder().withModCode("CS2100").build();
        Tutorial t4 = new TutorialBuilder().withTutorialName("T01").build();
        Tutorial t5 = new TutorialBuilder().withDay("1").build();
        Tutorial t6 = new TutorialBuilder().withLocation("SR3").build();

        AddTutorialCommand addT1Command = new AddTutorialCommand(t1);
        AddTutorialCommand addT2Command = new AddTutorialCommand(t2);
        AddTutorialCommand addT3command = new AddTutorialCommand(t3);
        AddTutorialCommand addT4command = new AddTutorialCommand(t4);
        AddTutorialCommand addT5command = new AddTutorialCommand(t5);
        AddTutorialCommand addT6command = new AddTutorialCommand(t6);

        // same object -> returns true
        assertTrue(addT1Command.equals(addT1Command));

        // same values -> returns true
        AddTutorialCommand addSr1CommandCopy = new AddTutorialCommand(t1);
        assertTrue(addT1Command.equals(addSr1CommandCopy));

        // different types -> returns false
        assertFalse(addT1Command.equals(1));

        // null -> returns false
        assertFalse(addT1Command.equals(null));

        // different timing -> returns true
        assertTrue(addT1Command.equals(addT2Command));

        // different location -> returns true
        assertTrue(addT6command.equals(addT1Command));

        // different day -> returns true
        assertTrue(addT5command.equals(addT1Command));

        // different module -> returns false
        assertFalse(addT3command.equals(addT1Command));

        // different tutorial name -> returns false
        assertFalse(addT4command.equals(addT1Command));
    }


    /**
     * A default model stub that has all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudentTAble(ReadOnlyStudent newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyStudent getStudentTAble() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameMatricNumber(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameEmail(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortStudent() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasConsult(Consult consult) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addConsult(Consult consult) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteConsult(Consult target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setConsult(Consult consultToEdit, Consult editedConsult) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearConsults() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Consult> getFilteredConsultList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredConsultList(Predicate<Consult> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameDateTime(Consult consult) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasSameDateTimeEdit(Consult editedConsult) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ReadOnlyConsult getConsultTAble() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void editConsultStudent(Consult target, Student editedStudent) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasTutorial(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTutorialStudent(Tutorial tutorial, Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTutorial(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void addTutorialStudent(Tutorial tutorial, Student matric) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTutorial(Tutorial target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTutorialStudent(Tutorial toDeleteFrom, Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editTutorialStudent(Tutorial toEditFrom, Student studentToEdit, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markPresent(Tutorial tutorialToMark, Student studentToMark, int weekZeroBased) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markAbsent(Tutorial tutorialToMark, Student studentToMark, int weekZeroBased) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTutorial getTutorialTAble() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameTiming(Tutorial tutorial) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasMod(Mod mod) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMod(Mod mod) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Mod> findMod(ModCode modCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMod(Mod mod) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMod(Mod target, Mod editedMod) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Mod> getFilteredModList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredModList(Predicate<Mod> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyMod getModTAble() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Mod> getViewedMod() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setViewedMod(Mod mod) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReminder(Reminder target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setReminder(Reminder reminderToEdit, Reminder editedReminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Reminder doneReminder(Reminder target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Reminder> getFilteredReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Reminder> getUnFilteredReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReminderList(Predicate<Reminder> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReminderList(Predicate<Reminder> firstPredicate,
                                               Predicate<Reminder> secondPredicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyReminder getReminderTAble() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setConsultTAble(ReadOnlyConsult consultTAble) {
            throw new AssertionError("This method should not be called.");
        }
    }


    /**
     * A Model stub that contains a single tutorial.
     */
    private class ModelStubWithTutorial extends AddTutorialCommandTest.ModelStub {
        private final Tutorial tutorial;

        ModelStubWithTutorial(Tutorial tutorial) {
            requireNonNull(tutorial);
            this.tutorial = tutorial;
        }

        @Override
        public boolean hasTutorial(Tutorial tutorial) {
            requireNonNull(tutorial);
            return this.tutorial.equals(tutorial);
        }
    }

    /**
     * A Model stub that always accepts the tutorial being added.
     */
    private class ModelStubAcceptingTutorialAdded extends AddTutorialCommandTest.ModelStub {
        final ArrayList<Tutorial> tutorialsAdded = new ArrayList<>();

        @Override
        public boolean hasTutorial(Tutorial tutorial) {
            requireNonNull(tutorial);
            return tutorialsAdded.stream().anyMatch(tutorial::equals);
        }

        @Override
        public void addTutorial(Tutorial tutorial) {
            requireNonNull(tutorial);
            tutorialsAdded.add(tutorial);
        }

        @Override
        public boolean hasSameTiming(Tutorial tutorial) {
            return tutorialsAdded.stream().anyMatch(tutorial::timeClash);
        }

        @Override
        public boolean hasMod(Mod mod) {
            return true;
        }

        @Override
        public ReadOnlyStudent getStudentTAble() {
            return new StudentTAble();
        }
    }

}
