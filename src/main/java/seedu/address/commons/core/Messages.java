package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_REPEATED_PREFIXES = "Please do not repeat prefixes!\n%1$s";
    public static final String MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX = "The student index provided is invalid.";
    public static final String MESSAGE_STUDENTS_LISTED_OVERVIEW = "%1$d students listed!";
    public static final String MESSAGE_STUDENTS_SORTED_OVERVIEW = "%1$d students sorted alphabetically!";
    public static final String MESSAGE_STUDENT_EMPTY_MATRIC_NUMBER = "Matric Number to be searched cannot be empty!\n";

    public static final String MESSAGE_INVALID_CONSULT_DISPLAYED_INDEX = "The consult index provided is invalid.";
    public static final String MESSAGE_CONSULT_BEGIN_TIME_BEFORE_END_TIME = "The Begin Date Time of the consult is "
            + "after the End Date Time of the consult.";
    public static final String MESSAGE_CONSULT_TIMING_CLASH = "This consult's timing clash with another pre-existing"
            + " consult.";
    public static final String MESSAGE_CONSULT_DIFFERENT_DATE = "The start and end date of the consult is different."
            + "\nThe consult must be held within the same day.";
    public static final String MESSAGE_CONSULT_PAST_CONSULT = "The consult added must be in the future.";


    public static final String MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX = "The tutorial index provided is invalid.";
    public static final String MESSAGE_INVALID_TUTORIAL_STUDENT_INDEX = "The student index provided is invalid.";
    public static final String MESSAGE_INVALID_TUTORIAL_NAME = "The tutorial name provided is invalid. Please "
            + "note that tutorial name cannot be empty and is capped at 8 alphanumeric characters.";
    public static final String MESSAGE_TUTORIAL_BEGIN_TIME_AFTER_END_TIME = "The BeginTime of the tutorial is not "
            + "before the EndTime of the tutorial.";
    public static final String MESSAGE_TUTORIAL_TIMING_CLASH = "This tutorial's timing clashes with an existing"
            + " tutorial's.";
    public static final String MESSAGE_INVALID_WEEK = "The week specified is invalid. Please enter a value between"
            + " 3 and 13 (inclusive).";
    public static final String MESSAGE_INVALID_DAY = "The value of day specified is invalid. Please enter a value"
            + " between 1 and 7 (inclusive), corresponding from Mon to Sun.";

    public static final String MESSAGE_MISSING_MOD = "The module code provided does not exist in TAble.";
    public static final String MESSAGE_INVALID_MODLINK_INDEX = "This index does not correspond to a ModLink in TAble.";

    public static final String MESSAGE_REMINDER_PAST_REMINDER = "The reminder must be in the future.";
    public static final String MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX = "The reminder index provided is invalid.";
    public static final String MESSAGE_REMINDER_ALREADY_DONE = "This reminder is already done!";
    public static final String MESSAGE_REMINDERS_LISTED_OVERVIEW = "%1$d reminders listed!";
    public static final String MESSAGE_REMINDERS_EMPTY_DESCRIPTION = "Description cannot be empty!";
    public static final String MESSAGE_REMINDER_INVALID_SNOOZE_DURATION = "Duration to be snoozed by must be a "
            + "positive integer.";

    public static final String NO_CALENDAR_OPEN_MESSAGE = "There is no calendar window that is currently open.";
}
