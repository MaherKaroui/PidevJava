package GUI.Planning;

import Models.Cours;
import Models.Planning;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierPlanningController implements Initializable {

    @FXML
    private DatePicker date_cours;

    @FXML
    private TextField cours_id;

    private Planning planning;
    private Preferences userP = Preferences.userNodeForPackage(ModifierPlanningController.class);
    private String Id = userP.get("selectedHotelId", "..");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

   
   @FXML
private void ModifierH(ActionEvent event) {
    PlanningServices hs = new PlanningServices();
    LocalDate date = date_cours.getValue();
    Date date_cours = Date.valueOf(date);

    CoursServices cs = new CoursServices();
    Cours cours = cs.GetCoursById(Integer.parseInt(cours_id.getText()));

    Planning h1 = new Planning(Integer.parseInt(Id), date_cours, cours);
    hs.updatePlanning(h1);

    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
    confirmation.setContentText("Planning est modifié avec succès");
    confirmation.show();
}


   void ModifyData(Planning planning) {
    this.planning = planning;
    cours_id.setText(String.valueOf(planning.getCours_id().getId()));
    date_cours.setValue(planning.getDate_cours().toLocalDate());
}


   @FXML
private void SupprimerH(ActionEvent event) {
    PlanningServices hs = new PlanningServices();
    int id = Integer.parseInt(Id);
    hs.deletePlanning(id);

    // Get the current stage and close it
    Stage stage = (Stage) cours_id.getScene().getWindow();
    stage.close();

    // Open the Planning page
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Planning/AfficherPlanning.fxml"));
        Parent root = loader.load();
        AfficherPlanningController planningController = loader.getController();
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
