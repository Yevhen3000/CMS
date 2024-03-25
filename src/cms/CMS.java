/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cms;

import interfaces.DatabaseConnector;
import database.DatabaseSetup;
import database.DatabaseController;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class CMS {

    public static DatabaseConnector db;
    
    public static void main(String[] args) {
                
        /**
        * Load config from file "cms.config.json"
        * and parse it to use in the entire app
        * 
        */
        Config appConfig = new Config();
        appConfig.Init();

        /**
        * Connects to database depending on the config
        * 
        */        
        DatabaseController dbCtrl = new DatabaseController( appConfig );
        
        /**
        * Set up database and tables
        * 
        */                
        DatabaseSetup dbSet = new DatabaseSetup(dbCtrl.db, appConfig );

        /**
        * Shutdown our app
        * 
        */                        
        dbCtrl.DatabaseStop();
        System.out.println("Done.");
        
    }
    
}
