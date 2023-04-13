/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Models.Planning;
import java.util.List;

/**
 *
 * @author MaherKaroui
 */
public interface PlanningInterface {
      public void addPlanning(Planning h);
    //liste : select
    public List<Planning> getAllPlannings() ;
    //getbyid: hotel
    public Planning GetPlanningById(int Id);
    //update hotel
    public void updatePlanning(Planning h);
    //delete hotel
    public void deletePlanning(int id);
    //Filter by name 
    public Planning filterByName(String nom);
 
    
}
