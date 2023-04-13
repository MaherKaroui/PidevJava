/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;

/**
 *
 * @author MaherKaroui
 */
public class Planning {
    
    private int id;
    private Date date_cours;
    private Cours cours;

    public Planning(int id, Date date_cours, Cours cours) {
        this.id = id;
        this.date_cours = date_cours;
        this.cours = cours;
    }

    public Planning(Date date_cours, Cours Cours) {
        this.date_cours = date_cours;
        this.cours = Cours;
    }


    public Planning() {
       
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_cours() {
        return date_cours;
    }

    public void setDate_cours(Date date_cours) {
        this.date_cours = date_cours;
    }

    public Cours getCours_id() {
        return cours;
    }

    public void setCours_id(Cours cours) {
        this.cours = cours;
    }

    

   
    @Override
    public String toString() {
        return "Planning{" + "id=" + id + ", date_cours=" + date_cours + ", cours=" + cours + '}';
    }
    
    
}
