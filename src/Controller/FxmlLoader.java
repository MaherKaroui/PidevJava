package Controller;


import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxmlLoader {

    private Pane workPlace;

    public Pane getPage(String fileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/" + fileName + ".fxml"));
            workPlace = loader.load();
        } catch (IOException e) {
            System.out.println("no page " + fileName+"\n"+e);
        }
        return workPlace;
    }

}

