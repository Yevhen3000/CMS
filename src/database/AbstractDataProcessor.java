/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import cms.Config;
import interfaces.DataOutputInterface;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

abstract public class AbstractDataProcessor implements DataOutputInterface{

    public String queryCourseReport =  "SELECT m.name AS module_name, c.name AS course_name, COUNT(e.student_id) AS enrolled_students, \n" +
                    "	   l.fullname AS lecturer, m.room AS room\n" +
                    "FROM modules m \n" +
                    "JOIN courses c ON m.course_id = c.id\n" +
                    "JOIN lecturers l ON m.lecturer_id = l.id\n" +
                    "LEFT JOIN enrollments e ON m.id = e.course_id\n" +
                    "GROUP BY m.name, c.name, l.fullname, m.room;"; 
    
    public String queryStudentReport =  "SELECT s.fullname AS student_name, s.number AS student_number, c.name AS programme,\n" +
                    "    GROUP_CONCAT(DISTINCT m_enrolled.name ORDER BY m_enrolled.name ASC SEPARATOR ', ') AS enrolled_modules,\n" +
                    "    GROUP_CONCAT(DISTINCT CONCAT(m_completed.name, ' (Grade: ', g.grade, ')') ORDER BY m_completed.name ASC SEPARATOR ', ') AS completed_modules_with_grades,\n" +
                    "    GROUP_CONCAT(DISTINCT m_repeated.name ORDER BY m_repeated.name ASC SEPARATOR ', ') AS modules_to_repeat\n" +
                    "FROM students s\n" +
                    "JOIN enrollments e ON s.id = e.student_id\n" +
                    "JOIN modules m_enrolled ON e.course_id = m_enrolled.course_id\n" +
                    "JOIN courses c ON m_enrolled.course_id = c.id\n" +
                    "LEFT JOIN grades g ON e.student_id = g.student_id AND m_enrolled.id = g.module_id\n" +
                    "LEFT JOIN (\n" +
                    "        SELECT e.student_id, m.name,\n" +
                    "            MAX(CASE WHEN g.grade >= 40 THEN m.name END) AS completed_module,\n" +
                    "            MAX(CASE WHEN g.grade < 40 THEN m.name END) AS repeated_module\n" +
                    "        FROM enrollments e\n" +
                    "        JOIN modules m ON e.course_id = m.course_id\n" +
                    "        LEFT JOIN grades g ON e.student_id = g.student_id AND m.id = g.module_id\n" +
                    "        GROUP BY e.student_id, m.name\n" +
                    "    ) AS grades ON s.id = grades.student_id\n" +
                    "LEFT JOIN modules m_completed ON grades.completed_module = m_completed.name\n" +
                    "LEFT JOIN modules m_repeated ON grades.repeated_module = m_repeated.name\n" +
                    "GROUP BY s.fullname, s.number, c.name";
    
    public String queryLecturerReport =  "SELECT l.fullname AS lecturer_name, l.role AS lecturer_role,\n" +
                    "    GROUP_CONCAT(DISTINCT m.name ORDER BY m.name ASC SEPARATOR ', ') AS taught_modules,\n" +
                    "    COUNT(DISTINCT e.student_id) AS enrolled_students,\n" +
                    "    GROUP_CONCAT(DISTINCT m.room ORDER BY m.room ASC SEPARATOR ', ') AS classes_taught\n" +
                    "FROM lecturers l\n" +
                    "JOIN modules m ON l.id = m.lecturer_id\n" +
                    "LEFT JOIN enrollments e ON m.id = e.course_id\n" +
                    "GROUP BY l.fullname, l.role;"; 
    
    @Override
    public void GenerateCourseReport(Config.outputFormat  outputFormat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void GenerateStudentReport(Config.outputFormat  outputFormat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void GenerateLecturerReport(Config.outputFormat  outputFormat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void GenerateLecturerReportOwn(Config.outputFormat  outputFormat){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
