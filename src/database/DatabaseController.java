/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import cms.Config;
import interfaces.DatabaseConnector;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class DatabaseController {

    public DatabaseConnector db;

    /**
    * Init Database connection depends on config
    * By default mySQL server type
    * 
    */    
    public DatabaseController( Config appConfig) {
        
        if (appConfig.getDbType().equalsIgnoreCase("mySQL") ) {
            db = new mySQLConnector(appConfig.getUrlHost(), appConfig.getUser(), appConfig.getPassword(), appConfig.getDbName());
        } else if (appConfig.getDbType().equalsIgnoreCase("PostgreSQL")) {
            db = new PostgreSQLConnector(appConfig.getUrlHost(), appConfig.getUser(), appConfig.getPassword(), appConfig.getDbName());
        } else {
            db = new mySQLConnector(appConfig.getUrlHost(), appConfig.getUser(), appConfig.getPassword(), appConfig.getDbName());
        }
        db.connect();

    }
    
    // Close connection to the database
    public void DatabaseStop(){
        db.disconnect();
    }
    
}
