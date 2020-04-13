package seedu.address.logic.commands.consult;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_TIME_PAST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_PAST;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalConsults.getTypicalConsultTAble;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalStudents.getTypicalStudentTAble;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.consults.AddConsultCommand;
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
import seedu.address.testutil.ConsultBuilder;

public class AddConsultCommandTest {

    private Model model = new ModelManager(getTypicalStudentTAble(), new UserPrefs(), getTypicalConsultTAble(),
        new TutorialTAble(), new ModTAble(), new ReminderTAble());

    @Test
    public void constructor_nullIndexNullConsult_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddConsultCommand(null, null));
    }

    @Test
    public void execute_consultRejectedByModel_addFail() throws Exception {
        Consult validConsult = new ConsultBuilder().withBeginDateTime(INVALID_DATE_TIME_PAST)
                .withEndDateTime(INVALID_END_TIME_PAST).build();
        Index index = Index.fromOneBased(1);

        AddConsultCommand addConsultCommand = new AddConsultCommand(index, validConsult);
        Model expectedModel = new ModelManager(new StudentTAble(model.getStudentTAble()),
            new UserPrefs(), new ConsultTAble(), new TutorialTAble(), new ModTAble(), new ReminderTAble());
        expectedModel.addConsult(validConsult);

        assertCommandFailure(addConsultCommand, model, Messages.MESSAGE_CONSULT_PAST_CONSULT);
    }

    @Test
    public void execute_sameConsult_throwsCommandException() throws ParseException {
        Consult firstConsult = model.getFilteredConsultList().get(INDEX_FIRST.getZeroBased());
        Index index = Index.fromOneBased(1);

        AddConsultCommand addConsultCommand = new AddConsultCommand(index, firstConsult);

        assertCommandFailure(addConsultCommand, model, addConsultCommand.MESSAGE_DUPLICATE_CONSULT);
    }

    @Test
    public void equals() throws ParseException {
        Consult c1 = new ConsultBuilder().withLocation("sr1").build();
        Consult c2 = new ConsultBuilder().withBeginDateTime("2020-02-02 12:00").withEndDateTime("2020-02-02 13:00")
                .build();
        Index index = Index.fromOneBased(1);

        AddConsultCommand addC1Command = new AddConsultCommand(index, c1);
        AddConsultCommand addC2Command = new AddConsultCommand(index, c2);

        // same object -> returns true
        assertTrue(addC1Command.equals(addC1Command));

        // same values -> returns true
        AddConsultCommand addSr1CommandCopy = new AddConsultCommand(index, c1);
        assertTrue(addC1Command.equals(addSr1CommandCopy));

        // different types -> returns false
        assertFalse(addC1Command.equals(1));

        // null -> returns false
        assertFalse(addC1Command.equals(null));

        // different timing -> returns false
        assertFalse(addC1Command.equals(addC2Command));
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
     * A Model stub that contains a single consult.
     */
    private class ModelStubWithConsult extends AddConsultCommandTest.ModelStub {
        private final Consult consult;

        ModelStubWithConsult(Consult consult) {
            requireAllNonNull(consult);
            this.consult = consult;
        }

        @Override
        public boolean hasConsult(Consult consult) {
            requireNonNull(consult);
            return this.consult.equals(consult);
        }
    }

    /**
     * A Model stub that always accept the consult being added.
     */
    private class ModelStubAcceptingConsultAdded extends AddConsultCommandTest.ModelStub {
        final ArrayList<Consult> consultsAdded = new ArrayList<>();

        @Override
        public boolean hasConsult(Consult consult) {
            requireNonNull(consult);
            return consultsAdded.stream().anyMatch(consult::equals);
        }

        @Override
        public void addConsult(Consult consult) {
            requireNonNull(consult);
            consultsAdded.add(consult);
        }

        @Override
        public boolean hasSameDateTime(Consult consult) {
            return consultsAdded.stream().anyMatch(consult::timeClash);
        }

        @Override
        public ReadOnlyStudent getStudentTAble() {
            return new StudentTAble();
        }
    }
}
