package seedu.address.model.student;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class StudentTAble implements ReadOnlyStudent {

    private final UniqueStudentList students;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
    }

    public StudentTAble() {}

    /**
     * Creates an StudentTAble using the Students in the {@code toBeCopied}
     */
    public StudentTAble(ReadOnlyStudent toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    /**
     * Resets the existing data of this {@code StudentTAble} with {@code newData}.
     */
    public void resetData(ReadOnlyStudent newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code students} exists in TAble.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Returns true if a student with the same matric number as {@code students} exists in TAble.
     */
    public boolean hasSameMatricNumber(Student student) {
        requireNonNull(student);
        return students.containsSameMatricNum(student);
    }

    /**
     * Returns true if a student with the same email as {@code students} exists in TAble.
     */
    public boolean hasSameEmail(Student student) {
        requireNonNull(student);
        return students.containsSameEmail(student);
    }

    /**
     * Adds a student to TAble.
     * The student must not already exist in TAble.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in TAble.
     * The student identity of {@code editedStudent} must not be the same as another existing student in TAble.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code StudentTAble}.
     * {@code key} must exist in TAble.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    /**
     * Sorts the list of students from {@code StudentTAble} in alphabetical order.
     */
    public void sortStudent() {
        students.sortStudents();
    }


    /**
     * Returns {@code student} from {@code TAble}.
     * {@code student} must exist in the TAble.
     */
    public Student getStudent(int index) {
        return students.getStudent(index);
    }

    //// util methods

    @Override
    public String toString() {
        return students.asUnmodifiableObservableList().size() + " students";
        // TODO: refine later
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentTAble // instanceof handles nulls
                && students.equals(((StudentTAble) other).students));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
