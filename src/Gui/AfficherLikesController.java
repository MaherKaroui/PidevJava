/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Blog;
import Entity.PostLike;
import Entity.comment;
import Service.BlogService;
import Service.CommentService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author saada
 */
public class AfficherLikesController implements Initializable {

    @FXML
    private TableView<PostLike> tabLikes;
    @FXML
    private TableColumn<PostLike, Integer> colArticle;
    @FXML
    private TableColumn<Blog, Integer> colNBlikes;
    @FXML
    private TableColumn<PostLike, String> colUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Blog b=new Blog();
BlogService bb= new BlogService();
PostLike p =new PostLike();

      colUser.setCellFactory(column -> {
    return new TableCell<PostLike, String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText("");
            } else {
                setText("User " + item);
            }
        }
    };
});


        
   colArticle.setCellValueFactory(new PropertyValueFactory<>("articles_id"));


        try {
    List<PostLike> listelikes = loadLikesFromDatabase();
    tabLikes.setItems(FXCollections.observableArrayList(listelikes));
} catch (SQLException ex) {
    Logger.getLogger(AfficherLikesController.class.getName()).log(Level.SEVERE, null, ex);
}
    }

    @FXML
    private void listeLikes(MouseEvent event) {
        
        
        
    }
  private List<PostLike> loadLikesFromDatabase() throws SQLException {
    List<PostLike> likes = new ArrayList<>();
    BlogService blogService = new BlogService();
    List<PostLike> coco = blogService.getAllLikesForPost();

    // Ajout des données dans la table
    tabLikes.setItems(FXCollections.observableArrayList(coco));
    return coco;
}

 
}

