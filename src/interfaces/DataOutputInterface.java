/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* This Interface is dedicated to handle different types of output
* such as "A txt file", "A csv file", "Output to the NetBeans Console"
*/
public interface DataOutputInterface {
    void GenerateCourseReport();
    void GenerateStudentReport();
    void GenerateLecturerReport();
}
