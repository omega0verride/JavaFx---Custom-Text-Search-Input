import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class Tester extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        StackPane root = new StackPane();
        final Scene scene = new Scene(root, 400, 400);

        JMetro jMetro = new JMetro(Style.DARK);
        root.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        CustomTextSearchInput input = new CustomTextSearchInput(scene);
        input.addEventHandler(CustomTextSearchInput.TEXT_SEARCH_TYPE_CHANGED, new EventHandler<CustomTextSearchInputEvent>() {
                    @Override
                    public void handle(CustomTextSearchInputEvent customSelectableTileOptionsPaneEvent) {
                        System.out.println("casesensitive: "+input.isCaseSensitive()+", wholeWord: "+input.isWholeWord()+", regex: "+input.isRegex());
                    }
                }
        );

        input.addEventHandler(CustomTextSearchInput.FOCUS_REMOVED, new EventHandler<CustomTextSearchInputEvent>() {
                    @Override
                    public void handle(CustomTextSearchInputEvent customSelectableTileOptionsPaneEvent) {
                        System.out.println("user clicked outside the input field");
                    }
                }
        );

        input.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                System.out.println("text: "+input.getText());
                System.out.println("casesensitive: "+input.isCaseSensitive()+", wholeWord: "+input.isWholeWord()+", regex: "+input.isRegex());
                input.clear();
            }
        });

        input.setMaxWidth(250);

        root.getChildren().add(input);

        jMetro.setScene(scene);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
        stage.show();
    }
}