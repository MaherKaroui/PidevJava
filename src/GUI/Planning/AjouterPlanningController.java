/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Planning;

import Models.Cours;
import Models.Planning;
import Services.CoursServices;
import Services.PlanningServices;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;



/**
 * FXML Controller class
 *
 * @author youssef
 */
public class AjouterPlanningController implements Initializable {

     @FXML
    private TextField cours_id;

    @FXML
    private DatePicker date_cours;
@FXML
    private Button annuler;
    
    CoursServices us = new CoursServices();
    PlanningServices ls =new PlanningServices();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjouterPlanning(ActionEvent event) {
    
   
     Cours u =new Cours();
     u=us.GetCoursById(Integer.parseInt(cours_id.getText()));
    if (u.getNom_cours()==null) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("cours n'existe pas!");
        alert.showAndWait();
        return;
    }
   
  
   // Obtenir la date système actuelle
    LocalDate localDate = LocalDate.now();
    // Convertir en java.sql.Date
    Date date_cours = Date.valueOf(localDate.toString());
    
     Planning planning = new Planning(date_cours,u);
   ls.addPlanning(planning);
    
    }

    @FXML
    private void AnnulerL(ActionEvent event) {
         // Close the current stage
    annuler.getScene().getWindow().hide();
    }
    
}