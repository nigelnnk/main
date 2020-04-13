package seedu.address.logic.commands.mods;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME;
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
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.consult.ReadOnlyConsult;
import seedu.address.model.event.tutorial.ReadOnlyTutorial;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.mod.ReadOnlyMod;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.student.ReadOnlyStudent;
import seedu.address.model.student.Student;

public class AddModCommandTest {

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModCommand(null, VALID_MODNAME));
        assertThrows(NullPointerException.class, () -> new AddModCommand(new ModCode(VALID_MODCODE), null));
    }

    @Test
    public void execute_modAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingModAdded modelStub = new ModelStubAcceptingModAdded();
        Mod expectedMod = new Mod(new ModCode(VALID_MODCODE), VALID_MODNAME);

        CommandResult commandResult = new AddModCommand(new ModCode(VALID_MODCODE), VALID_MODNAME).execute(modelStub);

        assertEquals(String.format(AddModCommand.MESSAGE_SUCCESS, expectedMod), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedMod), modelStub.modsAdded);
    }


    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Mod expectedMod = new Mod(new ModCode(VALID_MODCODE), VALID_MODNAME);
        AddModCommand addStudentCommand = new AddModCommand(new ModCode(VALID_MODCODE), VALID_MODNAME);
        ModelStub modelStub = new ModelStubWithMod(expectedMod);

        assertThrows(CommandException.class,
            AddModCommand.MESSAGE_DUPLICATE_MOD, ()-> addStudentCommand.execute(modelStub));
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
        public void addStudent(Student mod) {
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
        public boolean hasStudent(Student mod) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameMatricNumber(Student mod) {
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
        public boolean hasTutorialStudent(Tutorial tutorial, Student mod) {
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
            throw new AssertionError("This method should not be called");
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
        public Reminder doneReminder(Reminder target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setReminder(Reminder reminderToEdit, Reminder editedReminder) {
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
     * A Model stub that contains a single Mod.
     */
    private class ModelStubWithMod extends ModelStub {
        private final Mod mod;

        ModelStubWithMod(Mod mod) {
            requireNonNull(mod);
            this.mod = mod;
        }

        @Override
        public boolean hasMod(Mod mod) {
            requireNonNull(mod);
            return this.mod.isSameMod(mod);
        }
    }

    /**
     * A Model stub that always accepts the module being added.
     */
    private class ModelStubAcceptingModAdded extends ModelStub {
        final ArrayList<Mod> modsAdded = new ArrayList<>();

        @Override
        public boolean hasMod(Mod mod) {
            requireNonNull(mod);
            return modsAdded.stream().anyMatch(mod::isSameMod);
        }

        @Override
        public void addMod(Mod mod) {
            requireNonNull(mod);
            modsAdded.add(mod);
        }

        @Override
        public ReadOnlyMod getModTAble() {
            return new ModTAble();
        }
    }
}
