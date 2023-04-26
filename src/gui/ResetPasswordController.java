/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.util.Random;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import pidevuser.PidevUser;

/**
 * FXML Controller class
 *
 * @author azizk
 */
public class ResetPasswordController implements Initializable {

   @FXML
    private TextField emailField;
   static String resetCode;

   @FXML
private void resetPassword() throws IOException {
    // Generate a random code for the password reset
    String resetCode = generateResetCode();
    Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

    try {
        // Send an email to the user with the reset code
        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator("ayedi.malek@esprit.tn", "Anas1995"));
        email.setStartTLSEnabled(true);
        email.setFrom("ayedi.malek@esprit.tn");
        email.setSubject("Sprotify : Password Reset Request");
        email.setMsg("Your password reset code is: " + resetCode 
                + "you can use the code in the reset code in the change password interface to reset your password");
        email.addTo(emailField.getText());
        email.send();

        // Display a confirmation message to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Password reset requested");
        alert.setContentText("An email has been sent to " + emailField.getText() + " with instructions for resetting your password.");
        alert.showAndWait();
    } catch (EmailException e) {
        // Handle email sending errors
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error sending email");
        alert.setContentText("An error occurred while sending the password reset email. Please try again later.");
        alert.showAndWait();
    }
    
    PidevUser m = new PidevUser() ;
         m.changeScene("/gui/password_reset.fxml");
}


    private String generateResetCode() {
        // Generate a random 6-digit code for the password reset
       Random random = new Random();
    int code = random.nextInt(900000) + 100000;
    return resetCode = String.valueOf(code);
    }
    
 
    
  
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
     @FXML
    private void Login()throws IOException {
        PidevUser m = new PidevUser() ;
         m.changeScene("/gui/login.fxml");
        
    }
}

    



