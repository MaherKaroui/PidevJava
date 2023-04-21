/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Blog;
import Entity.PostLike;
import Entity.User;
import Entity.comment;
import Service.BlogService;
import Service.CommentService;
import Util.MyDB;
import com.mysql.jdbc.StringUtils;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
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
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


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
    private Button likeButton;
      private boolean isLiked = false;
      
    @FXML
    private TextField idlike;

    @FXML
    private ListView<comment> listecom;
    Preferences prefs = Preferences.userNodeForPackage(DetailsFrontController.class);
    private int articleId;
    @FXML
    private TableView<PostLike> tablieks;
    @FXML
    private TableColumn<?, ?> emailuser;

    public void updateBlogDetails(Blog blog) {
        tftitre.setText("Titre Article : " + blog.getTitre_article());
        tfcontenu.setText("Contenu: " + blog.getContenu_article());
        tfdate.setText("Date : " + String.valueOf(blog.getDate()));
        tfauteur.setText("Auteur: " + blog.getAuteur_article());
        tfid.setText(Integer.toString(blog.getID()));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Récupérer tous les commentaires associés à l'article

    }

    public DetailsFrontController() {
    }

    @FXML
    private void click(MouseEvent event) {
        comment t = listecom.getSelectionModel().getSelectedItem();
        tnom.setText(String.valueOf(t.getNom_c()));
        temail.setText(t.getEmail());
        tcom.setText(t.getContenu_c());

    }

    public void updateBlogDetails(Blog blog, int articleId) {
        this.articleId = articleId;

    }

    @FXML
    private void commenter(ActionEvent event) {
        CommentService co = new CommentService();
        comment newComment = new comment();

        try {
            int articleId = Integer.parseInt(tfid.getText());
            newComment.setId_article(articleId);
        } catch (NumberFormatException e) {
            System.err.println("Invalid article ID: " + tfid.getText());
            return; 
        }

        newComment.setContenu_c(tcom.getText());
        newComment.setNom_c(tnom.getText());
        newComment.setEmail(temail.getText());
        newComment.setDate(LocalDate.now());
        newComment.setApproved(0);

        co.AjouterCo(newComment);
    }





    @FXML
    void listecomm(ActionEvent event) {

        {
            CommentService commentService = new CommentService();
            int articleId;
            try {
                articleId = Integer.parseInt(tfid.getText());
            } catch (NumberFormatException e) {
                // Handle the case where the text field does not contain a valid integer
                System.err.println("Invalid article ID: " + tfid.getText());
                return;
            }

            comment newComment = new comment();
            newComment.setId_article(articleId);

            List<comment> comments;
            try {
                comments = commentService.getCommentsByArticle(articleId);
                comments = comments.stream().filter(c -> c.getApproved() == 1).collect(Collectors.toList());

            } catch (Exception e) {
                System.err.println("Error retrieving comments: " + e.getMessage());
                return;
            }

            ObservableList<comment> commentList = FXCollections.observableArrayList(comments);

            listecom.setItems(commentList);

            listecom.setCellFactory(param -> new comCell());

            listecom.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

                int selectedIndex = newValue.intValue();

                comment selectedComment = commentList.get(selectedIndex);

                int commentId = selectedComment.getID();
                prefs.putInt("selectedCommentID", commentId);
                System.out.println(commentId);
            });

        }

    }

    @FXML
    void modifier(ActionEvent event) {
        CommentService cc = new CommentService();
        comment c = new comment();
        c = listecom.getSelectionModel().getSelectedItem();
        c.setID(listecom.getSelectionModel().getSelectedItem().getID());
        c.setNom_c(tnom.getText());
        c.setEmail(temail.getText());
        c.setContenu_c(tcom.getText());

        // Mettre à jour l'état "approved" du commentaire en fonction de l'état de la case à cocher
        // Appeler la méthode de service pour mettre à jour le commentaire dans la base de données
        cc.ModifierCo(c);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("commentaire modifié avec succes");
        alert.showAndWait();
        listecom.refresh();
    }

    @FXML
    void supprimer(ActionEvent event) {

        CommentService co = new CommentService();
        comment c = new comment();

        c = listecom.getSelectionModel().getSelectedItem();

        co.SupprimerCo(c.getID());

    }

    

 

    
     @FXML
    private void handleLikeButton(ActionEvent event) {
  
    BlogService blogService = new BlogService();
    int articleId = Integer.parseInt(tfid.getText());
    User user = new User();

    // Check if the user has already liked the post
    List<PostLike> postLikes = blogService.islikedbyuser(articleId, user.getId());

    // Remove the like if it exists
   
    // Add the like if it doesn't exist
    
        blogService.ajouterlike(articleId,"ayedi.malek@esprit.tn");
    


    

    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Success");
    alert.setHeaderText("Like updated");
    alert.setContentText("The like was successfully updated on the article.");

    alert.showAndWait();
       
    }

  @FXML
private void remove(ActionEvent event) {
    BlogService blogService = new BlogService();
    PostLike p = new PostLike();
    String idlikeStr = idlike.getText().trim(); 
    if (idlikeStr.isEmpty() || idlikeStr.trim().length() == 0) {
        System.err.println("Invalid idlike ID: " + idlike.getText());
        return;
    }
    try {
        int likeId = Integer.parseInt(idlikeStr);
        p.setId(likeId);
        User user = new User();
        blogService.removeLike(likeId);
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Like deleted");
        alert.setContentText("The like was successfully updated on the article.");
        alert.showAndWait();
    } catch (NumberFormatException e) {
        System.err.println("Invalid idlike ID: " + idlike.getText());
        return;
    }
}
public void loadPostDetails(Blog post) {
    BlogService blogService = new BlogService();
    // Récupérez les likes relatifs au poste à partir de la base de données et stockez-les dans une liste d'objets Like
    List<PostLike> postLikes = blogService.getLikesByPost(post);

    // Créez un ObservableList en utilisant la liste d'objets Like que vous avez créée
    ObservableList<PostLike> likesList = FXCollections.observableArrayList(postLikes);

    // Configurez votre TableView avec la source de données de l'ObservableList que vous avez créée
    tablieks.setItems(likesList);

    // Mappez les propriétés de l'objet Like aux colonnes de TableView appropriées
    emailuser.setCellValueFactory(new PropertyValueFactory<>("email"));

    // Chargez votre TableView pour afficher les likes relatifs au poste
    tablieks.refresh();
}

    @FXML
    private void like(MouseEvent event) {

    }

    


}
