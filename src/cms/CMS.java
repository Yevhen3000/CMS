/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cms;

import Menu.MenuController;
import Server.HttpServer;
import cms.Config.userType;
import database.DatabaseSetup;
import database.DatabaseController;
import database.ObservationsMaker;
import interfaces.DatabaseInterface;
import users.User;
import users.UserPermissions;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* This Class is an entry point of this app
*/
public class CMS {

    /*
    * Variable holds object of database engin
    */
    public static DatabaseInterface db;

    /*
    * Variable holds wether to show user process 
    */
    private static boolean verbose_output = true;
    
    public static void main(String[] args) {
                
        /*
        * Load config from file "cms.config.json"
        * and parse it to use in the entire app
        */
        
        logToConsole("Loading config... ", false);
        Config appConfig = new Config();
        appConfig.Init();
        logToConsole("Done", true);
        
        /*
        * Connects to database depending on the config
        */
        logToConsole("Initializing database engine... ", false);
        DatabaseController dbCtrl = new DatabaseController( appConfig );
        logToConsole("Done", true);
        
        /*
        * Set up database and tables
        */                
        logToConsole("Setting up database and tables... ", false);
        DatabaseSetup dbSet = new DatabaseSetup(appConfig );
        dbSet.Init();
        logToConsole("Done", true);

        
        MenuController menuCtrl = new MenuController(appConfig);
        menuCtrl.menu.showMenu();
        
        //System.out.println("add_user:" + userPermissions.hasPermission(admin, "add_user"));
        
        //dbCtrl.setActiveDatabase(appConfig.getDbName());
        //dbSet.createTables();

        //Security sec = new Security();
        //System.out.println(sec.hashPassword("java"));
        //System.out.println(sec.verifyPassword("java",appConfig.getAdminPassword()));

        
        /*
        * ToDO:
        * Launch app menu.
        */                                
        
        /*
        * Shutdown our app
        */                        
        dbCtrl.DatabaseStop();
        System.out.println("Exit.");
        
    }
    
    private static void logToConsole(String message, boolean newLine){
        if (verbose_output) {
            if (newLine) {
                System.out.println(message);
            } else {
                System.out.print(message);
            }
        }
    }
    
}
