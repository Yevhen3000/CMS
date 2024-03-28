
/*
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 * Integrated CA: a sample of  database structure from CMS
 */

/*
* Create a new DataBase if there is still no
*/
CREATE DATABASE IF NOT EXISTS cms;

/*
* Choose new created DB to use
*/
USE cms;

/*
* Delete DB - for Debug only
* DROP DATABASE cms;
*/

/*
* Create table "courses" they hold modules
*/
CREATE TABLE IF NOT EXISTS courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    semester_number INT,
    UNIQUE KEY (name)
);

/*
* Create table "lecturers" they hold info of lecturer
*/
CREATE TABLE IF NOT EXISTS lecturers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
	type VARCHAR(255) NOT NULL,
	UNIQUE KEY (fullname)
);

/*
* Create table "modules" they hold info of subject
*/
CREATE TABLE IF NOT EXISTS modules (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    course_id INT,
    lecturer_id INT,
    room VARCHAR(8),
    semester INT,
	UNIQUE KEY (name)
);

/*
* Create table "students" they hold info of student
*/
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    number INT NOT NULL,
	UNIQUE KEY (fullname)
);

/*
* Create table "enrollments" it holds info of enrollments
*/
CREATE TABLE IF NOT EXISTS enrollments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    student_id INT,
    course_id INT
);

/*
* Create table "grades" it holds info of grades
*/
CREATE TABLE IF NOT EXISTS grades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    student_id INT,
	module_id INT,
    grade INT,
    CHECK (grade >= 0 AND grade <= 100)
);

/*
* Create table "feedbacks" it holds info of feedbacks
*/
CREATE TABLE IF NOT EXISTS feedbacks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    student_id INT,
    course_id INT,
	`text` TEXT
);


/*
* At this step I import all .csv files generated in the JAVA app
* (in logic order) first courses, 
* then modules, lectures, students, enrollments, grades, feedbacks
*/


/*
* Adding foreing keys to 'modules' table after import
* FOREIGN KEY (course_id) REFERENCES courses(id),
* FOREIGN KEY (lecturer_id) REFERENCES lecturers(id)
*/
ALTER TABLE modules ADD FOREIGN KEY (course_id) REFERENCES courses(id);
ALTER TABLE modules ADD FOREIGN KEY (lecturer_id) REFERENCES lecturers(id);

/*
* Adding foreing keys to 'enrollments' table after import
* FOREIGN KEY (student_id) REFERENCES students(id),
* FOREIGN KEY (course_id) REFERENCES courses(id)
*/
ALTER TABLE enrollments ADD FOREIGN KEY (student_id) REFERENCES  students(id);
ALTER TABLE enrollments ADD FOREIGN KEY (course_id) REFERENCES courses(id);

/*
* Adding foreing keys to 'grades' table after import
* FOREIGN KEY (student_id) REFERENCES students(id),
* FOREIGN KEY (module_id) REFERENCES modules(id)
*/
ALTER TABLE grades ADD FOREIGN KEY (student_id) REFERENCES  students(id);
ALTER TABLE grades ADD FOREIGN KEY (module_id) REFERENCES modules(id);

/*
* Adding foreing keys to 'feedbacks' table after import
* FOREIGN KEY (student_id) REFERENCES students(id),
* FOREIGN KEY (course_id) REFERENCES courses(id)
*/
ALTER TABLE feedbacks ADD FOREIGN KEY (student_id) REFERENCES  students(id);
ALTER TABLE feedbacks ADD FOREIGN KEY (course_id) REFERENCES courses(id);

/*
* Check if database 'cms' exists:
* If exists it returns a value greater than 0
* Usage of aggregator COUNT: 
* it calculates the total count of rows in the current SCHEMA
*/
SELECT COUNT(*) as count
FROM information_schema.SCHEMATA
WHERE SCHEMA_NAME = 'cms';
