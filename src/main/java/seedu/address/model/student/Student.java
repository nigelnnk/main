package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Student in TAble.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student implements Comparable<Student> {

    // Identity fields
    private final Name name;
    private final MatricNumber matricNumber;
    private final Email email;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, MatricNumber matricNumber, Email email, Set<Tag> tags) {
        requireAllNonNull(name, matricNumber, email, tags);
        this.name = name;
        this.matricNumber = matricNumber;
        this.email = email;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public MatricNumber getMatricNumber() {
        return matricNumber;
    }

    public Email getEmail() {
        return email;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both students of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName())
                && otherStudent.getMatricNumber().equals(getMatricNumber())
                && otherStudent.getEmail().equals(getEmail());
    }

    /**
     * Returns true if two students have the same matric number.
     */
    public boolean hasSameMatricNum(Student otherStudent) {
        return otherStudent != null
                && otherStudent.getMatricNumber().equals(getMatricNumber());
    }

    /**
     * Returns true if two students have the same matric number.
     */
    public boolean hasSameEmail(Student otherStudent) {
        return otherStudent != null
                && otherStudent.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(getName())
                && otherStudent.getMatricNumber().equals(getMatricNumber())
                && otherStudent.getEmail().equals(getEmail())
                && otherStudent.getTags().equals(getTags());
    }

    @Override
    public int compareTo(Student student) {
        if (this.getName().toString().compareTo(student.getName().toString()) == 0) {
            return this.getMatricNumber().value.compareTo(student.getMatricNumber().value);
        } else {
            return this.getName().toString().compareTo(student.getName().toString());
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, matricNumber, email, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Matric Number: ")
                .append(getMatricNumber())
                .append(" Email: ")
                .append(getEmail())
                .append(" Tags: ");
        getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(builder::append);
        return builder.toString();
    }

}
