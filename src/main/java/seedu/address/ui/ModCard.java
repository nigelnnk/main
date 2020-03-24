package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.mod.Mod;

/**
 * An UI component that displays information of a {@code Mod}.
 */
public class ModCard extends UiPart<Region> {

    private static final String FXML = "ModListCard.fxml";

    public final Mod mod;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label modName;
    @FXML
    private Label modCode;
    //@FXML
    //private FlowPane tags;


    public ModCard(Mod mod, int displayedIndex) {
        super(FXML);
        this.mod = mod;
        id.setText(displayedIndex + ". ");
        modCode.setText(mod.getModCode().toString());
        modName.setText(mod.getModName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModCard)) {
            return false;
        }

        // state check
        ModCard card = (ModCard) other;
        return id.getText().equals(card.id.getText())
                && mod.equals(card.mod);
    }
}

