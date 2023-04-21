/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Blog;
import Service.BlogService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author saada
 */
public class menuBlogController  implements Initializable {
        @FXML
    private GridPane panePosts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
          List<Blog> posts = new ArrayList<>();
          BlogService b =new BlogService();
          b.Recuperer();
          afficherPosts(posts);
        
    }

    private void afficherPosts(List<Blog> posts) {
  int row = 0;
    for (Blog post : posts) {
        // Créer un panneau pour le post
        VBox postPane = new VBox();
        postPane.setSpacing(10);

        // Ajouter le titre du post
        Label titleLabel = new Label(post.getTitre_article());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        postPane.getChildren().add(titleLabel);

        // Ajouter le contenu du post
        Label contentLabel = new Label(post.getContenu_article());
        contentLabel.setWrapText(true);
        postPane.getChildren().add(contentLabel);

        // Ajouter le panneau à la grille de panneaux
        panePosts.add(postPane, 0, row++);
    }    }

    
}
