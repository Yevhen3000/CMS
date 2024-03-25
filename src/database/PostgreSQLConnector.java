/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class PostgreSQLConnector extends AbstractDatabaseConnector {
    
    private Connection conn;
    private String db_url;
    private String db_user;
    private String db_password;


     /**
     * Init PostgreSQL server variables
     *
     */
    
    public PostgreSQLConnector(String url, String user, String password){
        db_url = url;
        db_user = user;
        db_password = password;
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
        System.out.println("Connecting to PostgreSQL database...");
    }
    
     /**
     * ToDo:
     * Execute a query to the PostgreSQL server
     * 
     * throws Exception if execution error occurs
     */
    
    @Override
    public void makeQuery(String query) {
        System.out.println("Executing PostgreSQL query: " + query);
    }

     /**
     * ToDo:
     * Closing connction to the PostgreSQL server 
     *
     * throws Exception if disconnection error occurs
     */    
    
    @Override
    public void disconnect() {
        System.out.println("Disconnecting from PostgreSQL database...");
    }
    
}
