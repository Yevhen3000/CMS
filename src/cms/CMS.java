/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cms;

import Menu.MenuController;
import database.DataProcessorController;
import database.DatabaseSetup;
import database.DatabaseController;
import interfaces.DataOutputInterface;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class CMS {
    /**
    * This Class is an entry point of this app
    */

    private static Config app;
    
    public static void main(String[] args) {
                
        /*
        * Load config from file "cms.config.json"
        * and parse it to use in the entire app
        */
        System.out.print("Loading config... ");
        app = new Config();
        app.Init();
        app.verbose_output = true;
        logToConsole("Done", true);
        
        /*
        * Connects to database depending on the config
        */
        logToConsole("Initializing database engine... ", false);
        DatabaseController dbCtrl = new DatabaseController( app );
        logToConsole("Done", true);
        
        /*
        * Set up database and tables
        */                
        logToConsole("Setting up database and tables... ", false);
        DatabaseSetup dbSet = new DatabaseSetup(app );
        dbSet.Init();
        logToConsole("Done", true);

        /*
        * Launch application menu.
        */         
        MenuController menuCtrl = new MenuController(app);
        menuCtrl.menu.showMenu();

        // --- DEBUG REPORTS --------
        //DataOutputInterface generator = new DataProcessorController().SetDataOutput(app);
        //Config.outputFormat MenuOutputType = Config.outputFormat.CONSOLE;
        //generator.GenerateCourseReport(MenuOutputType);
        //generator.GenerateStudentReport(MenuOutputType);
        //generator.GenerateLecturerReport(MenuOutputType);
        //generator.GenerateLecturerReportOwn(MenuOutputType);
        
//      For Debug        
//      MenuConsole mm = new MenuConsole(app);
//      mm.ShowUserList();
  
        /*
        * Shutdown our app
        */                        
        dbCtrl.DatabaseStop();
        if (app.verbose_output) System.out.println("Exit.");
        
    }
    
    private static void logToConsole(String message, boolean newLine){
        /**
        * Output to console
        * @param String message - a message to print out
        * @param boolean newLine - new line flag
        */ 
        if (app.verbose_output) {
            if (newLine) {
                System.out.println(message);
            } else {
                System.out.print(message);
            }
        }
    }
    
}
