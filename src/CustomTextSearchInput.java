import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.textfield.CustomTextField;

import java.util.ArrayList;


public class CustomTextSearchInput extends CustomTextField {
    public static EventType<CustomTextSearchInputEvent> TEXT_SEARCH_TYPE_CHANGED = new EventType<>("TEXT_SEARCH_TYPE_CHANGED");
    public static EventType<CustomTextSearchInputEvent> FOCUS_REMOVED = new EventType<>("FOCUS_REMOVED");
    Scene scene;
    private final CheckBox caseSensitiveCheckbox;

    private final CheckBox isWordCheckbox;

    private final CheckBox regexCheckbox;

    public CustomTextSearchInput(Scene scene) {
        this(scene, "");
    }

    public CustomTextSearchInput(Scene scene, String value) {
        super();
        this.scene = scene;
        this.setText(value);
        setId("CustomTextSearchInput");

        caseSensitiveCheckbox = new CheckBox();
        caseSensitiveCheckbox.setFocusTraversable(false);
        caseSensitiveCheckbox.setId("check-box-caseSensitiveCheckbox");
        caseSensitiveCheckbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                requestFocus();
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
                requestFocus();
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
                requestFocus();
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
        Event event = new CustomTextSearchInputEvent(TEXT_SEARCH_TYPE_CHANGED);
        this.fireEvent(event);
    }

    public void emitFocusRemoved() {
        Event event = new CustomTextSearchInputEvent(FOCUS_REMOVED);
        this.fireEvent(event);
    }

    public boolean hasFocus() {
        if (this.isFocused())
            return true;
        for (Node n : getChildren()) {
            if (n.isFocused())
                return true;
        }
        return false;
    }

    private boolean isPaneChildrenFocused(EventTarget target, Pane pane) {
        for (Node n : pane.getChildren()) {
            if (n instanceof Pane) {
                isPaneChildrenFocused(target, (Pane) n);
            }
            if (n.equals(target)) {
                return true;
            }
        }
        return false;
    }

    private boolean targetIsFocusOwner(EventTarget target) {
        for (Node n : getAllNodes(this))
            if ((n.isFocused() || isFocused()) && n.equals(target))
                return true;
        return false;
    }

    public static ArrayList<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        addAllDescendents(root, nodes);
        return nodes;
    }

    private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            nodes.add(node);
            if (node instanceof Parent)
                addAllDescendents((Parent)node, nodes);
        }
    }

    private void addLooseFocusHandler() {
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

            if (!targetIsFocusOwner(event.getTarget())) {
                emitFocusRemoved();
                getParent().requestFocus();
            }
        });
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
