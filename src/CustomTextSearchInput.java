import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.textfield.CustomTextField;


public class CustomTextSearchInput extends CustomTextField {
    public static EventType<CustomTextSearchInputEvent> SELECTION_CHANGED = new EventType<>("SELECTION_CHANGED");
    Scene scene;
    private final CheckBox caseSensitiveCheckbox;

    private final CheckBox isWordCheckbox;

    private final CheckBox regexCheckbox;

    public CustomTextSearchInput(Scene scene) {
        super();
        this.scene = scene;
        setId("textField");

        caseSensitiveCheckbox = new CheckBox();
        caseSensitiveCheckbox.setFocusTraversable(false);
        caseSensitiveCheckbox.setId("check-box-caseSensitiveCheckbox");
        caseSensitiveCheckbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                emitChange();
            }
        });

        isWordCheckbox = new CheckBox(); // disable when using regex
        isWordCheckbox.setFocusTraversable(false);
        isWordCheckbox.setId("check-box-isWordCheckbox");
        isWordCheckbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                regexCheckbox.setSelected(false);
                emitChange();
            }
        });

        regexCheckbox = new CheckBox();
        regexCheckbox.setFocusTraversable(false);
        regexCheckbox.setId("check-box-regexCheckbox");
        regexCheckbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isWordCheckbox.setSelected(false);
                emitChange();
            }
        });

        HBox controls = new HBox();
        controls.getChildren().addAll(caseSensitiveCheckbox, isWordCheckbox, regexCheckbox);
        controls.setAlignment(Pos.CENTER);
        setRight(controls);


        getStylesheets().add(String.valueOf(CustomTextSearchInput.class.getResource("resources/customTextSearchInput.css")));
        addLooseFocusHandler();
    }

    public void emitChange() {
        Event event = new CustomTextSearchInputEvent(SELECTION_CHANGED);
        this.fireEvent(event);
    }

    private void addLooseFocusHandler() {
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (this.isFocused())
                if (!targetIsFocusOwner(event.getTarget())) {
                    getParent().requestFocus();
                }
        });
    }

    private boolean targetIsFocusOwner(EventTarget target) {
        for (Node n : getChildrenUnmodifiable()) {
            if (n.equals(target))
                return true;
            if (n instanceof Pane) {
                for (Node n1 : ((Pane) n).getChildren())
                    if (n1.equals(target))
                        return true;
            }
        }
        return false;
    }

    public boolean isCaseSensitive() {
        return this.caseSensitiveCheckbox.isSelected();
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitiveCheckbox.setSelected(caseSensitive);
        emitChange();
    }

    public boolean isWholeWord() {
        return this.isWordCheckbox.isSelected();
    }

    public void setWholeWord(boolean wholeWord) {
        this.isWordCheckbox.setSelected(wholeWord);
        emitChange();
    }

    public boolean isRegex() {
        return this.regexCheckbox.isSelected();
    }

    public void setRegex(boolean regex) {
        this.regexCheckbox.setSelected(regex);
        emitChange();
    }
}
