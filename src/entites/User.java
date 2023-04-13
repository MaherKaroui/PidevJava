/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

/**
 *
 * @author Souid
 */
public class User {
    int id ;
    String email;
    String roles ;
    String Password ;
    Boolean isVerified ;
    String num_telephone ;
    String type ;
    int  score ;
    int nb_etoile ;
    String nom ; 
    String prenom ;
    String image ;
    Boolean blocked ;

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

  
    

    public User(int id ,String email ,String roles,String Password ,Boolean isVerified,String num_telephone,String type,int score,int nb_etoile)
        {
            this.id=id ;
            this.email=email ;
            this.roles=roles ;
            this.Password =Password ;
            this.num_telephone = num_telephone ;
            this.type=type ;
            this.score =score ;
            this.nb_etoile = nb_etoile ;
            
        }

    public User(String email, String Password, String num_telephone, String type, String nom, String prenom, String image) {
        this.email = email;
        this.Password = Password;
        this.num_telephone = num_telephone;
        this.type = type;
        this.nom = nom;
        this.prenom = prenom;
        this.image = image;
    }

    public User(int id ,String email, String num_telephone, String roles, int score, int nb_etoile,String nom, String prenom) {
        this.id = id;
        this.email = email;
        this.num_telephone = num_telephone;
        this.roles = roles;
        this.score = score;
        this.nb_etoile = nb_etoile;
        this.nom = nom;
        this.prenom = prenom;
    }

    public User() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getNum_telephone() {
        return num_telephone;
    }

    public void setNum_telephone(String num_telephone) {
        this.num_telephone = num_telephone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNb_etoile() {
        return nb_etoile;
    }

    public void setNb_etoile(int nb_etoile) {
        this.nb_etoile = nb_etoile;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
      public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}
