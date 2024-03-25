/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import interfaces.DatabaseConnector;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

abstract class AbstractDatabaseConnector implements DatabaseConnector{
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
