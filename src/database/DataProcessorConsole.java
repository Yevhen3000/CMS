/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import cms.Config;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class DataProcessorConsole extends AbstractDataProcessor  {
    /**
     * Class is dedicated for all main function 
     * for generating reprts and do on
     */
    
    private Config config;
    
    public DataProcessorConsole(Config appConfig) {
        /**
         * Constructer - initializes class
         * @param appConfig - an instance of Config class
         */        
        config = appConfig;
    }
   
    @Override
    public void GenerateLecturerReport(){
        
        ResultSet rs;
        int lineCount = 1;
        String formatString = "%-4s | %-20s | %-20s | %-45s | %-10s\n";
        //lecturer_name| lecturer_role | taught_modules | enrolled_students | classes_taught
        rs = config.db.getResultSet(queryLecturerReport);
        try {
            // Table header
            String headerString = String.format(formatString,
            "#",        
            "lecturer_name",
            "lecturer_role",
            "taught_modules",
            "classes_taught");
            String underline = "_".repeat(headerString.length()+ 2);
            System.out.print("Student Report:\n" + underline + "\n" + headerString + "\n" + underline + "\n" );
            
            while (rs.next()) {
     
                System.out.printf(formatString,
                lineCount,        
                rs.getString("lecturer_name"),
                rs.getString("lecturer_role"),
                rs.getString("taught_modules"),
                rs.getString("classes_taught"));
               
                lineCount++;
            }
            System.out.println(underline + "\nTotal records: " + (lineCount-1));
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }        
    }    
    
    @Override
    public void GenerateStudentReport(){
        
        ResultSet rs;
        int lineCount = 1;
        String formatString = "%-4s | %-45s | %-45s | %-8s | %-20s | %s";

        rs = config.db.getResultSet(queryStudentReport);
        try {
            // Table header
            String headerString = String.format(formatString,
            "#",        
            "student_name",
            "student_number",
            "course",
            "lecturer",
            "room");
            String underline = "_".repeat(headerString.length()+ 2);
            System.out.print("Student Report:\n" + underline + "\n" + headerString + "\n" + underline + "\n" );
            
            while (rs.next()) {
     
                System.out.printf(formatString,
                lineCount,        
                rs.getString("student_name"),
                rs.getString("student_number"),
                rs.getString("programme"),
                rs.getString("enrolled_modules"),
                rs.getString("completed_modules_with_grades"));
                //rs.getString("modules_to_repeat"));
                
                lineCount++;
            }
            System.out.println(underline + "\nTotal records: " + (lineCount-1));
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }        
    }
    
    @Override
    public void GenerateCourseReport(){
        
        ResultSet rs;
        int lineCount = 1;
        String formatString = "%-4s | %-45s | %-45s | %-8s | %-20s | %s";

        rs = config.db.getResultSet(queryCourseReport);
        try {
            // Table header
            String headerString = String.format(formatString,
            "#",        
            "module name",
            "course name",
            "students",
            "lecturer",
            "room");
            String underline = "_".repeat(headerString.length()+ 2);
            System.out.print("Course Report:\n" + underline + "\n" + headerString + "\n" + underline + "\n" );
            
            while (rs.next()) {
     
                System.out.printf(formatString,
                lineCount,        
                rs.getString("module_name"),
                rs.getString("course_name"),
                rs.getString("enrolled_students"),
                rs.getString("lecturer"),
                rs.getString("room"));
                
                lineCount++;
            }
            System.out.println(underline + "\nTotal records: " + (lineCount-1));
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }        
    }
}