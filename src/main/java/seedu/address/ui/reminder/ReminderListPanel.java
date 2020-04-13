package seedu.address.ui.reminder;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reminder.Reminder;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of reminders.
 */
public class ReminderListPanel extends UiPart<Region> {

    private static final String FXML = "ReminderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);

    @FXML
    private ListView<Reminder> reminderListView;

    public ReminderListPanel(ObservableList<Reminder> reminderList) {
        super(FXML);
        reminderListView.setItems(reminderList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());

        // Prevent ReminderListPanel from being selected with a mouse click
        reminderListView.addEventFilter(MouseEvent.MOUSE_PRESSED, Event::consume);
        reminderListView.setFocusTraversable(false);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reminder} using a {@code ReminderCard}.
     */
    class ReminderListViewCell extends ListCell<Reminder> {
        @Override
        protected void updateItem(Reminder reminder, boolean empty) {
            super.updateItem(reminder, empty);

            if (empty || reminder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderCard(reminder, getIndex() + 1).getRoot());
            }
        }
    }
}
