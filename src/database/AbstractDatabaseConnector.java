/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import interfaces.DatabaseInterface;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

abstract class AbstractDatabaseConnector implements DatabaseInterface{
    
    public Connection conn;
    public String db_url;
    public String db_user;
    public String db_password;
    public String db_database;
    public String database_type;
       
    
   @Override
    public void connect() {
        // Connection to DB
    }

    @Override
    public void makeQuery(String query) {
        // Query to the DB
    }    
    
    @Override
    public void disconnect() {
        // Closing connection to DB
    }
    
}
