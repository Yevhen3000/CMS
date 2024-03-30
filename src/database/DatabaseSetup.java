/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import cms.Config;
import interfaces.DatabaseInterface;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */


/**
* This Class to create a DataBase, Tables
* if there are still no
*/
public class DatabaseSetup {
    
    private DatabaseInterface database;
    private Config config;
    private boolean isNewDatabase = false;
    
    private String[] queries = {
            "CREATE TABLE IF NOT EXISTS courses (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "semester_number INT," +
                "UNIQUE KEY (name)" +
                ");",

            "CREATE TABLE IF NOT EXISTS lecturers (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "fullname VARCHAR(255) NOT NULL," +
                "role VARCHAR(255) NOT NULL," +
                "type VARCHAR(255) NOT NULL," +
                "UNIQUE KEY (fullname)" +
                ");",          
        
            "CREATE TABLE IF NOT EXISTS modules (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "course_id INT," +
                "lecturer_id INT," +
                "room VARCHAR(8)," +
                "semester INT," +
                "UNIQUE KEY (name)," +
                "FOREIGN KEY (course_id) REFERENCES courses(id)," +
                "FOREIGN KEY (lecturer_id) REFERENCES lecturers(id)"+
                ");",
            
            "CREATE TABLE IF NOT EXISTS students (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "fullname VARCHAR(255) NOT NULL," +
                "number INT NOT NULL," +
                "UNIQUE KEY (fullname)" +
                ");",
  
            "CREATE TABLE IF NOT EXISTS enrollments (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "`dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "student_id INT," +
                "course_id INT," +
                "FOREIGN KEY (student_id) REFERENCES students(id)," +
                "FOREIGN KEY (course_id) REFERENCES courses(id)" +
                ");",
            
            "CREATE TABLE IF NOT EXISTS grades (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "`dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "student_id INT," +
                "module_id INT," +
                "grade INT," +
                "CHECK (grade >= 0 AND grade <= 100)," +
                "FOREIGN KEY (student_id) REFERENCES students(id)," +
                "FOREIGN KEY (module_id) REFERENCES modules(id)" +
                ");",
            
            "CREATE TABLE IF NOT EXISTS feedbacks (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "`dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "student_id INT," +
                "course_id INT," +
                "`text` TEXT," +
                "FOREIGN KEY (student_id) REFERENCES students(id)," +
                "FOREIGN KEY (course_id) REFERENCES courses(id)" +
                ");",
            
            "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(64) NOT NULL," +
                "password VARCHAR(128) NOT NULL," +
                "type INT NOT NULL DEFAULT '0'," +
                "UNIQUE KEY (name)" +
                ");"
    
    };
    
    /**
    * Create a new DataBase if there is still no
    * and all tables we need to work with
    * 
     * @param appConfig -current config 
    */
    public DatabaseSetup( Config appConfig ){
        database = appConfig.db;
        config = appConfig;
    }
    
    /**
     * Create DataBase
     */
    public void Init(){
        createDataBase();
    }

    /**
    * Create Database we need to work with
    * Create tables
    * if database is new:
    * - add all required data
    * - populate with random generated observations
    */ 
    private void createDataBase() {
        
        if (!isDataBaseExists(config.getDbName() )) {
            database.makeQuery("CREATE DATABASE IF NOT EXISTS " + config.getDbName());
            isNewDatabase = true;
        }
        database.makeQuery("USE " + config.getDbName());
        createTables();
        
        if (isNewDatabase) {
            insertRequiredData();
            ObservationsMaker dataFiller = new ObservationsMaker();
            dataFiller.generateData();
        }
        
    }

    /**
    * Create all tables we need to work with
    */    
    private void createTables() {
        
        for (String oneQ: queries) {
            database.makeQuery(oneQ);
        }
       
    }

    /**
    * Checks for database with DataBaseName for existance
    */     
    private boolean isDataBaseExists(String DataBaseName) {
        ResultSet rs;
        int count = 0;
                
        rs = database.getResultSet(" SELECT COUNT(*) as count FROM information_schema.SCHEMATA WHERE SCHEMA_NAME = '"+ DataBaseName +"';");
        try {
            rs.next();
            count = rs.getInt("count");
        } catch (SQLException e) {
            System.out.println("Error, cannot execute Database query: " + e.getMessage());
        } 
        return (count != 0);
    }

    /**
    * Add to database all required data
    */    
    private void insertRequiredData(){
        // Add admin
        database.makeQuery("INSERT INTO users (name, password, type) VALUES ('admin','" + config.getAdminPassword() + "',99)");
        
    }
    
}
