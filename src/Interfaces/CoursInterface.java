/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Models.Cours;
import java.util.List;

/**
 *
 * @author MaherKaroui
 */
public interface CoursInterface {
      public void addCours(Cours h);
    //liste : select
    public List<Cours> getAllCourss() ;
    //getbyid: hotel
    public Cours GetCoursById(int Id);
    //update hotel
    public void updateCours(Cours h);
    //delete hotel
    public void deleteCours(int id);
    //Filter by name 
    public Cours filterByName(String nom);
    
    
    
}
