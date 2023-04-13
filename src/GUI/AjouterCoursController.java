package GUI;

import Models.Cours;
import Services.CoursServices;
import static java.lang.System.exit;
import java.sql.Date;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AjouterCoursController {

    @FXML
    private TextArea activite;

    @FXML
    private Button ajth;

    @FXML
    private Button annuler;

    @FXML
    private DatePicker date_cours;

    @FXML
    private TextField img;

    @FXML
    private TextField nom_cours;

    @FXML
void AnnulerH(ActionEvent event) {
    // Close the current stage
    annuler.getScene().getWindow().hide();
}


    @FXML
void ajouterCours(ActionEvent event) {
    // Get the values from the JavaFX components
    String nom_cours_text = nom_cours.getText();
    String activite_text = activite.getText();
     java.sql.Date date = java.sql.Date.valueOf(date_cours.getValue());
    String img_text = img.getText();
    
    // Create a new Cours object with the entered values
    Cours cours = new Cours(nom_cours_text, activite_text, img_text,date);
    
    // Create an instance of CoursServices class and call the addCours method
    CoursServices coursServices = new CoursServices();
    coursServices.addCours(cours);
    
    // Display a success message
    System.out.println("Nouveau Cours ajouté avec succès!");
}

 }


