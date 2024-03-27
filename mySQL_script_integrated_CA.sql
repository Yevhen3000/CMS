
/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 * Integrated CA: a sample of  database structure fro CMS
 */

/**
* Create a new DataBase if there is still no
*/
CREATE DATABASE IF NOT EXISTS cms;

/**
* Choose new created DB to use
*/
USE cms;

/**
* Delete DB - for Debug only
* DROP DATABASE cms;
*/

/**
* Create table "courses" they hold modules
*/
CREATE TABLE IF NOT EXISTS courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    semester_number INT,
    UNIQUE KEY (name)
);

/**
* Create table "lecturers" they hold info of lecturer ))
*/
CREATE TABLE IF NOT EXISTS lecturers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
	type VARCHAR(255) NOT NULL,
	UNIQUE KEY (fullname)
);

/**
* Create table "modules" they hold info of subject
*/
CREATE TABLE IF NOT EXISTS modules (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    course_id INT,
    lecturer_id INT,
    room VARCHAR(8),
    semester INT,
	UNIQUE KEY (name),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (lecturer_id) REFERENCES lecturers(id)
);

/**
* Create table "students" they hold info of student
*/
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    number INT NOT NULL,
	UNIQUE KEY (fullname)
);

/**
* Create table "enrollments" it holds info of enrollments
*/
CREATE TABLE IF NOT EXISTS enrollments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    student_id INT,
    course_id INT,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

/**
* Create table "grades" it holds info of grades
*/
CREATE TABLE IF NOT EXISTS grades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    student_id INT,
	module_id INT,
    grade INT,
    CHECK (grade >= 0 AND grade <= 100),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (module_id) REFERENCES modules(id)
);

/**
* Create table "feedbacks" it holds info of feedbacks
*/
CREATE TABLE IF NOT EXISTS feedbacks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    student_id INT,
    course_id INT,
	`text` TEXT,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

/**
* Check if database 'cms' exists:
* If exists it returns a value greater than 0
* Usage of aggregator COUNT: 
* it calculates the total count of rows in the current SCHEMA
*/
SELECT COUNT(*) as count
FROM information_schema.SCHEMATA
WHERE SCHEMA_NAME = 'cms';
