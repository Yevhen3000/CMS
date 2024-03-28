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


public class DatabaseController {
    /**
    * This Class is to work with database engine more agile
    * It initializes a connection to DataBase choosen in config
    */    

    public DatabaseInterface db;

    public DatabaseController( Config appConfig) {
        
        /**
        * Creates database object depending on which 
        * database engin shoosen in the Config file
        * By default mySQL server type
        * 
        * @param Config is an instance of app config
        * @return Returns nothing
        */
        
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

    public void setActiveDatabase(String databaseName){
        /**
        * Sets active database
        * 
        * @param databaseName (String) is a database name to set active
        * @return Returns nothing
        */
        db.makeQuery("USE " + databaseName + ";");
    }
    
    public void DatabaseStop(){
        /**
        * Closes connection to database
        * 
        * @param No params
        * @return Returns nothing
        */        
        db.disconnect();
    }
    
}
