package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.consults.ListConsultCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.mods.ListModCommand;
import seedu.address.logic.commands.mods.ViewModInfoCommand;
import seedu.address.logic.commands.students.FindStudentCommand;
import seedu.address.logic.commands.students.FindStudentMatricNumberCommand;
import seedu.address.logic.commands.students.ListStudentCommand;
import seedu.address.logic.commands.tutorials.ListTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.ui.calendar.CalendarWindow;
import seedu.address.ui.consult.ConsultListPanel;
import seedu.address.ui.mod.ModInfoPanel;
import seedu.address.ui.mod.ModListPanel;
import seedu.address.ui.reminder.ReminderListPanel;
import seedu.address.ui.student.StudentListPanel;
import seedu.address.ui.tutorial.AttendanceListPanel;
import seedu.address.ui.tutorial.TutorialListPanel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private StudentListPanel studentListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private CalendarWindow calendarWindow;
    private TutorialListPanel tutorialListPanel;
    private AttendanceListPanel attendanceListPanel;
    private ConsultListPanel consultListPanel;
    private ModListPanel modListPanel;
    private ModInfoPanel modInfoPanel;
    private ReminderListPanel reminderListPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane studentListPanelPlaceholder;

    @FXML
    private StackPane tutorialListPanelPlaceholder;

    @FXML
    private StackPane attendanceListPanelPlaceholder;

    @FXML
    private StackPane consultListPanelPlaceholder;

    @FXML
    private StackPane modListPanelPlaceholder;

    @FXML
    private StackPane modInfoPanelPlaceholder;

    @FXML
    private StackPane reminderListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private SplitPane splitPanePlaceholder;

    @FXML
    private TabPane firstTabPanePlaceholder;

    @FXML
    private TabPane secondTabPanePlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        calendarWindow = new CalendarWindow(logic.getFilteredConsultList(),
                logic.getFilteredTutorialList(), logic.getUnFilteredReminderList());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        studentListPanel = new StudentListPanel(logic.getFilteredStudentList(), logic.getFilteredTutorialList());
        studentListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());

        tutorialListPanel = new TutorialListPanel(logic.getFilteredTutorialList());
        tutorialListPanelPlaceholder.getChildren().add(tutorialListPanel.getRoot());

        attendanceListPanel = new AttendanceListPanel();
        attendanceListPanelPlaceholder.getChildren().add(attendanceListPanel.getRoot());

        consultListPanel = new ConsultListPanel(logic.getFilteredConsultList());
        consultListPanelPlaceholder.getChildren().add(consultListPanel.getRoot());

        modListPanel = new ModListPanel(logic.getFilteredModList());
        modListPanelPlaceholder.getChildren().add(modListPanel.getRoot());

        modInfoPanel = new ModInfoPanel(logic.getViewedMod());
        modInfoPanelPlaceholder.getChildren().add(modInfoPanel.getRoot());

        reminderListPanel = new ReminderListPanel(logic.getFilteredReminderList());
        reminderListPanelPlaceholder.getChildren().add(reminderListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void setSplitPane() {
        splitPanePlaceholder.lookupAll(".split-pane-divider")
                .forEach(div -> div.setMouseTransparent(true));
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setMinHeight(guiSettings.getWindowHeight());
        primaryStage.setMinWidth(guiSettings.getWindowWidth());
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
        primaryStage.setResizable(false);
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Selects the corresponding command's tab based on {@code commandText}
     */
    @FXML
    public void handleList(String commandText) {
        switch(commandText.split(" ", 2)[0]) {

        case ListStudentCommand.COMMAND_WORD:
        case FindStudentCommand.COMMAND_WORD:
        case FindStudentMatricNumberCommand.COMMAND_WORD:
            firstTabPanePlaceholder.getSelectionModel().select(0);
            break;

        case ListTutorialCommand.COMMAND_WORD:
            firstTabPanePlaceholder.getSelectionModel().select(1);
            break;

        case ListModCommand.COMMAND_WORD:
            firstTabPanePlaceholder.getSelectionModel().select(2);
            break;

        case ListConsultCommand.COMMAND_WORD:
            secondTabPanePlaceholder.getSelectionModel().select(0);
            break;

        case ViewModInfoCommand.COMMAND_WORD:
            secondTabPanePlaceholder.getSelectionModel().select(2);
            break;

        default:
            break;
        }
    }

    /**
     * Opens the Calendar window or focuses on it if it's already opened.
     */
    @FXML
    public void handleCalendar() {
        if (!calendarWindow.isShowing()) {
            calendarWindow.show();
        } else {
            calendarWindow.focus();
        }
    }

    /**
     * Closes the Calendar window if it's already opened.
     */
    @FXML
    public void closeCalendar() throws CommandException {
        if (calendarWindow.isShowing()) {
            calendarWindow.close();
        } else {
            throw new CommandException(Messages.NO_CALENDAR_OPEN_MESSAGE);
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Loads AttendanceListPanel in the GUI with the appropriate data.
     */
    @FXML
    private void handleAttendance(Tutorial tutorialToShow, int weekZeroBased) {
        attendanceListPanel = new AttendanceListPanel(tutorialToShow, weekZeroBased);
        attendanceListPanelPlaceholder.getChildren().clear();
        attendanceListPanelPlaceholder.getChildren().add(attendanceListPanel.getRoot());
        secondTabPanePlaceholder.getSelectionModel().select(1);
    }

    /**
     * Loads AttendanceListPanel in the GUI with the default label.
     * Updates Student panel with new tutorial tags.
     */
    @FXML
    private void handleRefreshTutorial() {
        attendanceListPanel = new AttendanceListPanel();
        attendanceListPanelPlaceholder.getChildren().clear();
        attendanceListPanelPlaceholder.getChildren().add(attendanceListPanel.getRoot());
        studentListPanel = new StudentListPanel(logic.getFilteredStudentList(), logic.getFilteredTutorialList());
        studentListPanelPlaceholder.getChildren().clear();
        studentListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());
    }


    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public StudentListPanel getStudentListPanel() {
        return studentListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowCalendar()) {
                handleCalendar();
            }

            if (commandResult.isCloseCalendar()) {
                closeCalendar();
            }
            if (commandResult.isShowList()) {
                handleList(commandText);
            }

            if (commandResult.isShowAttendance()) {
                handleAttendance(commandResult.getTutorialToShow(), commandResult.getWeekZeroBased());
            }

            if (commandResult.isRefreshTutorial()) {
                handleRefreshTutorial();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
