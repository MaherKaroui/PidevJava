/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Blog;
import Entity.comment;
import Service.CommentService;
import Util.MyDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author saada
 */
public class DetailsFrontController implements Initializable {

    @FXML
    private Label lbtitre;
    @FXML
    private Label lbauteur;
    @FXML
    private ImageView imgP;

    /**
     * Initializes the controller class.
     */
    private Blog fruit;
    @FXML
    private TextArea tfcontenu;
    @FXML
    private Label titre_article;
    @FXML
    private TextField tftitre;
    @FXML
    private TextField tfauteur;
    @FXML
    private TextField tfdate;
    @FXML
    private TextField tnom;
    @FXML
    private TextField temail;
    @FXML
    private TextArea tcom;
    @FXML
    private TextField tfid;
       @FXML
    private GridPane listecom;

    @Override
    
public void initialize(URL url, ResourceBundle rb) {
    // Retrieve the list of comments from the database
    CommentService commentService = new CommentService();
    List<comment> comments = commentService.Recuperer();

    // Display the comments in the GridPane
    afficherCom(comments);
}


    public DetailsFrontController() {
    }

    @FXML
    private void click(MouseEvent event) {

    }
    private int articleId;

    public void updateBlogDetails(Blog blog) {
        tftitre.setText("Titre Article : " + blog.getTitre_article());
        tfcontenu.setText("Contenu: " + blog.getContenu_article());
        tfdate.setText("Date : " + String.valueOf(blog.getDate()));
        tfauteur.setText("Auteur: " + blog.getAuteur_article());
        tfid.setText(Integer.toString(blog.getID()));

    }

    public void updateBlogDetails(Blog blog, int articleId) {
        this.articleId = articleId;

    }

    @FXML
    private void commenter(ActionEvent event) {
        CommentService co = new CommentService();
        comment newComment = new comment();

        // Set the article ID to the value entered in the text field, if it's valid
        try {
            int articleId = Integer.parseInt(tfid.getText());
            newComment.setId_article(articleId);
        } catch (NumberFormatException e) {
            // Handle the case where the text field does not contain a valid integer
            System.err.println("Invalid article ID: " + tfid.getText());
            return; // Exit the method without adding the comment to the database
        }

        // Set the other comment properties from the text fields
        newComment.setContenu_c(tcom.getText());
        newComment.setNom_c(tnom.getText());
        newComment.setEmail(temail.getText());
        newComment.setDate(LocalDate.now());
        newComment.setApproved(0);

        // Add the new comment to the database
        co.AjouterCo(newComment);
    }
     @FXML
    void liste_com(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Commentaire.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AfficherBlogController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        listecom.add(postPane, 0, row++);
    }
}
}



