package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_BEGIN_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CSV_FILEPATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_LINK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_MINUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_BEGIN_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEKDAY;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import seedu.address.logic.commands.CloseCalendarCommand;
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
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.consults.ClearConsultCommand;

/**
 * Driver class for matching user's input against a list of possible suggestions.
 */
public class InputMatcher {

    private static final Set<String> defaultKeyWords = new HashSet<>(List.of(
            AddStudentCommand.COMMAND_WORD, EditStudentCommand.COMMAND_WORD,
            DeleteStudentCommand.COMMAND_WORD, ClearStudentCommand.COMMAND_WORD,
            FindStudentCommand.COMMAND_WORD, FindStudentMatricNumberCommand.COMMAND_WORD,
            ListStudentCommand.COMMAND_WORD, AddConsultCommand.COMMAND_WORD,
            ListConsultCommand.COMMAND_WORD, DeleteConsultCommand.COMMAND_WORD,
            EditConsultCommand.COMMAND_WORD, ClearConsultCommand.COMMAND_WORD,
            AddTutorialCommand.COMMAND_WORD, AddTutorialStudentCommand.COMMAND_WORD,
            DeleteTutorialStudentCommand.COMMAND_WORD, MarkPresentCommand.COMMAND_WORD,
            MarkAbsentCommand.COMMAND_WORD, DeleteTutorialCommand.COMMAND_WORD,
            ListTutorialCommand.COMMAND_WORD, ListAttendanceCommand.COMMAND_WORD,
            CopyTutorialEmailsCommand.COMMAND_WORD, ExportTutorialAttendanceCommand.COMMAND_WORD,
            AddModCommand.COMMAND_WORD, DeleteModCommand.COMMAND_WORD,
            ListModCommand.COMMAND_WORD, NoteModCommand.COMMAND_WORD,
            AddModLinkCommand.COMMAND_WORD, ClearModLinksCommand.COMMAND_WORD,
            ViewModInfoCommand.COMMAND_WORD, CopyModLinkCommand.COMMAND_WORD,
            AddReminderCommand.COMMAND_WORD, DeleteReminderCommand.COMMAND_WORD,
            DoneReminderCommand.COMMAND_WORD, EditReminderCommand.COMMAND_WORD,
            FindReminderCommand.COMMAND_WORD, ListReminderCommand.COMMAND_WORD,
            SnoozeReminderCommand.COMMAND_WORD, ViewCalendarCommand.COMMAND_WORD,
            CloseCalendarCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD, SortStudentCommand.COMMAND_WORD));

    @FXML
    private TextField commandTextField;

    private SuggestionProvider<String> suggestionProvider;
    private AutoCompletionBinding<String> autoCompletionBinding;

    public InputMatcher(TextField commandTextField) {
        this.commandTextField = commandTextField;
        suggestionProvider = SuggestionProvider.create(defaultKeyWords);
        autoCompletionBinding =
                TextFields.bindAutoCompletion(commandTextField, suggestionProvider);
        autoCompletionBinding.setPrefWidth(960);
        autoCompletionBinding.setDelay(0);
        autoCompletionBinding.setVisibleRowCount(4);
    }

    /**
     * Matches user input against possible list of keywords and update user's list of suggestions.
     * If user's input is less than 5 character (excluding spaces) default commands are suggested.
     */
    public void match(String userInput) {
        if (userInput.trim().length() <= 4) {
            suggestionProvider.clearSuggestions();
            suggestionProvider.addPossibleSuggestions(defaultKeyWords);
        } else if (Character.isWhitespace(userInput.charAt(userInput.length() - 1))) {
            String[] userInputs = userInput.split(" ");
            switch (userInputs[0]) {

            case AddStudentCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_NAME, PREFIX_MATRIC_NUMBER, PREFIX_EMAIL, PREFIX_TAG);
                break;

            case EditStudentCommand.COMMAND_WORD:
                if (checkIndexProvided(userInputs)) {
                    setSuggestion(userInput, PREFIX_NAME, PREFIX_MATRIC_NUMBER, PREFIX_EMAIL, PREFIX_TAG);
                }
                break;

            case FindStudentMatricNumberCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_MATRIC_NUMBER);
                break;

            case AddConsultCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_STUDENT, PREFIX_CONSULT_BEGIN_DATE_TIME,
                        PREFIX_CONSULT_END_DATE_TIME, PREFIX_PLACE);
                break;

