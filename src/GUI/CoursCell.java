package GUI;

import Models.Cours;
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
public class CoursCell extends ListCell<Cours> {
    private ImageView imageView = new ImageView();
    private Label Nom_cours = new Label();
    private Label Activite = new Label();
    private Label date_cours = new Label();
   
    
    public CoursCell() {  
        super();
        VBox vBox = new VBox(Nom_cours, Activite,date_cours,imageView);
        HBox hBox = new HBox(imageView, vBox);
        hBox.setSpacing(10);
        vBox.setSpacing(5);
        setGraphic(hBox);
                Nom_cours.setFont(Font.font("System", FontWeight.BOLD, 14));
        Nom_cours.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

Nom_cours.setFont(Font.font("System", FontWeight.BOLD, 14));
Activite.setFont(Font.font("System", FontWeight.BOLD, 12));
date_cours.setFont(Font.font("System", FontWeight.BOLD, 12));
date_cours.setFont(Font.font("System", FontWeight.BOLD, 12));

imageView.setFitWidth(100); // définir une largeur de 100 pixels
imageView.setFitHeight(100);
    }
 @Override
    public void updateItem(Cours cours, boolean empty) {
        super.updateItem(cours, empty);
        if (empty || cours == null) {
            setText(null);
            setGraphic(null);
        } else {
            Nom_cours.setText("nom : "+cours.getNom_cours());
            Activite.setText("Activite: "+cours.getActivite());
           // Nbre_chmbreLabel.setText("Le nombre de chambre encore disponible : "+String.valueOf(cours.getNbre_chambres()));
            date_cours.setText("Date : "+String.valueOf(cours.getDate_cours()));
            Image image = new Image("file:///C:/Users/ASUS/Desktop/cross.jpg");
            imageView.setImage(image);
            setGraphic(getListCell());
        }

    }  

    private HBox getListCell() {
        HBox hBox = new HBox(imageView, new VBox(Nom_cours, Activite,date_cours));
          hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
        hBox.setStyle("-fx-background-color: #edece6; -fx-background-radius: 10px;");
        Separator separator = new Separator(Orientation.HORIZONTAL);
        VBox.setVgrow(separator, Priority.ALWAYS);
        VBox vBox = new VBox(hBox, separator);
        return hBox;
    }    
}