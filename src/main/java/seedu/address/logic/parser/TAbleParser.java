package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.CloseCalendarCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ViewCalendarCommand;
import seedu.address.logic.commands.consults.AddConsultCommand;
import seedu.address.logic.commands.consults.DeleteConsultCommand;
import seedu.address.logic.commands.consults.EditConsultCommand;
import seedu.address.logic.commands.consults.ListConsultCommand;
import seedu.address.logic.commands.mods.AddModCommand;
import seedu.address.logic.commands.mods.AddModLinkCommand;
import seedu.address.logic.commands.mods.ClearModLinksCommand;
import seedu.address.logic.commands.mods.CopyModLinkCommand;
import seedu.address.logic.commands.mods.DeleteModCommand;
import seedu.address.logic.commands.mods.ListModCommand;
import seedu.address.logic.commands.mods.NoteModCommand;
import seedu.address.logic.commands.mods.ViewModInfoCommand;
import seedu.address.logic.commands.reminders.AddReminderCommand;
import seedu.address.logic.commands.reminders.DeleteReminderCommand;
import seedu.address.logic.commands.reminders.DoneReminderCommand;
import seedu.address.logic.commands.reminders.EditReminderCommand;
import seedu.address.logic.commands.reminders.FindReminderCommand;
import seedu.address.logic.commands.reminders.ListReminderCommand;
import seedu.address.logic.commands.reminders.SnoozeReminderCommand;
import seedu.address.logic.commands.students.AddStudentCommand;
import seedu.address.logic.commands.students.ClearStudentCommand;
import seedu.address.logic.commands.students.DeleteStudentCommand;
import seedu.address.logic.commands.students.EditStudentCommand;
import seedu.address.logic.commands.students.FindStudentCommand;
import seedu.address.logic.commands.students.FindStudentMatricNumberCommand;
import seedu.address.logic.commands.students.ListStudentCommand;
import seedu.address.logic.commands.students.SortStudentCommand;
import seedu.address.logic.commands.tutorials.AddTutorialCommand;
import seedu.address.logic.commands.tutorials.AddTutorialStudentCommand;
import seedu.address.logic.commands.tutorials.CopyTutorialEmailsCommand;
import seedu.address.logic.commands.tutorials.DeleteTutorialCommand;
import seedu.address.logic.commands.tutorials.DeleteTutorialStudentCommand;
import seedu.address.logic.commands.tutorials.ExportTutorialAttendanceCommand;
import seedu.address.logic.commands.tutorials.ListAttendanceCommand;
import seedu.address.logic.commands.tutorials.ListTutorialCommand;
import seedu.address.logic.commands.tutorials.MarkAbsentCommand;
import seedu.address.logic.commands.tutorials.MarkPresentCommand;
import seedu.address.logic.parser.consults.AddConsultCommandParser;
import seedu.address.logic.parser.consults.ClearConsultCommand;
import seedu.address.logic.parser.consults.DeleteConsultCommandParser;
import seedu.address.logic.parser.consults.EditConsultCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.mods.AddModCommandParser;
import seedu.address.logic.parser.mods.AddModLinkCommandParser;
import seedu.address.logic.parser.mods.ClearModLinksCommandParser;
import seedu.address.logic.parser.mods.CopyModLinkCommandParser;
import seedu.address.logic.parser.mods.DeleteModCommandParser;
import seedu.address.logic.parser.mods.NoteModCommandParser;
import seedu.address.logic.parser.mods.ViewModInfoCommandParser;
import seedu.address.logic.parser.reminders.AddReminderCommandParser;
import seedu.address.logic.parser.reminders.DeleteReminderCommandParser;
import seedu.address.logic.parser.reminders.DoneReminderCommandParser;
import seedu.address.logic.parser.reminders.EditReminderCommandParser;
import seedu.address.logic.parser.reminders.FindReminderCommandParser;
import seedu.address.logic.parser.reminders.SnoozeReminderCommandParser;
import seedu.address.logic.parser.students.AddStudentCommandParser;
import seedu.address.logic.parser.students.DeleteStudentCommandParser;
import seedu.address.logic.parser.students.EditStudentCommandParser;
import seedu.address.logic.parser.students.FindStudentCommandParser;
import seedu.address.logic.parser.students.FindStudentMatricNumberCommandParser;
import seedu.address.logic.parser.tutorials.AddTutorialCommandParser;
import seedu.address.logic.parser.tutorials.AddTutorialStudentCommandParser;
import seedu.address.logic.parser.tutorials.CopyTutorialEmailsCommandParser;
import seedu.address.logic.parser.tutorials.DeleteTutorialCommandParser;
import seedu.address.logic.parser.tutorials.DeleteTutorialStudentCommandParser;
import seedu.address.logic.parser.tutorials.ExportTutorialAttendanceCommandParser;
import seedu.address.logic.parser.tutorials.ListAttendanceCommandParser;
import seedu.address.logic.parser.tutorials.MarkAbsentCommandParser;
import seedu.address.logic.parser.tutorials.MarkPresentCommandParser;

