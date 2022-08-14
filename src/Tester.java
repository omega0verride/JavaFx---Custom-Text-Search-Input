import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
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
        input.addEventHandler(CustomTextSearchInput.SELECTION_CHANGED, new EventHandler<CustomTextSearchInputEvent>() {
                    @Override
                    public void handle(CustomTextSearchInputEvent customSelectableTileOptionsPaneEvent) {
                        System.out.println("casesensitive: "+input.isCaseSensitive()+", wholeWord: "+input.isWholeWord()+", regex: "+input.isRegex());
                    }
                }
        );
        input.setMaxWidth(250);

        root.getChildren().add(input);

        jMetro.setScene(scene);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
        stage.show();
    }
}