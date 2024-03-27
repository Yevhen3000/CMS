/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cms;

import Server.MyHttpHandler;
import cms.Config.userType;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import database.DatabaseSetup;
import database.DatabaseController;
import interfaces.DatabaseInterface;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import users.User;
import users.UserPermissions;

import com.sun.net.httpserver.HttpHandler;
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
                
        /**
        * Load config from file "cms.config.json"
        * and parse it to use in the entire app
        */
        
        logToConsole("Loading config... ", false);
        Config appConfig = new Config();
        appConfig.Init();
        logToConsole("Done", true);
        
        /**
        * Connects to database depending on the config
        */
        logToConsole("Initializing database engine... ", false);
        DatabaseController dbCtrl = new DatabaseController( appConfig );
        logToConsole("Done", true);
        
        /**
        * Set up database and tables
        */                
        logToConsole("Setting up database and tables... ", false);
        DatabaseSetup dbSet = new DatabaseSetup(dbCtrl.db, appConfig );
        dbSet.Init();
        logToConsole("Done", true);

        User admin = new User(appConfig);
        admin.Add("admin", appConfig.getAdminPassword() ,userType.ADMIN, false);
        UserPermissions userPermissions = new UserPermissions(appConfig);
        
        
        
        
        // Create HttpServer which is listening to the given port on the given IP address
        HttpServer server;
        InetAddress localAddress;
        
        try {
            localAddress = InetAddress.getByName("127.0.0.1");
            server = HttpServer.create(new InetSocketAddress(localAddress, 8080), 0);
            HttpContext context = server.createContext("/", new MyHttpHandler());
            server.start();  
        } catch (Exception e) {
            System.out.println("Erro: server");
        }
             
        
        //System.out.println("add_user:" + userPermissions.hasPermission(admin, "add_user"));
        
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
