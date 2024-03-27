/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* This Class is to connect and work with a Postgre DataBase
* Implementation of abstract class
*/
public class PostgreSQLConnector extends AbstractDatabaseConnector {

     /**
     * Init PostgreSQL server variables
     *
     */
    public PostgreSQLConnector(String url, String user, String password, String databasename){
        db_url = url;
        db_user = user;
        db_password = password;
        db_database = databasename;
    }
    
     /**
     * ToDo: 
     * Tries to connect to a PostgreSQL server
     * And keeps a connection open
     *
     * throws Exception if connection error occurs
     */    
    @Override
    public void connect() {
        //String db_url = "jdbc:postgresql://localhost:5432/
        try {
            conn = DriverManager.getConnection(db_url, db_user, db_password);
            Statement stmt = conn.createStatement();
            stmt.execute("USE " + db_database + ";");            
        } catch (Exception e) {
            System.out.println("Error: cannot connect to PostgreSQL: " + e.getMessage());
        }
    }
    
     /**
     * Execute a query to the PostgreSQL server
     * 
     * throws Exception if execution error occurs
     */
    @Override
    public void makeQuery(String query) {
        if (conn==null) {
            System.out.println("Error: no active PostgreSQL connection");
            return;
        }
                    
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query + ";");
        } catch (Exception e) {
            System.out.println("Error: cannot execute PostgreSQL query: " + e.getMessage());
        } 
    }

    @Override
    public ResultSet getResultSet(String query){
     
        ResultSet rs = null;
        // To DO
        return rs;
    }
    
     /**
     * Closing connction to the PostgreSQL server 
     *
     * throws Exception if disconnection error occurs
     */    
    @Override
    public void disconnect() {
        try {
            if (conn!=null) conn.close();
        } catch (Exception e) {
            System.out.println("Error: cannot close PostgreSQL connection: " + e.getMessage());
        } 
    }
    
}
