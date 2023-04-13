/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Models.Cours;

import Services.CoursServices;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
public class AfficherCoursController implements Initializable {

    @FXML
    private ListView<Cours> listeH;
    Preferences prefs = Preferences.userNodeForPackage(AfficherCoursController.class);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         CoursServices hs = new CoursServices();
    
    listeH.setCellFactory(param -> new CoursCell());
    CoursServices coursServices = new CoursServices();
    List<Cours> cours = coursServices.getAllCourss();
    ObservableList<Cours> items = FXCollections.observableArrayList(cours);
    listeH.setItems(items);
    // Ajouter les éléments de la liste à la ListView
    listeH.setItems(items);
    // 2. Créez une ArrayList de maps pour stocker les attributs de chaque hôtel
    listeH.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
    // Récupérer l'index de l'élément sélectionné
    int selectedIndex = newValue.intValue();   
    // Récupérer l'objet Hotel correspondant à cet index
    Cours selectedHotel = cours.get(selectedIndex);   
    // Récupérer l'ID de l'hôtel
    int hotelId = selectedHotel.getId();
     prefs.putInt("selectedHotelId", hotelId);
        System.out.println(hotelId);
   
});   


    }    

    void refreshList() {
    CoursServices coursServices = new CoursServices();
    List<Cours> planning = coursServices.getAllCourss();
    ObservableList<Cours> items = FXCollections.observableArrayList(planning);
    listeH.setItems(items);
     }
   @FXML
    private void modifierHotel(MouseEvent event) {
    Cours selectedHotel = listeH.getSelectionModel().getSelectedItem();
    Cours h1 = new Cours();
    CoursServices hs = new CoursServices();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/ModifierCours.fxml"));
            Parent root = loader.load();
            ModifierCoursController modifierCoursController = loader.getController();
            h1=selectedHotel;
            modifierCoursController.ModifyData(h1);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
              stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    refreshList();
        
    }
    
     
    
public static Cours fromString(String hotelString) {
    String[] parts = hotelString.split(" - ");
    String nom = parts[0];
    String Activite = parts[1];
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date_cours = parts[2];
    Date Datec = Date.valueOf(LocalDate.parse(date_cours, formatter));
    //Date date_cours = parts[2];
    String image = parts[3];
    Cours h = new Cours();
    CoursServices hs = new CoursServices();
    h=hs.filterByName(nom);
    
    
    return new Cours(nom, Activite, image, Datec);
}

    @FXML
    private void AjouterHotel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/AjouterCours.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);   
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AfficherCoursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        refreshList();
    }




}