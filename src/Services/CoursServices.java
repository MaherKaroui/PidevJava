/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Interfaces.CoursInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.Cours;
import Utils.MyConnection;

/**
 *
 * @author youssef
 */
public class CoursServices implements CoursInterface {
   Connection cnx = MyConnection.getInstance().getCnx();
    
   /* -----------ajouter hotel --------*/
    @Override
    public void addCours(Cours h) {
        String req = "INSERT INTO `cours`(`nom_cours`, `activite`, `date_cours`, `image`)"
                + "VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
           ps.setString(1, h.getNom_cours());
           ps.setString(2, h.getActivite());
           ps.setDate(3, h.getDate_cours());
           ps.setString(4, h.getImage());

            ps.executeUpdate();
            System.out.println("Nouveau Cours Ajoute avec success via prepared Statement!!!");
        } catch (SQLException ex) {
            Logger.getLogger(CoursServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   /* -----------afficher tous les hotels --------*/
    @Override
    public List<Cours> getAllCourss() {
       List<Cours> Courses = new ArrayList<>();
       String req = "SELECT * FROM cours";
        Statement st;
       try {
           st = cnx.createStatement();
           ResultSet rs = st.executeQuery(req);
           while(rs.next()){
              Cours h = new Cours() ; 
              h.setId(rs.getInt(1));
              h.setNom_cours(rs.getString(2));
              h.setActivite(rs.getString(3));
              h.setDate_cours(rs.getDate(4));
              h.setImage(rs.getString(5));
              Courses.add(h);
           }
       } catch (SQLException ex) {
           Logger.getLogger(CoursServices.class.getName()).log(Level.SEVERE, null, ex);
       }       
       return Courses;
    }
    /* -----------GETBYID HOTEL --------*/   

    @Override
    public Cours GetCoursById(int Id) {
     Cours h =new Cours();
     String req = "SELECT * FROM cours where `id` ="+Id+";";
     try {
      Statement st= cnx.createStatement();
       ResultSet rs = st.executeQuery(req); 
       while(rs.next()){
            h.setId(rs.getInt(1));
              h.setNom_cours(rs.getString(2));
              h.setActivite(rs.getString(3));
              h.setDate_cours(rs.getDate(4));
              h.setImage(rs.getString(5));
         }
       } catch (SQLException ex) {
           Logger.getLogger(CoursServices.class.getName()).log(Level.SEVERE, null, ex);
       }
    return h ;
    }
    /* -----------Modifier un  HOTEL --------*/
    @Override
    public void updateCours(Cours h) {
        String req = "UPDATE cours SET nom_cours = ?, activite = ?, date_cours = ?, image = ?"
                 +" WHERE id = ?";
       try {
           PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, h.getNom_cours());
           ps.setString(2, h.getActivite());
           ps.setDate(3, h.getDate_cours());
           ps.setString(4, h.getImage());
            ps.setInt(5, h.getId());
            ps.executeUpdate();
            System.out.println(" Cours Modifie avec success !!!");
       } catch (SQLException ex) {
           Logger.getLogger(CoursServices.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    /* -----------Supprimer un  HOTEL --------*/
  @Override
    public void deleteCours(int id) {
      String req = "DELETE FROM cours WHERE id = ?";
       try {
           PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println(" Cours Supprime avec success !!!");
       } catch (SQLException ex) {
           Logger.getLogger(CoursServices.class.getName()).log(Level.SEVERE, null, ex);
       }

    }
    /* -----------Filtre par nom d'HOTEL --------*/
    @Override
    public Cours filterByName(String nom) {
     Cours h =new Cours();
     String req = "SELECT * FROM cours where `nom_cours` ='"+nom+"';";
       try {
           Statement st= cnx.createStatement();
           ResultSet rs = st.executeQuery(req); 
             while(rs.next()){
             h.setId(rs.getInt(1));
              h.setNom_cours(rs.getString(2));
              h.setActivite(rs.getString(3));
              h.setDate_cours(rs.getDate(4));
              h.setImage(rs.getString(5));
         }
       } catch (SQLException ex) {
           Logger.getLogger(CoursServices.class.getName()).log(Level.SEVERE, null, ex);
       }     
     return h ;
    }
    
  
    
   
}
