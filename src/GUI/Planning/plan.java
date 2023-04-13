/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Planning;

import Models.Cours;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class plan extends Application {
    
   public void start(Stage primaryStage) {
       
        try {
       
           
       
       
         Parent root;
            root = FXMLLoader.load(getClass().getResource("AfficherPlanning.fxml"));

        Scene scene = new Scene(root);
       
        primaryStage.setTitle("Planning");
        primaryStage.setScene(scene); 
        primaryStage.show();
       
                } catch (IOException ex) {
            Logger.getLogger(Cours.class.getName()).log(Level.SEVERE, null, ex);
           
        }
    }
   
     public static void main(String[] args) {
        launch(args);
    
     }
    
}
