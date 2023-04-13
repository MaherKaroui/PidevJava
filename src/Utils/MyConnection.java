/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 
 */
public class MyConnection {
    //DB PARAM
    static final String URL ="jdbc:mysql://localhost:3306/sport";
    static final String USER ="root";
    static final String PASSWORD ="";
    //var
    private Connection cnx;
    static MyConnection instance;
     //const
    //2
    private MyConnection(){
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion etablie avec success");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public Connection getCnx() {
        return cnx;
    }
     //3
    public static MyConnection getInstance() {
        if(instance == null)
            instance = new MyConnection();
        
        return instance;
    }
}
