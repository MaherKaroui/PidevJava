/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
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
    // Remove leading/trailing whitespace and convert to lowercase
    input = input.trim().toLowerCase();
    
    // Check for specific keywords and return corresponding response
    if (input.contains("bonjour") || input.contains("salut")) {
        return "Bonjour ! Comment puis-je vous aider aujourd'hui ?";
    } else if (input.contains("abonnement")) {
        return "Nous proposons des abonnements mensuels, trimestriels et annuels. Veuillez consulter notre site web ou notre application mobile pour plus d'informations.";
    } else if (input.contains("cours en ligne")) {
        return "Nous avons une grande variété de cours en ligne, allant de la musculation au yoga en passant par la danse. Tous nos cours sont dispensés par des instructeurs certifiés et peuvent être suivis en direct ou en différé.";
    } else if (input.contains("vidéos de cours")) {
        return "Vous pouvez accéder aux vidéos de cours enregistrés en vous connectant à votre compte sur notre site web ou notre application mobile.";
    } else if (input.contains("équipement")) {
        return "Le type d'équipement nécessaire dépend du cours que vous suivez, mais la plupart de nos cours ne nécessitent qu'un tapis de yoga et des poids libres. Nous proposons également des options de substitution pour les personnes qui n'ont pas d'équipement.";
    } else {
        // If no specific keyword matches, return a random response
        Random rand = new Random();
        return RESPONSES[rand.nextInt(RESPONSES.length)];
    }
}
    
     @FXML
    private void back()throws IOException {
        PidevUser m = new PidevUser() ;
         m.changeScene("/gui/LoggedInClient.fxml");
        
    }
}
