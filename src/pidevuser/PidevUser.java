/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevuser;

import entites.User;
import util.dbconnection;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Souid
 */
public class PidevUser extends Application {
    
    private static Stage stg ;
    public static User user=null ;
     @Override
    public void start(Stage stage) throws Exception{
        stg = stage;
       // primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/gui/login.fxml"));
        stage.setTitle("Sportify");
        stage.setScene(new Scene(root, 600*2, 400*2));
        stage.show();
    }
 public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);        
    }
    
}
