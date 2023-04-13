/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entites.User;
import Services.ServiceUser;
import java.io.IOException;
import pidevuser.PidevUser;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 * FXML Controller class
 *
 * @author Souid
 */
public class LoggedInClientController implements Initializable {

    PidevUser m ;
    @FXML
    private Label label_welcome;
    @FXML
    private Button button_logout;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> num_telephone;
    @FXML
    private TableColumn<User, String> type;
    @FXML
    private TableColumn<User, Integer> score;
    @FXML
    private TableColumn<User, Integer> nombre_etoile;
    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<User, String> prenom;
   
    @FXML
    private Label lnom;
    @FXML
    private Label lprenom;
    @FXML
    private Label lemail;
    @FXML
    private Label lnumt;
    @FXML
    private Label letoile;
    @FXML
    private Label lscore;
    @FXML
    private Label ltype;
    @FXML
    private TextField tsearch;

    
    
    /**
     * Initializes the controller class.
     */
      public void initialize(URL location,ResourceBundle resources)
      {
          
          
      }
      
    
    
      @FXML
    private void logout() throws IOException {  
        PidevUser m = new PidevUser() ;
       
        m.changeScene("/gui/login.fxml");
        
    }
    @FXML
    private void toupdatescene()throws IOException {
        PidevUser m = new PidevUser() ;
         m.changeScene("/gui/editprofile.fxml");
        
    }
   
    @FXML
    private void tobot()throws IOException {
        PidevUser m = new PidevUser() ;
         m.changeScene("/gui/ChatBot.fxml");
        
    }
  
}
