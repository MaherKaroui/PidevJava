/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Blog;
import Entity.comment;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author saada
 */
public class comCell extends ListCell<comment> {
    private Label nom = new Label();
    private Label email = new Label();
    private Label date_com = new Label();
    private Label commentaire = new Label();
    private VBox vBox = new VBox(nom, email, date_com, commentaire);

    public comCell() {
        super();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        nom.setStyle("-fx-font-weight: bold;");
        email.setStyle("-fx-font-style: italic;");
        date_com.setStyle("-fx-font-size: 12px; -fx-text-fill: #999999;");
        commentaire.setStyle("-fx-text-fill: #333333;");
    }

    @Override
    protected void updateItem(comment item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            nom.setText(item.getNom_c());
            email.setText(item.getEmail());
            date_com.setText(item.getDate_com().toString());
            commentaire.setText(item.getContenu_c());
            setGraphic(vBox);
        }
    }
}


