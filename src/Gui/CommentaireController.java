/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Blog;
import Entity.comment;
import Service.BlogService;
import Service.CommentService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * FXML Controller class
 *
 * @author saada
 */
public class CommentaireController implements Initializable {
 @FXML
    private GridPane paneListeCom;

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comment c =new comment();
         List<comment> com = new ArrayList<>();
          CommentService b =new CommentService();
          b.Recuperer();
          afficherCom(com);
    }    

    
 @FXML
    void click(MouseEvent event) {

    }

    private void afficherCom(List<comment> com) {
  int row = 0;
    for (comment post : com) {
        // Créer un panneau pour le post
        VBox postPane = new VBox();
        postPane.setSpacing(10);

        // Ajouter le titre du post
        Label titleLabel = new Label(post.getEmail());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        postPane.getChildren().add(titleLabel);

        // Ajouter le contenu du post
        Label contentLabel = new Label(post.getContenu_c());
        contentLabel.setWrapText(true);
        postPane.getChildren().add(contentLabel);

        // Ajouter le panneau à la grille de panneaux
        paneListeCom.add(postPane, 0, row++);
    }        }
    
}
