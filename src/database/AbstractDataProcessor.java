/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import interfaces.DataOutputInterface;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

abstract public class AbstractDataProcessor implements DataOutputInterface{

    public String queryCourseReport =  "SELECT m.name AS module_name, " +
                    "c.name AS course_name, " +
                    "COUNT(e.student_id) AS enrolled_students," +
                    "l.fullname AS lecturer, " +
                    "m.room AS room " +
                    "FROM modules m " +
                    "JOIN courses c ON m.course_id = c.id " +
                    "JOIN lecturers l ON m.lecturer_id = l.id " +
                    "LEFT JOIN enrollments e ON m.id = e.course_id " +
                    "GROUP BY m.name, c.name, l.fullname, m.room"; 
    
    public String queryStudentReport =  "SELECT s.fullname AS student_name, s.number AS student_number, c.name AS programme, " +
    "GROUP_CONCAT(DISTINCT m_enrolled.name ORDER BY m_enrolled.name ASC SEPARATOR ', ') AS enrolled_modules, " +
    "GROUP_CONCAT(DISTINCT CONCAT(m_completed.name, ' (Grade: ', g.grade, ')') ORDER BY m_completed.name ASC SEPARATOR ', ') AS completed_modules_with_grades, " +
    "GROUP_CONCAT(DISTINCT m_repeated.name ORDER BY m_repeated.name ASC SEPARATOR ', ') AS modules_to_repeat " +
    "FROM students s" +
    "JOIN enrollments e ON s.id = e.student_id " +
    "JOIN modules m_enrolled ON e.course_id = m_enrolled.course_id " +
    "JOIN courses c ON m_enrolled.course_id = c.id " +
    "LEFT JOIN grades g ON e.student_id = g.student_id AND m_enrolled.id = g.module_id" +
    "LEFT JOIN ( " +
    "SELECT e.student_id, m.name, " +
    "MAX(CASE WHEN g.grade >= 40 THEN m.name END) AS completed_module, " +
    "MAX(CASE WHEN g.grade < 40 THEN m.name END) AS repeated_module " +
    "FROM enrollments e " +
    "JOIN modules m ON e.course_id = m.course_id " +
    "LEFT JOIN grades g ON e.student_id = g.student_id AND m.id = g.module_id " +
    "GROUP BY e.student_id, m.name ) AS grades ON s.id = grades.student_id " +
    "LEFT JOIN modules m_completed ON grades.completed_module = m_completed.name " +
    "LEFT JOIN modules m_repeated ON grades.repeated_module = m_repeated.name " +
    "GROUP BY s.fullname, s.number, c.name";
    
    public String queryLecturerReport =  "SELECT l.fullname AS lecturer_name, l.role AS lecturer_role, " +
    "GROUP_CONCAT(DISTINCT m.name ORDER BY m.name ASC SEPARATOR ', ') AS taught_modules," +
    "COUNT(DISTINCT e.student_id) AS enrolled_students, " +
    "GROUP_CONCAT(DISTINCT m.room ORDER BY m.room ASC SEPARATOR ', ') AS classes_taught " +
    "FROM lecturers l " +
    "JOIN modules m ON l.id = m.lecturer_id " +
    "LEFT JOIN enrollments e ON m.id = e.course_id " +
    "GROUP BY l.fullname, l.role"; 
    
    @Override
    public void GenerateCourseReport() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void GenerateStudentReport() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void GenerateLecturerReport() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
