package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
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

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
    Predicate<Consult> PREDICATE_SHOW_ALL_CONSULTS = unused -> true;
    Predicate<Tutorial> PREDICATE_SHOW_ALL_TUTORIALS = unused -> true;
    Predicate<Mod> PREDICATE_SHOW_ALL_MODS = unused -> true;
    Predicate<Reminder> PREDICATE_SHOW_ALL_REMINDERS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces TAble data with the data in {@code studentTAble}.
     */
    void setStudentTAble(ReadOnlyStudent studentTAble);

    /** Returns the StudentTAble */
    ReadOnlyStudent getStudentTAble();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the list of students.
     */
    boolean hasStudent(Student student);

    /**
     * Returns true if a student with the same matric number as {@code student} exists in the list of students.
     */
    boolean hasSameMatricNumber(Student student);

    /**
     * Returns true if a student with the same email as {@code student} exists in the list of students.
     */
    boolean hasSameEmail(Student student);

    /**
     * Deletes the given student.
     * The student must exist in TAble
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the list of students.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the list of students.
     * The student identity of {@code editedStudent} must not be the same as another existing student in Table.
     */
    void setStudent(Student target, Student editedStudent);

    /**
     * Sorts the current list of student in alphabetical order.
     */
    void sortStudent();

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    // Consult-level operations =======================================================================================

    /**
     * Returns true if a consult with the same identity as {@code consult} exists in TAble.
     */
    boolean hasConsult(Consult consult);

    /**
     * Adds the given consult.
     * {@code consult} must not already exist in TAble.
     */
    void addConsult(Consult consult);

    /**
     * Deletes the given consult.
     * The consult must exist in TAble.
     */
    void deleteConsult(Consult target);

    /**
     * Replaces the given consult {@code consultToEdit} with {@code editedConsult}.
     * {@code consultToEdit} must exist in TAble.
     * The student identity of {@code editedConsult} must not be the same as another existing consult in TAble.
     */
    void setConsult(Consult consultToEdit, Consult editedConsult);

    /**
     * Deletes all consults in the Consult TAble.
     */
    void clearConsults();

    /** Returns an unmodifiable view of the filtered consult list */
    ObservableList<Consult> getFilteredConsultList();

    /**
     * Updates the filter of the filtered consult list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredConsultList(Predicate<Consult> predicate);

    /**
     * Returns true if a {@code consult}'s timing clashes with another {@code consult}'s timing in TAble.
     */
    boolean hasSameDateTime(Consult consult);

    /**
     * Returns true if the list contains another consult which timing clashes with the argument.
     * Used by the editConsult command.
     */
    boolean hasSameDateTimeEdit(Consult editedConsult);


    /** Returns the Consult TAble */
    ReadOnlyConsult getConsultTAble();

    /**
     * Edits the student identify of a given consult.
     * The consult must exist in TAble.
     */
    void editConsultStudent(Consult target, Student editedStudent);

    // Tutorial-level operations =====================================================================================

    /**
     * Returns true if a tutorial with the same identity as {@code tutorial} exists in TAble.
     */
    boolean hasTutorial(Tutorial tutorial);

    /**
     * Returns true if a specified tutorial contains the identical {@code student}.
     */
    boolean hasTutorialStudent(Tutorial tutorial, Student student);

    /**
     * Adds the given tutorial.
     * {@code tutorial} must not already exist in TAble.
     */
    void addTutorial(Tutorial tutorial);

    /**
     * Adds the given student to the given tutorial.
     * {@code tutorial} and {@code student} must already exist in TAble.
     */
    void addTutorialStudent(Tutorial tutorial, Student student);

    /**
     * Deletes the given tutorial.
     * The tutorial must exist in TAble.
     */
    void deleteTutorial(Tutorial target);

    /**
     * Deletes the given student from  the given tutorial.
     * {@code toDeleteFrom} and {@code target} must already exist in TutorialTAble.
     */
    void deleteTutorialStudent(Tutorial toDeleteFrom, Student target);

    /**
     * Edits the equivalent student from the given tutorial and updates the student.
     * {@code toEditFrom} and {@code studentToEdit} must already exist in TutorialTAble.
     */
    void editTutorialStudent(Tutorial toEditFrom, Student studentToEdit, Student editedStudent);

    /**
     * Marks the given student in the given tutorial as present in {@code weekZeroBased}.
     * {@code tutorialToMark} and {@code studentToMark} must already exist in TutorialTAble.
     */
    void markPresent(Tutorial tutorialToMark, Student studentToMark, int weekZeroBased);

    /**
     * Marks the given student in the given tutorial as absent in {@code weekZeroBased}.
     * {@code tutorialToMark} and {@code studentToMark} must already exist in TutorialTAble.
     */
    void markAbsent(Tutorial tutorialToMark, Student studentToMark, int weekZeroBased);

    /** Returns an unmodifiable view of the filtered tutorial list */
    ObservableList<Tutorial> getFilteredTutorialList();

    /**
     * Updates the filter of the filtered tutorial list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTutorialList(Predicate<Tutorial> predicate);

    /**
     * Returns true if a {@code tutorial}'s timing clashes with another {@code tutorial}'s timing in TAble.
     */
    boolean hasSameTiming(Tutorial tutorial);

    /** Returns the Tutorial TAble */
    ReadOnlyTutorial getTutorialTAble();

    // Mod-level operations =====================================================================================

    /**
     * Returns true if a module with the same identity as {@code mod} exists in TAble.
     */
    boolean hasMod(Mod mod);

    /**
     * Adds the given module.
     * {@code mod} must not already exist in TAble.
     */
    void addMod(Mod mod);

    /**
     * Deletes the given module.
     * The module must exist in TAble.
     */
    void deleteMod(Mod mod);

    /**
     * Finds the corresponding module given a {@code modCode}.
     * As the module may not exist in TAble, an optional Mod is returned.
     * @param modCode module code of the module to be found
     */
    Optional<Mod> findMod(ModCode modCode);

    /**
     * Replaces the given module {@code target} with {@code editedMod}.
     * {@code target} must exist in the TAble.
     * The module identity of {@code editedMod} must not be the same as another existing Mod in Table.
     */
    void setMod(Mod target, Mod editedMod);

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Mod> getFilteredModList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModList(Predicate<Mod> predicate);

    /** Returns the module TAble */
    ReadOnlyMod getModTAble();

    /**
     * Returns currently viewed Mod.
     */
    ObservableList<Mod> getViewedMod();

    /**
     * Sets current view to a different {@code mod}.
     */
    void setViewedMod(Mod mod);

    // Reminder-level operations =====================================================================================

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in TAble.
     */
    boolean hasReminder(Reminder reminder);

    /**
     * Adds the given reminder.
     * {@code reminder} must not already exist in TAble.
     */
    void addReminder(Reminder reminder);

    /**
     * Deletes the given reminder.
     * The reminder must exist in TAble.
     */
    void deleteReminder(Reminder target);

    /**
     * Replaces the given reminder {@code reminderToEdit} with {@code editedReminder}.
     * {@code reminderToEdit} must exist in TAble.
     * The identity of {@code editedReminder} must not be the same as another existing reminder in TAble.
     */
    void setReminder(Reminder reminderToEdit, Reminder editedReminder);

    /**
     * Marks the given reminder as done.
     * The reminder must exist in TAble.
     */
    Reminder doneReminder(Reminder target);

    /** Returns an unmodifiable view of the filtered reminder list */
    ObservableList<Reminder> getFilteredReminderList();

    /** Returns an unmodifiable view of the unfiltered reminder list */
    ObservableList<Reminder> getUnFilteredReminderList();

    /**
     * Updates the filter of the filtered reminder list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReminderList(Predicate<Reminder> predicate);

    /**
     * Updates the filter of the filtered reminder list to filter by the given {@code predicates}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReminderList(Predicate<Reminder> firstPredicate, Predicate<Reminder> secondPredicate);

    /** Returns the Reminder TAble */
    ReadOnlyReminder getReminderTAble();


    void setConsultTAble(ReadOnlyConsult consultTAble);

}
