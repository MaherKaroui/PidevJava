/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Interfaces.PlanningInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.Planning;
import Models.Cours;
import Utils.MyConnection;


public class PlanningServices implements PlanningInterface {
   Connection cnx = MyConnection.getInstance().getCnx();
   CoursServices c = new CoursServices();
    
   /* -----------ajouter planning --------*/
    @Override
    public void addPlanning(Planning h) {
        String req = "INSERT INTO `planning`(`date_cours`, `cours_id`)"
                + "VALUES (?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
           
           ps.setDate(1, h.getDate_cours());
           
         System.out.println("this cours id " + h.getCours_id().getId());
           ps.setInt(2,h.getCours_id().getId());

            ps.executeUpdate();
            System.out.println("Nouveau Planning Ajoute avec success via prepared Statement!!!");
        } catch (SQLException ex) {
            Logger.getLogger(PlanningServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   /* -----------afficher tous les planning --------*/
    @Override
   public List<Planning> getAllPlannings() {
    List<Planning> plannings = new ArrayList<>();
    String query = "SELECT * FROM planning";
    try {
        Statement statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Planning planning = new Planning();
            planning.setId(resultSet.getInt("id"));
            planning.setDate_cours(resultSet.getDate("date_cours"));
            int coursId = resultSet.getInt("cours_id");
            CoursServices coursServices = new CoursServices();
            Cours cours = coursServices.GetCoursById(coursId);
            planning.setCours_id(cours);
            plannings.add(planning);
        }
        statement.close();
    } catch (SQLException ex) {
        Logger.getLogger(PlanningServices.class.getName()).log(Level.SEVERE, null, ex);
    }
    return plannings;
}

    /* -----------GETBYID planning --------*/   
/* @Override
    public List<PlanningFilmSalle> afficherPlanning() {
        List<PlanningFilmSalle> plannings = new ArrayList<>();
        String req = "SELECT * from `planningfilmsalle`";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                PlanningFilmSalle p = new PlanningFilmSalle();
                p.setID_planning(rs.getInt(1));
                p.setFilm(fs.GetFilmById(rs.getInt(2)));
                p.setSalle(ss.GetSalleByID(rs.getInt(3)));
                p.setDatediffusion(rs.getDate(4));
                p.setHeurediffusion(rs.getString(5));

                
                plannings.add(p);
                
            }
        }catch (SQLException ex) {
            Logger.getLogger(PlanningService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plannings;
    }*/
    @Override
    public Planning GetPlanningById(int Id) {
     Planning h =new Planning();
     String req = "SELECT * FROM Planning where `id` ="+Id+";";
     try {
      Statement st= cnx.createStatement();
       ResultSet rs = st.executeQuery(req); 
       while(rs.next()){
            h.setId(rs.getInt(1));
              h.setDate_cours(rs.getDate(2));
             int id = rs.getInt(3);
                System.out.println(id);
                Cours cours = c.GetCoursById(id);
                h.setCours_id(cours);
         }
       } catch (SQLException ex) {
           Logger.getLogger(PlanningServices.class.getName()).log(Level.SEVERE, null, ex);
       }
    return h ;
    }
    /* -----------Modifier un  planning --------*/
    @Override
    public void updatePlanning(Planning h) {
        String req = "UPDATE planning SET  date_cours = ? , cours_id = ? "
                 +" WHERE id = ?";
       try {
           PreparedStatement ps = cnx.prepareStatement(req);
              ps.setDate(1, h.getDate_cours());
           ps.setInt(2, h.getCours_id().getId());
            ps.setInt(3, h.getId());
            ps.executeUpdate();
            System.out.println(" Planning Modifie avec success !!!");
       } catch (SQLException ex) {
           Logger.getLogger(PlanningServices.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    /* -----------Supprimer un  planning --------*/
  @Override
    public void deletePlanning(int id) {
      String req = "DELETE FROM planning WHERE id = ?";
       try {
           PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println(" Planning Supprime avec success !!!");
       } catch (SQLException ex) {
           Logger.getLogger(PlanningServices.class.getName()).log(Level.SEVERE, null, ex);
       }

    }
    /* -----------Filtre par nom d'cours --------*/
    @Override
    public Planning filterByName(String nom) {
     Planning h =new Planning();
     String req = "SELECT * FROM cours where `nom_cours` ='"+nom+"';";
       try {
           Statement st= cnx.createStatement();
           ResultSet rs = st.executeQuery(req); 
             while(rs.next()){
           h.setId(rs.getInt(1));
              h.setDate_cours(rs.getDate(2));
             int id = rs.getInt(3);
                System.out.println(id);
                Cours cours = c.GetCoursById(id);
                h.setCours_id(cours);
         }
       } catch (SQLException ex) {
           Logger.getLogger(PlanningServices.class.getName()).log(Level.SEVERE, null, ex);
       }     
     return h ;
    }
   
    
   
}
