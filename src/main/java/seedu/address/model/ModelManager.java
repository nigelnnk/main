package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
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


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final StudentTAble studentTAble;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;
    private final ConsultTAble consultTAble;
    private final FilteredList<Consult> filteredConsults;
    private final TutorialTAble tutorialTAble;
    private final FilteredList<Tutorial> filteredTutorials;
    private final ModTAble modTAble;
    private final FilteredList<Mod> filteredMods;
    private final ReminderTAble reminderTAble;
    private final FilteredList<Reminder> filteredReminders;

    /**
     * Initializes a ModelManager with the given studentTAble and userPrefs.
     */
    public ModelManager(ReadOnlyStudent studentTAble, ReadOnlyUserPrefs userPrefs, ReadOnlyConsult consultTAble,
                        ReadOnlyTutorial tutorialTAble, ReadOnlyMod modTAble, ReadOnlyReminder reminderTAble) {
        super();
        requireAllNonNull(studentTAble, userPrefs, consultTAble, tutorialTAble, modTAble, reminderTAble);

        logger.fine("Initializing with TAble: " + studentTAble + " , user prefs " + userPrefs
            + ", with consults " + consultTAble + " and modules in " + modTAble);

        this.studentTAble = new StudentTAble(studentTAble);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.studentTAble.getStudentList());
        this.consultTAble = new ConsultTAble(consultTAble);
        filteredConsults = new FilteredList<>(this.consultTAble.getAllConsults());
        this.tutorialTAble = new TutorialTAble(tutorialTAble);
        filteredTutorials = new FilteredList<>(this.tutorialTAble.getAllTutorials());
        this.reminderTAble = new ReminderTAble(reminderTAble);
        filteredReminders = new FilteredList<>(this.reminderTAble.getAllReminders());

        this.modTAble = new ModTAble(modTAble);
        tutorialTAble.getAllTutorials().stream()
            .map(Tutorial::getModCode)
            .filter(mc -> !this.modTAble.hasMod(new Mod(mc, "blank")))
            .forEach(mc -> this.modTAble
                .addMod(new Mod(mc, "Blank mod\n(present in tutorial.json but not mod.json)")));
        filteredMods = new FilteredList<>(this.modTAble.getAllMods());
    }

    public ModelManager() {
        this(new StudentTAble(), new UserPrefs(), new ConsultTAble(), new TutorialTAble(),
                new ModTAble(), new ReminderTAble());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getStudentTAbleFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setStudentTAbleFilePath(addressBookFilePath);
    }

    //=========== StudentTAble ================================================================================

    @Override
    public void setStudentTAble(ReadOnlyStudent studentTAble) {
        this.studentTAble.resetData(studentTAble);
    }

    @Override
    public ReadOnlyStudent getStudentTAble() {
        return studentTAble;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return studentTAble.hasStudent(student);
    }

    @Override
    public boolean hasSameMatricNumber(Student student) {
        requireNonNull(student);
        return studentTAble.hasSameMatricNumber(student);
    }

    @Override
    public boolean hasSameEmail(Student student) {
        requireNonNull(student);
        return studentTAble.hasSameEmail(student);
    }

    @Override
    public void deleteStudent(Student target) {
        studentTAble.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        studentTAble.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        studentTAble.setStudent(target, editedStudent);
    }

    @Override
    public void sortStudent() {
        studentTAble.sortStudent();
    }

    /**
     * Gets student with specific index number.
     *
     * @param indexNumber Index number of student.
     * @return Student with index number specified.
     */
    public Student getStudent(int indexNumber) {
        return studentTAble.getStudent(indexNumber);
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return studentTAble.equals(other.studentTAble)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents);
    }

    // Consults section =======================================================

    @Override
    public boolean hasConsult(Consult consult) {
        requireNonNull(consult);
        return consultTAble.hasConsult(consult);
    }

    @Override
    public void addConsult(Consult consult) {
        consultTAble.addConsult(consult);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void deleteConsult(Consult target) {
        consultTAble.removeConsult(target);
    }

    @Override
    public void setConsult(Consult consultToEdit, Consult editedConsult) {
        requireAllNonNull(consultToEdit, editedConsult);
        consultTAble.setConsult(consultToEdit, editedConsult);
    }

    @Override
    public void clearConsults() {
        consultTAble.clearConsults();
    }

    @Override
    public ObservableList<Consult> getFilteredConsultList() {
        return filteredConsults;
    }

    @Override
    public void updateFilteredConsultList(Predicate<Consult> predicate) {
        requireNonNull(predicate);
        filteredConsults.setPredicate(predicate);
    }

    @Override
    public boolean hasSameDateTime(Consult consult) {
        requireAllNonNull(consult, consultTAble);
        return consultTAble.hasSameDateTime(consult);
    }


    @Override
    public boolean hasSameDateTimeEdit(Consult consult) {
        requireAllNonNull(consult, consultTAble);
        return consultTAble.hasSameDateTimeEdit(consult);
    }

    @Override
    public ReadOnlyConsult getConsultTAble() {
        return consultTAble;
    }

    @Override
    public void setConsultTAble(ReadOnlyConsult consultTAble) {
        this.consultTAble.resetData(consultTAble);
    }

    @Override
    public void editConsultStudent(Consult target, Student editedStudent) {
        consultTAble.editConsultStudent(target, editedStudent);
    }


    // Tutorials section ==========================================================================

    @Override
    public boolean hasTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        return tutorialTAble.hasTutorial(tutorial);
    }

    @Override
    public boolean hasTutorialStudent(Tutorial tutorial, Student student) {
        requireAllNonNull(tutorial, student);
        return tutorialTAble.hasTutorialStudent(tutorial, student);
    }

    @Override
    public void addTutorialStudent(Tutorial tutorial, Student student) {
        tutorialTAble.addTutorialStudent(tutorial, student);
    }

    @Override
    public void addTutorial(Tutorial tutorial) {
        tutorialTAble.addTutorial(tutorial);
    }

    @Override
    public void deleteTutorial(Tutorial target) {
        tutorialTAble.removeTutorial(target);
    }

    @Override
    public void deleteTutorialStudent(Tutorial toDeleteFrom, Student target) {
        tutorialTAble.deleteTutorialStudent(toDeleteFrom, target);
    }

    @Override
    public void editTutorialStudent(Tutorial tutorial, Student studentToEdit, Student editedStudent) {
        tutorialTAble.editTutorialStudent(tutorial, studentToEdit, editedStudent);
    }

    @Override
    public void markPresent(Tutorial tutorialToMark, Student studentToMark, int weekZeroBased) {
        tutorialTAble.markPresent(tutorialToMark, studentToMark, weekZeroBased);
    }

    @Override
    public void markAbsent(Tutorial tutorialToMark, Student studentToMark, int weekZeroBased) {
        tutorialTAble.markAbsent(tutorialToMark, studentToMark, weekZeroBased);
    }


    @Override
    public ObservableList<Tutorial> getFilteredTutorialList() {
        return filteredTutorials;
    }

    @Override
    public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
        requireNonNull(predicate);
        filteredTutorials.setPredicate(predicate);
    }

    @Override
    public boolean hasSameTiming(Tutorial tutorial) {
        requireAllNonNull(tutorial, tutorialTAble);
        return tutorialTAble.hasSameTiming(tutorial);
    }

    @Override
    public ReadOnlyTutorial getTutorialTAble() {
        return tutorialTAble;
    }

    // Modules section ==========================================================================

    @Override
    public boolean hasMod(Mod mod) {
        requireNonNull(mod);
        return modTAble.hasMod(mod);
    }

    @Override
    public void addMod(Mod mod) {
        modTAble.addMod(mod);
    }

    @Override
    public void deleteMod(Mod target) {
        modTAble.removeMod(target);
    }

    @Override
    public Optional<Mod> findMod(ModCode modCode) {
        List<Mod> lastShownList = this.getFilteredModList();
        Mod temp = new Mod(modCode, "");
        return lastShownList.stream().filter(x -> x.isSameMod(temp)).findFirst();
    }

    @Override
    public void setMod(Mod target, Mod editedMod) {
        requireAllNonNull(target, editedMod);
        modTAble.setMod(target, editedMod);
    }

    @Override
    public ObservableList<Mod> getFilteredModList() {
        return filteredMods;
    }

    @Override
    public void updateFilteredModList(Predicate<Mod> predicate) {
        requireNonNull(predicate);
        filteredMods.setPredicate(predicate);
    }

    @Override
    public ReadOnlyMod getModTAble() {
        return modTAble;
    }

    @Override
    public ObservableList<Mod> getViewedMod() {
        return modTAble.getViewedModSingletonList();
    }

    @Override
    public void setViewedMod(Mod mod) {
        modTAble.setViewedModSingletonList(mod);
    }


    // Reminders section ==========================================================================

    @Override
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminderTAble.hasReminder(reminder);
    }

    @Override
    public void addReminder(Reminder reminder) {
        reminderTAble.addReminder(reminder);
    }

    @Override
    public void deleteReminder(Reminder target) {
        reminderTAble.removeReminder(target);
    }

    @Override
    public void setReminder(Reminder reminderToEdit, Reminder editedReminder) {
        requireAllNonNull(reminderToEdit, editedReminder);
        reminderTAble.setReminder(reminderToEdit, editedReminder);
    }

    @Override
    public Reminder doneReminder(Reminder target) {
        return reminderTAble.markReminderAsDone(target);
    }

    @Override
    public ObservableList<Reminder> getFilteredReminderList() {
        return filteredReminders;
    }

    @Override
    public ObservableList<Reminder> getUnFilteredReminderList() {
        return reminderTAble.getAllReminders();
    }

    @Override
    public void updateFilteredReminderList(Predicate<Reminder> predicate) {
        requireNonNull(predicate);
        filteredReminders.predicateProperty().bind(Bindings.createObjectBinding(() -> predicate));
    }

    @Override
    public void updateFilteredReminderList(Predicate<Reminder> firstPredicate, Predicate<Reminder> secondPredicate) {
        requireAllNonNull(firstPredicate, secondPredicate);
        filteredReminders.predicateProperty().bind(Bindings.createObjectBinding
                (() -> firstPredicate.and(secondPredicate)));
    }

    @Override
    public ReadOnlyReminder getReminderTAble() {
        return reminderTAble;
    }
}
