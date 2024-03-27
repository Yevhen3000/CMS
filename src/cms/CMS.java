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

/**
* This Class is an entry point of this app
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
        dbSet.Init();

        //dbCtrl.setActiveDatabase(appConfig.getDbName());
        //dbSet.createTables();

        //Security sec = new Security();
        //System.out.println(sec.hashPassword("java"));
        //System.out.println(sec.verifyPassword("java",appConfig.getAdminPassword()));
        
        
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
