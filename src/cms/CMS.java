/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cms;

import Menu.MenuConsole;
import Menu.MenuController;
import database.DatabaseSetup;
import database.DatabaseController;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class CMS {
    /**
    * This Class is an entry point of this app
    */

    private static Config appConfig;
    
    public static void main(String[] args) {
                
        /*
        * Load config from file "cms.config.json"
        * and parse it to use in the entire app
        */
        System.out.print("Loading config... ");
        appConfig = new Config();
        appConfig.Init();
        appConfig.verbose_output = true;
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

        /*
        * Launch application menu.
        */         
        MenuController menuCtrl = new MenuController(appConfig);
        //menuCtrl.menu. .ShowUserList();
        //menuCtrl.menu.showMenu();
        
        MenuConsole mm = new MenuConsole(appConfig);
        mm.ShowUserList();
  
        /*
        * Shutdown our app
        */                        
        dbCtrl.DatabaseStop();
        if (appConfig.verbose_output) System.out.println("Exit.");
        
    }
    
    private static void logToConsole(String message, boolean newLine){
        if (appConfig.verbose_output) {
            if (newLine) {
                System.out.println(message);
            } else {
                System.out.print(message);
            }
        }
    }
    
}
