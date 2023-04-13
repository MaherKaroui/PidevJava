/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify;

import Models.Cours;
import Models.Planning;
import Services.CoursServices;
import Services.PlanningServices;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class Sportify {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
           Date date = new Date(2022, 23, 2);
         Connection cnx = MyConnection.getInstance().getCnx();
         CoursServices c = new CoursServices();
         PlanningServices p = new PlanningServices();
         
         c.getAllCourss().forEach(System.out::println);
         Cours C1 = new Cours(122,"test", "tunis","dsf",date);
         
         //c.addCours(C1);
         
         Planning P1 = new Planning(date,C1);
        
         
         //p.addPlanning(P1);
         p.getAllPlannings().forEach(System.out::println);
        // TODO code application logic here
    }
    
}
