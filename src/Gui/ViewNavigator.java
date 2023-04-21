package Gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewNavigator {
    public static final String MAIN_VIEW = "Main.fxml";
    public static final String POST_VIEW = "Post.fxml";

    private static Stage mainStage;

    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    public static void loadView(String viewName) {
        try {
            Parent root = FXMLLoader.load(ViewNavigator.class.getResource(viewName));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
