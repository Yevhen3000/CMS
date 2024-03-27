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

    /**
    * Init mySQL server variables
    *
    */
    public mySQLConnector(String url, String user, String password, String databasename){
        db_url = url;
        db_user = user;
        db_password = password;
        db_database = databasename;
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
        }
    }

     /**
     * Execute a query to the MySQL server
     *
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