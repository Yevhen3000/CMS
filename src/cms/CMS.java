/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cms;

import database.DatabaseSetup;
import database.DatabaseController;
import interfaces.DatabaseInterface;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class CMS {

    public static DatabaseInterface db;
    
    public static void main(String[] args) {
                
        /**
        * Load config from file "cms.config.json"
        * and parse it to use in the entire app
        */
        Config appConfig = new Config();
        appConfig.Init();

        /**
        * Connects to database depending on the config
        */        
        DatabaseController dbCtrl = new DatabaseController( appConfig );
        
        /**
        * Set up database and tables
        */                
        DatabaseSetup dbSet = new DatabaseSetup(dbCtrl.db, appConfig );

        dbCtrl.DatabaseSet(appConfig.getDbName());
        
        dbSet.createTables();

        /**
        * ToDO:
        * Fill tables with observations
        */

        
        /**
        * ToDO:
        * Launch app menu.
        */                                
        
        /**
        * Shutdown our app
        */                        
        dbCtrl.DatabaseStop();
        System.out.println("Done.");
        
    }
    
}
