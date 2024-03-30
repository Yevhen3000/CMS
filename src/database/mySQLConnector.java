/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import cms.Config;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* This Class is to connect and work with a mySQL DataBase
* Implementation of abstract class
*/
public class mySQLConnector extends AbstractDatabaseConnector {

    private Config config;

    /**
    * Init mySQL server variables
    * @param application config
    * @return Nothing
    */     
    public mySQLConnector(Config appConfig){
 
        config = appConfig;
        db_url = config.getUrlHost();
        db_user = config.getUser();
        db_password = config.getPassword();
        db_database = config.getDbName();
    }
    
     /**
     * Tries to connect to a mySQL server
     * And keeps a connection open
     *
     * throws Exception if connection error occurs
     */ 
    @Override
    public void connect() {
       
        try {
            conn = DriverManager.getConnection(db_url, db_user, db_password);
        } catch (Exception e) {
            System.out.println("Error: cannot connect to DataBase: " + e.getMessage());
            System.out.println("Critical Error. Exiting...");
            System.exit(0);
        }
    }

     /**
     * Execute a query to the MySQL server
     * @param query -  a mySQL query string
     * throws Exception if execution error occurs
     */  
    @Override
    public void makeQuery(String query){
      
        
        if (conn==null) {
            System.out.println("Error: no active MySQL connection");
            return;
        }
                    
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query + ";");
        } catch (Exception e) {
            System.out.println("Error: cannot execute query: " + e.getMessage());
        }      
    }
    
    @Override
    public ResultSet getResultSet(String query){
        
        ResultSet rs = null;
                    
        try {
            Statement stmt = conn.createStatement();
            //stmt.execute(query + ";");
            rs = stmt.executeQuery(query + ";");
            
        } catch (Exception e) {
            System.out.println("Error: cannot execute query: " + e.getMessage());
        }    
        return rs;
    }    

     /**
     * Closing connction to the MySQL server 
     * 
     * throws Exception if disconnection error occurs
     */
    
    @Override
    public void disconnect() {
        try {
            if (conn!=null) conn.close();
        } catch (Exception e) {
            System.out.println("Error: cannot close connection: " + e.getMessage());
        }              
    }
        
}