/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entites.User;
import pidevuser.PidevUser;
import Services.ServiceUser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Souid
 */
public class EditprofileController implements Initializable {
String imagePath="";
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private TextField type;
    @FXML
    private TextField num_telephone;
@FXML
    private ImageView pic;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         User user = pidevuser.PidevUser.user;
          final String imageURI4 = new File(user.getImage()).toURI().toString();
       pic.setImage(new Image(imageURI4));
   
        nom.setText(user.getNom());
        email.setText(user.getEmail());
        prenom.setText(user.getPrenom());
        type.setText(user.getType());
        num_telephone.setText(user.getNum_telephone());
    }    
     @FXML
    private void addImage(ActionEvent event) {
        FileChooser fc = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File selectedFile = fc.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            Image image2 = SwingFXUtils.toFXImage(bufferedImage, null);
            //imageIn.setFill(new ImagePattern(image2));
            imagePath = selectedFile.toURI().toURL().toString();
             pic.setImage(image2);
        } catch (IOException ex) {
            Logger.getLogger(EditprofileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
     private void editUser(ActionEvent event) throws IOException, SQLException {
       
           
           
           if(ValidateEmptyForm(nom,prenom,type,num_telephone,email,pic)
            && ValidateName(nom) && ValidateEmail(email) && ValidateNumTel(num_telephone))
        {
           
            User userx = new User(email.getText(),pidevuser.PidevUser.user.getPassword(), num_telephone.getText(),type.getText(), nom.getText(), prenom.getText(),imagePath);
           System.out.println("***************"+userx);
            
               
                ServiceUser.getInstance().editUser(userx);
                
                
                //  FXMLLoader loader =  new FXMLLoader(getClass().getResource("../Views/Login.fxml"));
            

            
        }
       
                
    }
     
      @FXML
    private void back()throws IOException {
        PidevUser m = new PidevUser() ;
         m.changeScene("/gui/LoggedInClient.fxml");
        
    }
    
      private boolean ValidateEmptyForm(TextField nom, TextField prenom,TextField email,TextField type,TextField num_telephone ,ImageView img){
         if (nom.getText().equals("")  || prenom.getText().equals("") ||
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
       
      private boolean ValidateNumTel(TextField t) {
        Pattern p = Pattern.compile("^\\d{8}$");

        Matcher m = p.matcher(t.getText());
        if (m.find() && m.group().equals(t.getText())) {
            return true;

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText(t.getText() + " : Numero non valide , format standard :/n/r"
                    + "seu1ement 8 chiffres ");
            alert.showAndWait();

            return false;
        }
    }
    
}
