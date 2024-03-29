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

public class DataProcessor {
    /**
     * Class is dedicated for all main function 
     * for generating reprts and do on
     */
    
    private Config config;
    
    public DataProcessor(Config appConfig) {
        /**
         * Constructer - initializes class
         */        
        config = appConfig;
    }
    
    public void GenerateCourseReport(){
        
        ResultSet rs;
        int lineCount = 1;
        
        String query =  "SELECT m.name AS module_name, " +
                        "c.name AS course_name, " +
                        "COUNT(e.student_id) AS enrolled_students," +
                        "l.fullname AS lecturer, " +
                        "m.room AS room " +
                        "FROM modules m " +
                        "JOIN courses c ON m.course_id = c.id " +
                        "JOIN lecturers l ON m.lecturer_id = l.id " +
                        "LEFT JOIN enrollments e ON m.id = e.course_id " +
                        "GROUP BY m.name, c.name, l.fullname, m.room";
                
        //config.db.getResultSet(query);
                
        rs = config.db.getResultSet(query);
        try {

                
                // Table header
                String headerString = String.format("%-4s | %-45s | %-45s | %-8s | %-20s | %s",
                "#",        
                "module name",
                "course name",
                "students",
                "lecturer",
                "room");
                String underline = "_".repeat(headerString.length()+ 2);
                System.out.print("Course Report:\n" + underline + "\n" + headerString + "\n" + underline + "\n" );
            
            while (rs.next()) {
              
                String module_name = rs.getString("module_name");
                String course_name = rs.getString("course_name");
                String enrolled_students = rs.getString("enrolled_students");
                String lecturer = rs.getString("lecturer");
                String room = rs.getString("room");
               
                System.out.printf("%-4s | %-45s | %-45s | %-8s | %-20s | %s%n",
                lineCount,        
                module_name,
                course_name,
                enrolled_students,
                lecturer,
                room);
                
                lineCount++;
            }
            System.out.println(underline + "\nTotal records: " + (lineCount-1));
          
            

        } catch (SQLException e) {
            System.out.println("Error, cannot execute Database query: " + e.getMessage());
        }        
        
    }
    
}
