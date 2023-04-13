/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Planning.AfficherPlanningController;
import Models.Cours;
import Services.CoursServices;
import Services.PlanningServices;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ModifierCoursController implements Initializable {

    @FXML
    private TextField nom_cours;
    @FXML
    private TextField activite;
    @FXML
    private TextField image;
    @FXML
    private DatePicker date_cours;

    /**
     * Initializes the controller class.
     */
    private Cours cours ;
         Preferences userP = Preferences.userNodeForPackage(ModifierCoursController.class);
                String Id = userP.get("selectedHotelId", "..") ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

            
    }    

    @FXML
    private void ModifierH(ActionEvent event) {
       CoursServices hs = new CoursServices();
       LocalDate date;
       date = date_cours.getValue();
       Date date_cours = Date.valueOf(date);
       
       Cours h1 = new Cours(Integer.parseInt(Id) ,nom_cours.getText(), activite.getText(), image .getText() , date_cours);
      
       hs.updateCours(h1); 
        
        
    }
    void ModifyData(Cours cours) {
        this.cours = cours;
        nom_cours.setText(cours.getNom_cours());
        activite.setText(cours.getActivite());
        date_cours.setValue(cours.getDate_cours().toLocalDate());
        image.setText(cours.getImage());
    }

    @FXML
private void SupprimerH(ActionEvent event) {
    PlanningServices hs = new PlanningServices();
    int id = Integer.parseInt(Id);
    hs.deletePlanning(id);

    

    // Open the Planning page
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/AfficherCours.fxml"));
        Parent root = loader.load();
        AfficherCoursController planningController = loader.getController();
        planningController.refreshList();
        Stage planningStage = new Stage();
        planningStage.setScene(new Scene(root));
        planningStage.setTitle("Planning");
        planningStage.show();
    } catch (IOException ex) {
        ex.printStackTrace();
    }

    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
    confirmation.setContentText("Planning est supprimé avec succès");
    confirmation.show();
}

    
}
 /*
*/