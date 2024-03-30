/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import cms.Config;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public void GenerateLecturerReport(Config.outputFormat outputFormat){
        //The name of the lecturer | Their role | The modules | e number of students |  types of classes
        if (outputFormat==null) return;
        
        System.out.println("outputFormat:"+outputFormat);
        
        List<String> reportLines = GetLecturerReport();
        int lineCount = 1;
        String formatString = "%-4s | %-20s | %-20s | %-40s | %-40s | %-10s\n";
        try {
            // Table header
            String headerString = String.format(formatString,"#", "lecturer name", "lecturer role", "taught modules", "students", "classes taught");
            String underline = "_".repeat(headerString.length()+ 2);
            System.out.print("Student Report:\n" + underline + "\n" + headerString + underline + "\n" );
            
            //lecturer_name, lecturer_role, taught_modules, enrolled_students, classes_taught
            for (String line : reportLines) {
                String[] part = line.split(",");
                System.out.printf(formatString,lineCount,part[0],part[1],part[2],part[3],part[4]);
                lineCount++;
            }
            System.out.println(underline + "\nTotal records: " + (lineCount-1));
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }        
    }    
    
    private List<String> GetLecturerReport(){
        ResultSet rs;
        rs = config.db.getResultSet(queryLecturerReport);
        List<String> records = new ArrayList<>();
        try {
            while (rs.next()) {
                records.add(rs.getString("lecturer_name") + "," + 
                            rs.getString("lecturer_role") + "," + 
                            rs.getString("taught_modules") + "," + 
                            rs.getString("enrolled_students") + "," + 
                            rs.getString("classes_taught")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }           
        return records;
    }
        
    
    

    @Override
    public void GenerateLecturerReportOwn(Config.outputFormat  outputFormat){
        
        ResultSet rs;
        int lineCount = 1;
        String formatString = "%-4s | %-20s | %-20s | %-85s | %-10s\n";
        
        String queryLecturerReportOwn = "SELECT l.fullname AS lecturer_name, l.role AS lecturer_role, " +
            "GROUP_CONCAT(DISTINCT m.name ORDER BY m.name ASC SEPARATOR ', ') AS taught_modules, " +
            "COUNT(DISTINCT e.student_id) AS enrolled_students, " +
            "GROUP_CONCAT(DISTINCT m.room ORDER BY m.room ASC SEPARATOR ', ') AS classes_taught " +
        "FROM lecturers l " +
        "JOIN modules m ON l.id = m.lecturer_id " +
        "LEFT JOIN enrollments e ON m.id = e.course_id " +
        "WHERE l.id = " + config.currentUser.getUserId() + " GROUP BY l.fullname, l.role;";

        rs = config.db.getResultSet(queryLecturerReportOwn);
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
    public void GenerateStudentReport(Config.outputFormat  outputFormat){
        
        ResultSet rs;
        int lineCount = 1;
        String formatString = "%-4s | %-20s | %-6s | %-45s | %-20s | %s\n";

        rs = config.db.getResultSet(queryStudentReport);
        try {
            // Table header
            String headerString = String.format(formatString, "#", "student name", "number", "course", "modules", "grades");
            String underline = "-".repeat(headerString.length()+ 2);
            System.out.print("Student Report:\n" + underline + "\n" + headerString + underline + "\n" );
            
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
    public void GenerateCourseReport(Config.outputFormat  outputFormat){
        
        ResultSet rs;
        int lineCount = 1;
        String formatString = "%-4s | %-45s | %-45s | %-8s | %-20s | %s\n";

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
            System.out.print("Course Report:\n" + underline + "\n" + headerString + underline + "\n" );
            
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