/**
 * Parses user input.
 */
public class TAbleParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(arguments);

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(arguments);

        case ClearStudentCommand.COMMAND_WORD:
            return new ClearStudentCommand();

        case FindStudentCommand.COMMAND_WORD:
            return new FindStudentCommandParser().parse(arguments);

        case FindStudentMatricNumberCommand.COMMAND_WORD:
            return new FindStudentMatricNumberCommandParser().parse(arguments);

        case ListStudentCommand.COMMAND_WORD:
            return new ListStudentCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SortStudentCommand.COMMAND_WORD:
            return new SortStudentCommand();


            // ======================================== Consult Commands ==============================

        case AddConsultCommand.COMMAND_WORD:
            return new AddConsultCommandParser().parse(arguments);

        case ListConsultCommand.COMMAND_WORD:
            return new ListConsultCommand();

        case DeleteConsultCommand.COMMAND_WORD:
            return new DeleteConsultCommandParser().parse(arguments);

        case EditConsultCommand.COMMAND_WORD:
            return new EditConsultCommandParser().parse(arguments);

        case ClearConsultCommand.COMMAND_WORD:
            return new ClearConsultCommand();

        // ======================================== Tutorial Commands =============================

        case AddTutorialCommand.COMMAND_WORD:
            return new AddTutorialCommandParser().parse(arguments);

        case AddTutorialStudentCommand.COMMAND_WORD:
            return new AddTutorialStudentCommandParser().parse(arguments);

        case DeleteTutorialStudentCommand.COMMAND_WORD:
            return new DeleteTutorialStudentCommandParser().parse(arguments);

        case MarkPresentCommand.COMMAND_WORD:
            return new MarkPresentCommandParser().parse(arguments);

        case MarkAbsentCommand.COMMAND_WORD:
            return new MarkAbsentCommandParser().parse(arguments);

        case DeleteTutorialCommand.COMMAND_WORD:
            return new DeleteTutorialCommandParser().parse(arguments);

        case ListTutorialCommand.COMMAND_WORD:
            return new ListTutorialCommand();

        case ListAttendanceCommand.COMMAND_WORD:
            return new ListAttendanceCommandParser().parse(arguments);

        case CopyTutorialEmailsCommand.COMMAND_WORD:
            return new CopyTutorialEmailsCommandParser().parse(arguments);

        case ExportTutorialAttendanceCommand.COMMAND_WORD:
            return new ExportTutorialAttendanceCommandParser().parse(arguments);

        // ======================================== Mod Commands ==================================

        case AddModCommand.COMMAND_WORD:
            return new AddModCommandParser().parse(arguments);

        case DeleteModCommand.COMMAND_WORD:
            return new DeleteModCommandParser().parse(arguments);

        case ListModCommand.COMMAND_WORD:
            return new ListModCommand();

        case NoteModCommand.COMMAND_WORD:
            return new NoteModCommandParser().parse(arguments);

        case AddModLinkCommand.COMMAND_WORD:
            return new AddModLinkCommandParser().parse(arguments);

        case ClearModLinksCommand.COMMAND_WORD:
            return new ClearModLinksCommandParser().parse(arguments);

        case ViewModInfoCommand.COMMAND_WORD:
            return new ViewModInfoCommandParser().parse(arguments);

        case CopyModLinkCommand.COMMAND_WORD:
            return new CopyModLinkCommandParser().parse(arguments);

        // ======================================== Reminder Commands =============================

        case AddReminderCommand.COMMAND_WORD:
            return new AddReminderCommandParser().parse(arguments);

        case DeleteReminderCommand.COMMAND_WORD:
            return new DeleteReminderCommandParser().parse(arguments);

        case DoneReminderCommand.COMMAND_WORD:
            return new DoneReminderCommandParser().parse(arguments);

        case EditReminderCommand.COMMAND_WORD:
            return new EditReminderCommandParser().parse(arguments);

        case FindReminderCommand.COMMAND_WORD:
            return new FindReminderCommandParser().parse(arguments);

        case ListReminderCommand.COMMAND_WORD:
            return new ListReminderCommand();

        case SnoozeReminderCommand.COMMAND_WORD:
            return new SnoozeReminderCommandParser().parse(arguments);

        // ======================================== Calendar Commands =============================

        case ViewCalendarCommand.COMMAND_WORD:
            return new ViewCalendarCommand();

        case CloseCalendarCommand.COMMAND_WORD:
            return new CloseCalendarCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
