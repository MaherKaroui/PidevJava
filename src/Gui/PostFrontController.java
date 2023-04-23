//blogCell
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Blog;
import Entity.categorieA;
import Entity.comment;
import Service.BlogService;
import Service.CategorieService;
import Util.MyDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author saada
 */
public class PostFrontController implements Initializable {

    private Label lbtitre;
    private Label lbauteur;
    private Label lbcontenu;
    private ScrollPane ScrolPosts;
    private TextField emailTextField;
    private TextArea commentaireTextArea;

    /**
     * Initializes the controller class.
     */
    //private MyListener myListener;
    @FXML
    private ListView<Blog> listeB;
    private ObservableList<Blog> filteredBlogs;

    Preferences prefs = Preferences.userNodeForPackage(PostFrontController.class);
    private DetailsFrontController detailsFrontController;
    @FXML
    private HBox topBar;
    @FXML
    private Button returnBtn;
    @FXML
    private TableColumn<categorieA, String> ColType;
    @FXML
    private TableView<categorieA> tab;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BlogService b = new BlogService();

        listeB.setCellFactory(param -> new BlogCellFront());
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

        ColType.setCellValueFactory(new PropertyValueFactory<>("type"));

        List<categorieA> listePosts = loadPostsFromDatabase();

        tab.setItems(FXCollections.observableArrayList(listePosts));

    }

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

    private void ajouterCommentaire(ActionEvent event) {
        int postId = prefs.getInt("selecteBlogID", -1);
        if (postId == -1) {
            return;
        }

        BlogService blogService = new BlogService();
        Blog selectedPost = blogService.getBlogById(postId);
        comment newComment = new comment();
        newComment.setId_article(selectedPost.getID());
        String email = emailTextField.getText();
        String contenu_c = commentaireTextArea.getText();

        newComment.setEmail(email);
        newComment.setContenu_c(contenu_c);
        selectedPost.addComment(newComment);
        blogService.ModifierBlog2(selectedPost);

    }

    private void handleAjouterCommentaire(ActionEvent event) {
        ajouterCommentaire(event);
    }

    @FXML
    private void listecateg(MouseEvent event) {
        int selectedRowIndex = tab.getSelectionModel().getSelectedIndex();
        if (selectedRowIndex != -1) {
            categorieA selectedCategory = tab.getItems().get(selectedRowIndex);
            int categoryId = selectedCategory.getId();
            BlogService b = new BlogService();
            List<Blog> blogList = b.AfficherArticlesByCategoryId(categoryId);
            ObservableList<Blog> observableBlogList = FXCollections.observableList(blogList);
            listeB.setItems(observableBlogList);
        }
    }

    public ObservableList<Blog> filterBlogsByCategory(ObservableList<Blog> blogs, String categoryName) {
        ObservableList<Blog> filteredBlogs = FXCollections.observableArrayList();
        for (Blog blog : blogs) {
            if (blog.getAuteur_article().equals(categoryName)) {
                filteredBlogs.add(blog);
            }
        }
        return filteredBlogs;
    }

    private List<categorieA> loadPostsFromDatabase() {
        List<categorieA> categg = new ArrayList<>();

        CategorieService catgService = new CategorieService();
        categg = catgService.Recuperer();

        return categg;
    }

}
