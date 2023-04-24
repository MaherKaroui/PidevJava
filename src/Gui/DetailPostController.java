/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Blog;
import Service.BlogService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author saada
 */
public class DetailPostController implements Initializable {

    @FXML
    private Label titre_article;
    private Label date_a;
    private Label auteur_article;
    @FXML
    private TextField tftitre;
    @FXML
    private TextField tfauteur;
    @FXML
    private TextField tfdate;
    @FXML
    private TextArea tfcontenu;
    @FXML
    private TextField tfbest;
    @FXML
    private ImageView image_article;
    private Blog blog;

    /**
     * Initializes the controller class.
     */
    public DetailPostController() {
        // Initialize the controller
    }

    public DetailPostController(Blog blog) {
        this.blog = blog;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //ToDo

    }

    public void updateBlogDetails(Blog blog) throws FileNotFoundException {
        tftitre.setText("Titre Article : " + blog.getTitre_article());
        tfcontenu.setText("Contenu: " + blog.getContenu_article());
        tfdate.setText("Date : " + String.valueOf(blog.getDate()));
        tfauteur.setText("Auteur: " + blog.getAuteur_article());
        if (blog.getIs_best() == 1) {
            tfbest.setText("Sélection : isBest");
        } else {
            tfbest.setText("Sélection : notBest");
        }


 try {
        BlogService blogService = new BlogService();

        List<ImageView> imageViews = blogService.Recuperer_images(blog.getID());
        if (!imageViews.isEmpty()) {
            image_article.setImage(imageViews.get(0).getImage());
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
     
    }

  

    private void commenter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCom.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AfficherBlogController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
