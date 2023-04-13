package GUI.Planning;

import GUI.*;
import Models.Planning;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author MaherKaroui
 */
public class PlanningCell extends ListCell<Planning> {
    private ImageView imageView = new ImageView();
    private Label cours = new Label();
    
    private Label date_cours = new Label();
   
    
    public PlanningCell() {  
        super();
        VBox vBox = new VBox(cours,date_cours,imageView);
        HBox hBox = new HBox(imageView, vBox);
        hBox.setSpacing(10);
        vBox.setSpacing(5);
        setGraphic(hBox);
                cours.setFont(Font.font("System", FontWeight.BOLD, 14));
        cours.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

cours.setFont(Font.font("System", FontWeight.BOLD, 14));

date_cours.setFont(Font.font("System", FontWeight.BOLD, 12));


imageView.setFitWidth(100); // définir une largeur de 100 pixels
imageView.setFitHeight(100);
    }
 @Override
    public void updateItem(Planning planning, boolean empty) {
        super.updateItem(planning, empty);
        if (empty || planning == null) {
            setText(null);
            setGraphic(null);
        } else {
            cours.setText("nom : "+planning.getCours_id().getNom_cours());
            
           // Nbre_chmbreLabel.setText("Le nombre de chambre encore disponible : "+String.valueOf(cours.getNbre_chambres()));
            date_cours.setText("Date : "+String.valueOf(planning.getDate_cours()));
            Image image = new Image("file:///C:/Users/ASUS/Desktop/cross.jpg");
            imageView.setImage(image);
            setGraphic(getListCell());
        }

    }  

    private HBox getListCell() {
        HBox hBox = new HBox(imageView, new VBox(cours,date_cours));
          hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
        hBox.setStyle("-fx-background-color: #edece6; -fx-background-radius: 10px;");
        Separator separator = new Separator(Orientation.HORIZONTAL);
        VBox.setVgrow(separator, Priority.ALWAYS);
        VBox vBox = new VBox(hBox, separator);
        return hBox;
    }    
}