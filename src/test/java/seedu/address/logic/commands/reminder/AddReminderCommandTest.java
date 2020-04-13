package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalReminders.getTypicalReminderTAble;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.reminders.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.consult.ReadOnlyConsult;
import seedu.address.model.event.tutorial.ReadOnlyTutorial;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.mod.ReadOnlyMod;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderTAble;
import seedu.address.model.student.ReadOnlyStudent;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentTAble;
import seedu.address.testutil.ReminderBuilder;

public class AddReminderCommandTest {

    private Model model = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
            new TutorialTAble(), new ModTAble(), getTypicalReminderTAble());

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddReminderCommand(null));
    }

    @Test
    public void execute_reminderAcceptedByModel_addSuccessful() throws Exception {
        Reminder validReminder = new ReminderBuilder().build();

        AddReminderCommand addReminderCommand = new AddReminderCommand(validReminder);
        String expectedMessage = String.format(AddReminderCommand.MESSAGE_SUCCESS, validReminder);
        Model expectedModel = new ModelManager(new StudentTAble(),
                new UserPrefs(), new ConsultTAble(), new TutorialTAble(), new ModTAble(), new ReminderTAble());
        expectedModel.addReminder(validReminder);

        assertCommandSuccess(addReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() throws ParseException {
        Reminder firstReminder = model.getFilteredReminderList().get(INDEX_FIRST.getZeroBased());

        AddReminderCommand addReminderCommand = new AddReminderCommand(firstReminder);

        assertCommandFailure(addReminderCommand, model, addReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    @Test
    public void equals() throws ParseException {
        Reminder r1 = new ReminderBuilder().withDescription("Email T03 tutorial 4 solutions").build();
        Reminder r2 = new ReminderBuilder().withDate("2022-03-25").withTime("14:00").build();

        AddReminderCommand addR1Command = new AddReminderCommand(r1);
        AddReminderCommand addR2Command = new AddReminderCommand(r2);

        // same object -> returns true
        assertTrue(addR1Command.equals(addR1Command));

        // same values -> returns true
        AddReminderCommand addR1CommandCopy = new AddReminderCommand(r1);
        assertTrue(addR1Command.equals(addR1CommandCopy));

        // different types -> returns false
        assertFalse(addR1Command.equals(1));

        // null -> returns false
        assertFalse(addR1Command.equals(null));

        // different information -> returns false
        assertFalse(addR1Command.equals(addR2Command));
    }

    /**
     * A default model stub that have all of the methods failing.
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
            throw new AssertionError("This method should not be called.");
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
        public boolean hasSameTiming(Tutorial tutorial) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ReadOnlyTutorial getTutorialTAble() {
            throw new AssertionError("This method should not be called.");
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
        public void deleteMod(Mod mod) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Mod> findMod(ModCode modCode) {
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
     * A Model stub that contains a single reminder.
     */
    private class ModelStubWithReminder extends AddReminderCommandTest.ModelStub {
        private final Reminder reminder;

        ModelStubWithReminder(Reminder reminder) {
            requireNonNull(reminder);
            this.reminder = reminder;
        }

        @Override
        public boolean hasReminder(Reminder reminder) {
            requireNonNull(reminder);
            return this.reminder.equals(reminder);
        }
    }

    /**
     * A Model stub that always accept the reminder being added.
     */
    private class ModelStubAcceptingReminderAdded extends AddReminderCommandTest.ModelStub {
        final ArrayList<Reminder> remindersAdded = new ArrayList<>();

        @Override
        public boolean hasReminder(Reminder reminder) {
            requireNonNull(reminder);
            return remindersAdded.stream().anyMatch(reminder::equals);
        }

        @Override
        public void addReminder(Reminder reminder) {
            requireNonNull(reminder);
            remindersAdded.add(reminder);
        }

        @Override
        public ReadOnlyStudent getStudentTAble() {
            return new StudentTAble();
        }
    }
}
