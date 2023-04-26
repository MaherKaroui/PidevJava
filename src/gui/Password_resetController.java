/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entites.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import pidevuser.PidevUser;
import util.dbconnection;

/**
 * FXML Controller class
 *
 * @author azizk
 */
public class Password_resetController implements Initializable {

     private String resetCode = ResetPasswordController.resetCode;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private TextField emailField;

    @FXML
    private TextField resetCodeField;

    @FXML
    private PasswordField passwordField;
 
    
    @FXML
void resetPassword123(ActionEvent event) {
    String resetCodeEntered = resetCodeField.getText();
    String email = emailField.getText();
    String newPassword = passwordField.getText();
    
  
    User user = new User(passwordField.getText());
     
    String pw_hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
      
    if (resetCodeEntered.equals(resetCode)) {
        try (Connection cnx = dbconnection.getInstance().getConnection();
             PreparedStatement stmt = cnx.prepareStatement("UPDATE user SET password = ? WHERE email = ?")) {
            stmt.setString(1, pw_hash);
            stmt.setString(2, email);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Password reset successful");
                alert.setContentText("Your password has been reset. You may now log in with your new password.");
                alert.showAndWait();
                resetCode = null;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Password reset failed");
                alert.setContentText("An error occurred while resetting your password. Please try again later.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Database error");
            alert.setContentText("An error occurred while accessing the database. Please try again later.");
            alert.showAndWait();
            e.printStackTrace();
        }
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid reset code");
        alert.setContentText("The reset code you entered is invalid. Please try again.");
        alert.showAndWait();
    }
}

  @FXML
    private void Login()throws IOException {
        PidevUser m = new PidevUser() ;
         m.changeScene("/gui/login.fxml");
        
    }



}