            case EditConsultCommand.COMMAND_WORD:
                if (checkIndexProvided(userInputs)) {
                    setSuggestion(userInput, PREFIX_CONSULT_BEGIN_DATE_TIME, PREFIX_CONSULT_END_DATE_TIME,
                            PREFIX_PLACE);
                }
                break;

            case AddTutorialCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_MODULE_CODE, PREFIX_TUTORIAL_NAME,
                        PREFIX_TUTORIAL_WEEKDAY, PREFIX_TUTORIAL_BEGIN_TIME, PREFIX_TUTORIAL_END_TIME,
                        PREFIX_PLACE);
                break;

            case AddTutorialStudentCommand.COMMAND_WORD:
            case DeleteTutorialStudentCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_TUTORIAL_INDEX, PREFIX_STUDENT);
                break;

            case MarkPresentCommand.COMMAND_WORD:
            case MarkAbsentCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_TUTORIAL_INDEX, PREFIX_TUTORIAL_WEEK, PREFIX_STUDENT);
                break;

            case ListAttendanceCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_TUTORIAL_INDEX, PREFIX_TUTORIAL_WEEK);
                break;

            case CopyTutorialEmailsCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_TUTORIAL_INDEX);
                break;

            case ExportTutorialAttendanceCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_TUTORIAL_INDEX, PREFIX_CSV_FILEPATH);
                break;

            case AddModCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_MODULE_CODE, PREFIX_MODULE_NAME);
                break;

            case DeleteModCommand.COMMAND_WORD:
            case ClearModLinksCommand.COMMAND_WORD:
            case ViewModInfoCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_MODULE_CODE);
                break;

            case NoteModCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_MODULE_CODE, PREFIX_MODULE_NOTE);
                break;

            case AddModLinkCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_MODULE_CODE, PREFIX_MODULE_LINK, PREFIX_MODULE_LINK_NAME);
                break;

            case CopyModLinkCommand.COMMAND_WORD:
                if (checkIndexProvided(userInputs)) {
                    setSuggestion(userInput, PREFIX_MODULE_CODE);
                }
                break;

            case AddReminderCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_REMINDER_DESCRIPTION, PREFIX_REMINDER_DATE, PREFIX_REMINDER_TIME);
                break;

            case EditReminderCommand.COMMAND_WORD:
                if (checkIndexProvided(userInputs)) {
                    setSuggestion(userInput, PREFIX_REMINDER_DESCRIPTION, PREFIX_REMINDER_DATE, PREFIX_REMINDER_TIME);
                }
                break;

            case SnoozeReminderCommand.COMMAND_WORD:
                if (checkIndexProvided(userInputs)) {
                    setSuggestion(userInput, PREFIX_REMINDER_DAY, PREFIX_REMINDER_HOUR, PREFIX_REMINDER_MINUTE);
                }
                break;

            case FindReminderCommand.COMMAND_WORD:
                setSuggestion(userInput, PREFIX_REMINDER_DESCRIPTION, PREFIX_REMINDER_DATE);
                break;

            default: break;
            }
        }
    }

    /**
     * Checks if the second string in user's input is a number.
     */
    private boolean checkIndexProvided(String[] userInputs) {
        if (userInputs.length > 1) {
            return Character.isDigit(userInputs[1].charAt(0));
        }
        return false;
    }

    private void setSuggestion(String userInput, Prefix ... prefixes) {
        ArgumentMultimap multimap = ArgumentTokenizer.tokenize(userInput, prefixes);
        for (Prefix p : prefixes) {
            if (multimap.getValue(p).isEmpty()) {
                suggestionProvider.clearSuggestions();
                suggestionProvider.addPossibleSuggestions(userInput + p);
                autoCompletionBinding.setUserInput(userInput);
                break;
            }
        }
    }
}
