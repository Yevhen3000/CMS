/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cms;

import database.DatabaseConnector;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class CMS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                
        // Init config file
        Config appConfig = new Config();
        appConfig.Init();
                
        // Init DataBase connection
        DatabaseConnector dbServer = new DatabaseConnector();
        dbServer.connect(appConfig.getUrlHost(), appConfig.getUser(), appConfig.getPassword());
        
        // Create a new DataBase if there is still no
        dbServer.makeQuery("CREATE DATABASE IF NOT EXISTS " +appConfig.getDbName());
        
    }
    
}
