/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Planning;

import GUI.Planning.PlanningCell;
import Models.Cours;
import Models.Planning;


import Services.PlanningServices;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author youssef
 */
public class AfficherPlanningController implements Initializable {

    @FXML
    private ListView<Planning> listeH;
    Preferences prefs = Preferences.userNodeForPackage(AfficherPlanningController.class);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         PlanningServices hs = new PlanningServices();
    
    listeH.setCellFactory(param -> new PlanningCell());
    PlanningServices coursServices = new PlanningServices();
    List<Planning> planning = coursServices.getAllPlannings();
    ObservableList<Planning> items = FXCollections.observableArrayList(planning);
    listeH.setItems(items);
    // Ajouter les éléments de la liste à la ListView
    listeH.setItems(items);
    // 2. Créez une ArrayList de maps pour stocker les attributs de chaque hôtel
    listeH.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
    // Récupérer l'index de l'élément sélectionné
    int selectedIndex = newValue.intValue();   
    // Récupérer l'objet Hotel correspondant à cet index
    Planning selectedHotel = planning.get(selectedIndex);   
    // Récupérer l'ID de l'hôtel
    int hotelId = selectedHotel.getId();
     prefs.putInt("selectedHotelId", hotelId);
        System.out.println(hotelId);
   
});   


    }    

    
    void refreshList() {
    PlanningServices coursServices = new PlanningServices();
    List<Planning> planning = coursServices.getAllPlannings();
    ObservableList<Planning> items = FXCollections.observableArrayList(planning);
    listeH.setItems(items);
}

   @FXML
    private void modifierHotel(MouseEvent event) {
    Planning selectedHotel = listeH.getSelectionModel().getSelectedItem();
    Planning h1 = new Planning();
    PlanningServices hs = new PlanningServices();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierPlanning.fxml"));
            Parent root = loader.load();
            ModifierPlanningController modifierPlanningController = loader.getController();
            h1=selectedHotel;
            modifierPlanningController.ModifyData(h1);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
              stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
         refreshList();
    }
/*public static Planning fromString(String hotelString) {
    String[] parts = hotelString.split(" - ");
    String nom = parts[0];
    String Activite = parts[1];
    //Date date_cours = parts[2];
    String image = parts[3];
    
    return new Planning(nom, Activite, image, date_cours);
}*/

    @FXML
    private void AjouterHotel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterPlanning.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);   
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AfficherPlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
         refreshList();
        
    }




}