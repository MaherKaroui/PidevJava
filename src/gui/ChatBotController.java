/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import pidevuser.PidevUser;

/**
 *
 * @author Souid
 */
public class ChatBotController implements Initializable{
      @FXML
    private Label lblTitle;

    @FXML
    private TextField txtInput;

    @FXML
    private Button btnSend;

    @FXML
    private Label lblOutput;

     @Override
    public void initialize(URL url, ResourceBundle rb){
        // Set button action
        btnSend.setOnAction(event -> {
            String input = txtInput.getText();
            System.out.println("input"+input);
            String response = getResponse(input);
            System.out.println(response);
            lblOutput.setText(response);
            txtInput.clear();
        });
    }
    
    private static final String[] RESPONSES = {
            "Hello",
            "How are you?",
            "Nice to meet you!",
            "What can I help you with today?",
            "I'm sorry, I don't understand. Can you please rephrase your question?"
    };

   

    private String getResponse(String input) {
        // Loop through responses and return a random one

        for (String response : RESPONSES) {
            if (input.toLowerCase().contains("salut ?")) {
                return "salut comment je peux t aider aujourd'hui ?";
            }
            if (input.toLowerCase().contains("les types d'abonnements ") ||input.toLowerCase().contains("type")  ) {
                return "Nous proposons des abonnements mensuels, trimestriels et annuels. ";
            } if (input.toLowerCase().contains("cours en ligne ")) {
                return "Bien sûr ! Nous proposons une grande variété de cours en ligne, allant de la musculation au yoga en passant par la danse. Tous nos cours sont dispensés par des instructeurs certifiés et peuvent être suivis en direct ou en différé.";
            } if (input.toLowerCase().contains(" videos de cours ")) {
                return "Vous pouvez accéder aux vidéos de cours enregistrés en vous connectant à votre compte sur notre site web ou notre application mobile.";
            } if (input.toLowerCase().contains("equipement en ligne")) {
                return "Le type d'équipement nécessaire dépend du cours que vous suivez, mais la plupart de nos cours ne nécessitent qu'un tapis de yoga et des poids libres. Nous proposons également des options de substitution pour les personnes qui n'ont pas d'équipement.";
            }
             if (input.toLowerCase().contains("i have some question")) {
                return "i will do my best to help you";
            }
            
            if (input.toLowerCase().contains(input.toLowerCase())) {
                return response;
            }
            if (input.toLowerCase().contains(input.toLowerCase())) {
                return response;
            }
        }
        return RESPONSES[RESPONSES.length];
    }
    
     @FXML
    private void back()throws IOException {
        PidevUser m = new PidevUser() ;
         m.changeScene("/gui/LoggedInClient.fxml");
        
    }
}
