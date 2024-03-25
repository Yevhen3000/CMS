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

public class DatabaseSetup {
    
    private DatabaseInterface database;
    private Config config;
    
    private String[] queries = {
            "CREATE TABLE IF NOT EXISTS courses (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(255) NOT NULL," +
            "hours INT NOT NULL," +
            "UNIQUE KEY (name))",
        
    
    };
    
    /**
    * Create a new DataBase if there is still no
    * and all tables we need to work with
    * 
    */
    public DatabaseSetup(DatabaseInterface db, Config appConfig ){
        database = db;
        config = appConfig;
        
        createDataBase();
        
    }

    /**
    * Create Database we need to work with
    */ 
    private void createDataBase() {
        database.makeQuery("CREATE DATABASE IF NOT EXISTS " +config.getDbName());    
    }

    /**
    * Create all tables we need to work with
    */    
    public void createTables() {
        
        for (String oneQ: queries) {           
            
            //System.out.println(oneQ);
            database.makeQuery(oneQ);
        }
       
    }
    
}
