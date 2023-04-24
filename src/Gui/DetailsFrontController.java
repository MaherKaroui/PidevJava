/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Blog;
import Entity.PostLike;
import Entity.User;
import Entity.categorieA;
import Entity.comment;
import Service.BlogService;
import Service.CommentService;
import Util.MyDB;
import animatefx.animation.Bounce;
import animatefx.animation.FadeIn;
import animatefx.animation.Flash;
import animatefx.animation.Pulse;
import com.github.plushaze.traynotification.animations.Animation;
import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;
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
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import tray.animations.AnimationType;

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
    private String recipient = "saadallahchaima58@gmail.com"; // L'adresse e-mail du destinataire

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
    private int numberOfLikes = 0;
    private static final int MAX_POSTS = 10;

    @FXML
    private ListView<comment> listecom;
    Preferences prefs = Preferences.userNodeForPackage(DetailsFrontController.class);
    private int articleId;
    private TextField nblike;
    @FXML
    private HBox topBar;
    @FXML
    private Button returnBtn;
    @FXML
    private Button modifiercom;
    @FXML
    private Button comemntebutton;
    @FXML
    private Button ActualiserButton;
    @FXML
    private Button SupprimerButoon;
    @FXML
    private ImageView imglike;
    @FXML
    private Button buttonpartage;
    @FXML
    private ImageView imgpartage;

    public void updateBlogDetails(Blog blog) {
        tftitre.setText("Titre Article : " + blog.getTitre_article());
        tfcontenu.setText("Contenu: " + blog.getContenu_article());
        tfdate.setText("Date : " + String.valueOf(blog.getDate()));
        tfauteur.setText("Auteur: " + blog.getAuteur_article());
        tfid.setText(Integer.toString(blog.getID()));
        try {
        BlogService blogService = new BlogService();

        List<ImageView> imageViews = blogService.Recuperer_images(blog.getID());
        if (!imageViews.isEmpty()) {
            imgP.setImage(imageViews.get(0).getImage());
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Flash(likeButton).play();
        new Flash(modifiercom).play();
        new Flash(buttonpartage).play();
        new Flash(imgpartage).play();
        new Flash(ActualiserButton).play();
        new Flash(imglike).play();
        new Flash(SupprimerButoon).play();
        new Flash(comemntebutton).play();
        new Bounce(tcom).play();

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
        String contenu = tcom.getText().trim();
        String nom = tnom.getText().trim();
        String email = temail.getText().trim();

        if (contenu.isEmpty() || nom.isEmpty() || email.isEmpty()) {
            new Bounce(tcom).play();
            new Bounce(tnom).play();
            new Bounce(temail).play();
            new Pulse(comemntebutton);

            return;
        }

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
        try {
            Mailing.sendEmail(recipient, contenu);
            System.out.println("Le message a été envoyé avec succès !");
            String title = "un mail est envoyé pour valider votre commentaire ";
            String message = "success";
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            tray.setTitle(title);
            tray.setMessage(message);
            tray.showAndDismiss(Duration.millis(3000));
        } catch (Exception ex) {
            System.out.println("Une erreur s'est produite : " + ex.getMessage());
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("commentaire ajouter avec succes");
        alert.showAndWait();
        String title = "commentaire ajouter avec succes";
        String message = "success";
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setTitle(title);
        tray.setMessage(message);
        tray.showAndDismiss(Duration.millis(3000));
        listecom.refresh();
    }

    @FXML
    void listecomm(ActionEvent event) {

        comment newComment = new comment();

        CommentService commentService = new CommentService();
        int articleId;
        try {
            articleId = Integer.parseInt(tfid.getText());
        } catch (NumberFormatException e) {
            // Handle the case where the text field does not contain a valid integer
            System.err.println("Invalid article ID: " + tfid.getText());
            return;
        }

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

        // Afficher une notification
        listecom.refresh();

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
        new Flash().play();

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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Like deleted");
        alert.setContentText("the post was successfully deleted.");
        alert.showAndWait();
        listecom.refresh();
        String title = "Supprimer";
        String message = "Error";
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setTitle(title);
        tray.setMessage(message);
        tray.showAndDismiss(Duration.millis(3000));
    }

    @FXML
    private void handleLikeButton(ActionEvent event) {

        BlogService blogService = new BlogService();
        int articleId = Integer.parseInt(tfid.getText());
        User user = new User();

        List<PostLike> postLikes = blogService.islikedbyuser(articleId, user.getId());

        blogService.ajouterlike(articleId, "ayedi.malek@esprit.tn");

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Like updated");
        alert.setContentText("The like was successfully updated on the article.");

        alert.showAndWait();

    }

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

    @FXML
    private void partagerfacebook(ActionEvent event) {
        String accessToken = "EAAIocBxlkWgBAG9FoCJO95u7rAhlVOQRHwE5rPpZBB81PMIbK0PZB9qB9Rp66ClIvS6US2IZB36ZByYouNmkSuKkO3XBBZCZAoVoZBZCFL2FyOBMhs2Y77pMlDXzkA6PTj0a4npIxOPDZBZBGunyiOmzHDejoEGKlN6amcT4bPOZA1peHKdfUEZA3Jko1L2S8DFk08FzmGXIZBdZCU4KnXDuEgz66y";

        String linkUrl = "https://www.Blog.com";

        String message = "Un lien intéressant à partager";

        FacebookShare facebookShare = new FacebookShare();
        facebookShare.shareLink(accessToken, linkUrl, message);
    }

    @FXML
    private void back(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PostFront.fxml"));
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
