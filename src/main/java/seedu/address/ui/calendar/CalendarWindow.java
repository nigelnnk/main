package seedu.address.ui.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ConsultUtil;
import seedu.address.commons.util.TutorialUtil;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.reminder.Reminder;
import seedu.address.ui.UiPart;

/**
 * Controller for a calendar page.
 * Code below adapted from https://github.com/SirGoose3432/javafx-calendar/blob/master/src/FullCalendarView.java
 */
public class CalendarWindow extends UiPart<Stage> {

    public static final String FXML = "CalendarWindow.fxml";
    private static final int MAX_WEEK_OF_MONTH = 5;
    private static final int WEEKDAYS = 7;
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Scene calendarScene;

    @FXML
    private GridPane dateDisplayGrid;

    @FXML
    private Label calendarTitle;

    private List<CalendarDay> calendarDays;
    private YearMonth currentYearMonth;
    private List<Consult> consults;
    private List<Tutorial> tutorials;
    private List<Reminder> reminders;

    /**
     * Constructs a calendar window with the current month as reference.
     */
    public CalendarWindow(Stage root, ObservableList<Consult> consults, ObservableList<Tutorial> tutorials,
                          ObservableList<Reminder> reminders) {
        super(FXML, root);
        this.consults = consults;
        this.tutorials = tutorials;
        this.reminders = reminders;
        calendarDays = new ArrayList<>();
        currentYearMonth = YearMonth.now();
        createUi();
        fillDays();
        fillTitle();
    }

    /**
     * Creates a new CalendarWindow.
     */
    public CalendarWindow(ObservableList<Consult> consults, ObservableList<Tutorial> tutorials,
                          ObservableList<Reminder> reminders) {
        this(new Stage(), consults, tutorials, reminders);
    }

    /**
     * Creates the UI elements for the calendar.
     */
    private void createUi() {
        for (int i = 0; i < MAX_WEEK_OF_MONTH; i++) {
            for (int j = 0; j < WEEKDAYS; j++) {
                CalendarDay calendarDay = new CalendarDay();
                calendarDays.add(calendarDay);
                StackPane calendarDayStackPane = calendarDay.getCalendarDayStackPane();
                dateDisplayGrid.add(calendarDayStackPane, j, i);
            }
        }
        calendarScene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.LEFT) {
                prevMonth();
            } else if (e.getCode() == KeyCode.RIGHT) {
                nextMonth();
            }
        });
    }

    /**
     * Fill the calendar based on the created CalendarDays.
     */
    private void fillDays() {
        LocalDate calendarDate = firstDateOfCalendar();
        for (CalendarDay calendarDay : calendarDays) {
            StackPane calendarDayStackPane = calendarDay.getCalendarDayStackPane();
            calendarDayStackPane.getChildren().clear();
            Text dateText = new Text(String.format("%02d", calendarDate.getDayOfMonth()));
            StackPane.setAlignment(dateText, Pos.TOP_LEFT);
            calendarDayStackPane.getChildren().add(dateText);
            calendarDay.setDate(calendarDate);
            addAllConsults(calendarDay, calendarDate);
            addAllTutorials(calendarDay, calendarDate);
            addAllReminders(calendarDay, calendarDate);
            calendarDay.updateNumConsults();
            calendarDay.updateNumTutorials();
            calendarDay.updateNumReminders();
            calendarDate = calendarDate.plusDays(1);
        }
    }

    /**
     * Find the first date that will appear in the calendar window, i.e the date of the first Sunday that will appear.
     * @return The date of the first Sunday in the calendar window. This will often be a date of the previous month.
     */
    private LocalDate firstDateOfCalendar() {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(currentYearMonth.getYear(), currentYearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        return calendarDate;
    }

    /**
     * Fill the calendar's title with month and year.
     */
    private void fillTitle() {
        calendarTitle.setText(currentYearMonth.getMonth().toString() + " "
            + currentYearMonth.getYear());
        logger.info(currentYearMonth.getMonth().toString() + " " + currentYearMonth.getYear());
    }

    /**
     * Returns true if the Calendar window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the Calendar window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Shows the Calendar window.
     */
    public void show() {
        logger.fine("Showing Calendar Window.");
        fillDays();
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Closes the Calendar window.
     */
    public void close() {
        logger.fine("Closing Calendar Window.");
        getRoot().close();
    }

    /**
     * Focuses on the Calendar window.
     */
    public void focus() {
        fillDays();
        getRoot().requestFocus();
    }

    /**
     * Moves the calendar month forward by 1 and repopulates the calendar with the new CalendarDays.
     */
    public void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        fillDays();
        fillTitle();
    }

    /**
     * JavaFX function to call the next month.
     */
    @FXML
    public void nextMonthFx() {
        nextMonth();
    }

    /**
     * Moves the calendar month backwards by 1 and repopulates the calendar with the new CalendarDays.
     */
    public void prevMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        fillDays();
        fillTitle();
    }

    /**
     * JavaFX function to call the next month.
     */
    @FXML
    public void prevMonthFx() {
        prevMonth();
    }

    /**
     * Adds all the consultations on the {@Code calendarDate} to the {@Code calendarDay}.
     */
    public void addAllConsults(CalendarDay calendarDay, LocalDate calendarDate) {
        //Remove all consults to restart the count.
        calendarDay.removeConsults();
        for (Consult consult : consults) {
            if (ConsultUtil.checkSameDate(consult.getBeginDateTime(), calendarDate)) {
                calendarDay.addConsult(consult);
                logger.fine("Same date detected.");
            }
        }
    }

    /**
     * Adds all the tutorials on the {@Code calendarDate} to the {@Code calendarDay}.
     */
    public void addAllTutorials(CalendarDay calendarDay, LocalDate calendarDate) {
        //Remove all tutorials to restart the count.
        calendarDay.removeTutorials();
        for (Tutorial tutorial : tutorials) {
            if (TutorialUtil.checkDayOfWeek(tutorial.getDay(), calendarDate)
                && TutorialUtil.checkDuringSemester(calendarDate)) {
                calendarDay.addTutorial(tutorial);
                logger.fine("Same date detected.");
            }
        }
    }

    /**
     * Adds all the reminders on the {@Code calendarDate} to the {@Code calendarDay}.
     */
    public void addAllReminders(CalendarDay calendarDay, LocalDate calendarDate) {
        //Remove all reminders to restart the count.
        calendarDay.removeReminders();
        for (Reminder reminder : reminders) {
            if (ConsultUtil.checkSameDate(LocalDateTime.of(reminder.getDate(), reminder.getTime()), calendarDate)) {
                calendarDay.addReminder(reminder);
                logger.fine("Same date detected.");
            }
        }
    }
}
