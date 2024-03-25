/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cms;

import database.mySQLConnector;
import interfaces.DatabaseConnector;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class CMS {

    public static void main(String[] args) {
                
        // Get config from file "cms.config.json"
        Config appConfig = new Config();
        appConfig.Init();
                
        // Init mySQL connection. In order to use Postgre database use PostgreSQLConnector (implementation is needed)
        DatabaseConnector mysql = new mySQLConnector(appConfig.getUrlHost(), appConfig.getUser(), appConfig.getPassword());
        mysql.connect();
        
        // Create a new DataBase if there is still no
        mysql.makeQuery("CREATE DATABASE IF NOT EXISTS " +appConfig.getDbName());
        
        //Security salt = new Security();
        //System.out.println(salt.hashPassword(appConfig.getPassword()) );
        
        // Close connection to the database
        mysql.disconnect();
        
    }
    
}
