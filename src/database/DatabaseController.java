/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import cms.Config;
import interfaces.DatabaseInterface;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* This Class is to work with database engine more agile
* It initializes a connection to DataBase choosen in config
*/
public class DatabaseController {

    public DatabaseInterface db;

    /**
    * Init Database connection depends on config
    * By default mySQL server type
    * 
    */    
    public DatabaseController( Config appConfig) {
        
        String DataBaseEngine = appConfig.getDbType().toLowerCase();
        
        switch (DataBaseEngine) {
          case "mysql": 
              db = new mySQLConnector(appConfig.getUrlHost(), appConfig.getUser(), appConfig.getPassword(), appConfig.getDbName());
              break;

          case "postgresql": 
              db = new PostgreSQLConnector(appConfig.getUrlHost(), appConfig.getUser(), appConfig.getPassword(), appConfig.getDbName());
              break;

          default:     
              db = new mySQLConnector(appConfig.getUrlHost(), appConfig.getUser(), appConfig.getPassword(), appConfig.getDbName());
        }

        db.connect();
    }

    // Set current database
    public void setActiveDatabase(String databaseName){
        db.makeQuery("USE " + databaseName + ";");
    }
    
    // Close connection to the database
    public void DatabaseStop(){
        db.disconnect();
    }
    
}
