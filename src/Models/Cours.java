
package Models;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author MaherKaroui
 */
public class Cours {
    private int id;
    private String nom_cours,activite,image;
    private  Date date_cours; 

    public Cours() {
    }


    

    public Cours(int id, String nom_cours, String activite, String image, Date date_cours) {
        this.id = id;
        this.nom_cours = nom_cours;
        this.activite = activite;
        this.image = image;
        this.date_cours = date_cours;
    }

    public Cours(String nom_cours, String activite, String image, Date date_cours) {
        this.nom_cours = nom_cours;
        this.activite = activite;
        this.image = image;
        this.date_cours = date_cours;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_cours() {
        return nom_cours;
    }

    public void setNom_cours(String nom_cours) {
        this.nom_cours = nom_cours;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate_cours() {
        return date_cours;
    }

    public void setDate_cours(Date date_cours) {
        this.date_cours = date_cours;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.nom_cours);
        hash = 83 * hash + Objects.hashCode(this.activite);
        hash = 83 * hash + Objects.hashCode(this.image);
        hash = 83 * hash + Objects.hashCode(this.date_cours);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cours other = (Cours) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nom_cours, other.nom_cours)) {
            return false;
        }
        if (!Objects.equals(this.activite, other.activite)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.date_cours, other.date_cours)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cours{" + "id=" + id + ", nom_cours=" + nom_cours + ", activite=" + activite + ", image=" + image + ", date_cours=" + date_cours + '}';
    }

   
    
}
