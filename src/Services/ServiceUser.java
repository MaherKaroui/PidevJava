/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import entites.User;
import util.dbconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;
import pidevuser.PidevUser;

/**
 *
 * @author Souid
 */
public class ServiceUser {
     private static ServiceUser instance;
     Connection cn = dbconnection.getInstance().getConnection();
    dbconnection cnx = dbconnection.getInstance();
     public static final String ACCOUNT_SID = "ACcc92f26eb952fc53fb21ffcbf0b95e41";
    public static final String AUTH_TOKEN = "e17f4a7192326c485a9c58bd11119fe1";
    public ServiceUser(){
        
    }
    
   public static ServiceUser getInstance(){
        if(instance == null)
            instance = new ServiceUser();
        return instance;
    }
    public ObservableList<User> getUserList(){
         ObservableList<User> UserList= FXCollections.observableArrayList();
         
         String query = "SELECT * from user";
         Statement st ;
         ResultSet rs ;
         try {
             st = cn.createStatement();
             rs=st.executeQuery(query);
             User users ;
             while(rs.next())
             {
                 users=new User( rs.getInt("id"), rs.getString("email"),  rs.getString("num_telephone"),  rs.getString("roles"),  rs.getInt("score"),  rs.getInt("nb_etoile"),  rs.getString("nom"),  rs.getString("prenom"));
                 System.out.println(rs.getString("id"));
                 UserList.add(users);
             }
         }catch(Exception ex )
         {
             ex.printStackTrace();
         }
         return UserList ;
     }
    public void addUser(User user) throws SQLException
    {
        
         
         
        String pw_hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
         String query = "INSERT INTO user (email,roles,password,is_verified,num_telephone,type,score,nb_etoile,nom,prenom,image,blocked) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
         PreparedStatement st = cn.prepareStatement(query);
            st.setString(1, user.getEmail());
            st.setString(2, "ROLE_USER");
            st.setString(3, pw_hash);
            st.setInt(4,0);//isverified
            st.setString(5, user.getNum_telephone());
            st.setString(6, user.getType());
            
            st.setInt(7, 0);
            st.setInt(8,0);
            st.setString(9, user.getNom());
            st.setString(10, user.getPrenom());
            st.setString(11, user.getImage());
            st.setInt(12, 0);
              st.executeUpdate();
            System.out.println("User ajoutée");
        
    }
    public void editUser(User user) throws SQLException{
        String req = "UPDATE user SET "
                  + "email = ?,"
                    + "num_telephone = ?,"   
                    + "type= ?,"
                    + "nom = ?,"
                    + "prenom = ?,"
                    + "image = ? "
                    + " where id=?";
        
        System.out.println(req);
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setString(1, user.getEmail());
        pre.setString(2, user.getNum_telephone().toLowerCase());
        pre.setString(3, user.getType());
        pre.setString(4, user.getNom().toLowerCase());
        pre.setString(5, user.getPrenom());
        pre.setString(6, user.getImage());
        pre.setInt(7, pidevuser.PidevUser.user.getId());
        pre.executeUpdate();
        
    }
    public void BlockUser(String email) throws SQLException{
        String req = "UPDATE user SET "
                  + "blocked = ?"        
                    + " where email=?";
        
        System.out.println(req);
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, 1);
        pre.setString(2, email);
        pre.executeUpdate();
          JOptionPane.showMessageDialog(null, "This account has been temporarily blocked !");  
            
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                    Message message = Message.creator(new PhoneNumber("+21623610318"),
        new PhoneNumber("+16205091869"), 
        "This account has been now blocked").create();  
        
    }
     public void unBlockUser(String email) throws SQLException{
        String req = "UPDATE user SET "
                  + "blocked = ?"        
                    + " where email=?";
        
        System.out.println(req);
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, 0);
        pre.setString(2, email);
        pre.executeUpdate();
        
    }
      public User searchUserByEmail(String pseudo, String password) throws SQLException {
        User user = null;
      String req="SELECT (password) FROM user where (nom=? OR email=?)";
      PreparedStatement st1 = cn.prepareStatement(req);
        st1.setString(1, pseudo.toLowerCase());
        st1.setString(2, pseudo.toLowerCase());
        ResultSet rs1 = st1.executeQuery();
        while (rs1.next()){
            if(BCrypt.checkpw(password,"$2a$"+rs1.getString("password").substring(4, rs1.getString("password").length()))){
                String requete = "SELECT * FROM user where (nom=? OR email=?)";
                PreparedStatement st = cn.prepareStatement(requete);
                st.setString(1, pseudo.toLowerCase());
                st.setString(2, pseudo.toLowerCase());


                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setRoles(rs.getString("roles"));
                    user.setPassword(rs.getString("password"));
                    user.setIsVerified(rs.getBoolean("is_verified"));
                    user.setPassword(rs.getString("password"));
                    user.setNum_telephone(rs.getString("num_telephone"));
                    user.setType(rs.getString("type"));
                    user.setScore(rs.getInt("score"));
                    user.setNb_etoile(rs.getInt("nb_etoile"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setImage(rs.getString("image"));
                    user.setBlocked(rs.getBoolean("blocked"));
                    System.out.println("User found");

                }
            }else{
                System.out.println("User not found");
            }
        }
        
        return user;
    }
       public void UpdateUser(String email) throws SQLException{
        String req = "UPDATE user SET "
                  + "blocked = ?"        
                    + " where email=?";
        
        System.out.println(req);
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, 1);
        pre.setString(2, email);
        pre.executeUpdate();
        
    }
      
      
 

    
}
