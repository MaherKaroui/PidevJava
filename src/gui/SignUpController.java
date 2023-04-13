/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entites.User;
import Services.ServiceUser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.PauseTransition;
import pidevuser.PidevUser;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Souid
 */
public class SignUpController implements Initializable {
 public String imagePath="";
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private TextField mot_de_passe;
    @FXML
    private TextField confirmerMotDePasse;
    @FXML
    private TextField type;
    @FXML
    private TextField num_telephone;
    @FXML
    private ImageView pic;
    @FXML
    private Circle imageIn;
     @FXML
    private Button imageB;
    /**
     * Initializes the controller class.
     */
     @FXML 
     private Button SignUp ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void addImage(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File selectedFile = fc.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
//            imageIn.setFill(new ImagePattern(image));
            
             pic.setImage(image);
              imagePath = selectedFile.toURI().toURL().toString();
            System.out.println("Image URL: " + imagePath);
        } catch (IOException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    private void addUser(ActionEvent event) throws IOException, SQLException {

         if(ValidateEmptyForm(nom,prenom,confirmerMotDePasse,mot_de_passe,type,num_telephone,email,pic)
            && ValidateName(nom) && ValidateEmail(email) && ValidateMdp(mot_de_passe) && ValidateNumTel(num_telephone))
        {
                    User user = new User(email.getText(), mot_de_passe.getText(), num_telephone.getText(), type.getText(), nom.getText(), prenom.getText(),imagePath);
                    

                    
                    ServiceUser.getInstance().addUser(user);

                   

                 }}
    @FXML
    private void login()throws IOException {
        PidevUser m = new PidevUser() ;
         m.changeScene("/gui/login.fxml");
        
    }
    
    
      private boolean ValidateEmptyForm(TextField nom, TextField prenom,TextField email,TextField confirmerMotDePasse, TextField mot_de_passe,TextField type,TextField num_telephone,ImageView img){
         if (nom.getText().equals("")  || prenom.getText().equals("") || confirmerMotDePasse.getText().equals("") || mot_de_passe.getText().equals("") || 
                 type.getText().equals("") || num_telephone.getText().equals("") || email.getText().equals("") || img.getImage()==null ){
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez remplir tous les champs");
             alert.showAndWait();
             
             return false;  
        } else {
             return true;  
         }
     }
    
    private boolean ValidateName(TextField t){
         Pattern p = Pattern.compile("[a-zA-Z]+");
         Matcher m = p.matcher(t.getText());
         if (m.find() && m.group().equals(t.getText())){
             return true;
             
         }else{
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText(t.getText()+" : nom non valide , votre nom doit contenir seulement que des lettres");
             alert.showAndWait();
             
             return false;
         }
     }
    
     private boolean ValidateEmail(TextField t){
         Pattern p = Pattern.compile("^(.+)@(.+)$");
         Matcher m = p.matcher(t.getText());
         if (m.find() && m.group().equals(t.getText())){
             return true;
             
         }else{
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText(t.getText()+" : email non valide , le format standard exemple@exemple.exemple");
             alert.showAndWait();
             
             return false;
         }
     }
    
       private boolean ValidateMdp(TextField t){
         Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
         Matcher m = p.matcher(t.getText());
         if (m.find() && m.group().equals(t.getText())){
             return true;
             
         }else{
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText(t.getText()+" : password non valide , format standard : \n\r"
                     + "a digit must occur at least once \n\r"
                     + "a lower case letter must occur at least once \n\r"
                     + "an upper case letter must occur at least once \n\r"
                     + "a special character must occur at least once \n\r"
                     + "no whitespace allowed in the entire string \n\r"
                     + "8-16 character password, both inclusive \n\r");
             alert.showAndWait();
             
             return false;
         }
     }
       
       private boolean ValidateNumTel(TextField t){
         Pattern p = Pattern.compile("^\\d{8}$");
         
         Matcher m = p.matcher(t.getText());
         if (m.find() && m.group().equals(t.getText())){
             return true;
             
         }else{
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setTitle("Erreur");
             alert.setHeaderText(null);
             alert.setContentText(t.getText()+" : Numero non valide , format standard :/n/r"
                     + "seu1ement 8 chiffres ");
             alert.showAndWait();
             
             return false;
         }
     }
   
   
}
       
      
  

