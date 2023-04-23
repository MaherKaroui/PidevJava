/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Blog;
import Service.BlogService;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author saada
 */
public class AfficherBlogController implements Initializable {

    @FXML
    private ListView<Blog> listeB;
    Preferences prefs = Preferences.userNodeForPackage(AfficherBlogController.class);
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private Label lbcontenu;
    @FXML
    private Button likebtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BlogService b = new BlogService();

        listeB.setCellFactory(param -> new BlogCell());
        BlogService blogS = new BlogService();
        List<Blog> blog = blogS.Recuperer();
        ObservableList<Blog> items = FXCollections.observableArrayList(blog);
        listeB.setItems(items);
        listeB.setItems(items);

        listeB.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            int selectedIndex = newValue.intValue();

            Blog selectedPost = blog.get(selectedIndex);

            int postId = selectedPost.getID();
            prefs.putInt("selecteBlogID", postId);
            System.out.println(postId);

        });
        Notification.showNotification("New Notification", "vous avez un nouveau commentaire pour l'approuver!");
//Notification.showNotificationImage();

    }

    @FXML
    private void AjouterBlog(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Article.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AfficherBlogController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void modifierBlog(MouseEvent event) {
        Blog blog = listeB.getSelectionModel().getSelectedItem();
        Blog h1 = new Blog();
        BlogService hs = new BlogService();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierBlog.fxml"));
            Parent root = loader.load();
            ModifierBlogController modifierBlogController = loader.getController();
            h1 = blog;
            modifierBlogController.ModifyData(h1);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
        }

    }

    @FXML
    private void AjouterC(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CategorieAXML.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AfficherBlogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void listeCom(ActionEvent event) {
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

    @FXML
    private void StatLikes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("stat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AfficherBlogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void StatCom(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("statCom.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AfficherBlogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void populatedArticle(ActionEvent event) {
        BlogService b = new BlogService();
        int limit = 10;
        List<Blog> articles = b.getArticlesPopulairesParCommentaires(limit);

        ObservableList<Blog> observableArticles = FXCollections.observableArrayList(articles);

        listeB.setItems(observableArticles);

    }

    @FXML
    private void affichermails(MouseEvent event) {

        try {
            Desktop.getDesktop().browse(new URI("https://mail.google.com/mail/u/0/#inbox"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnlike(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Afficherlikes.fxml"));
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
