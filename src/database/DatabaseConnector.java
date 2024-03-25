/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class DatabaseConnector {
   
    private Connection conn;
    
    public void connect(String dbUrl, String dbUser, String dbPassword){
        try {
            
            System.out.println(dbUrl);
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            //conn.close();
        } catch (Exception e) {
            System.out.println("Error: cannot connect to DataBase: " + e.getMessage());
        }
    }
    
    public void makeQuery(String query){
        
        if (conn==null) {
            System.out.println("Error: no active DataBase connection");
            return;
        }
                    
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query + ";");
        } catch (Exception e) {
            System.out.println("Error: cannot execute query: " + e.getMessage());
        }      
    }
        
}