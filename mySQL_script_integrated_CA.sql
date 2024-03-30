
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


/*
* Get all students, their number and course title they enrolled
*/
SELECT s.fullname, s.number as student_number, c.name as course_name
FROM enrollments e
JOIN students s on e.student_id = s.id
JOIN courses c on e.course_id = c.id
GROUP BY s.number, c.name, s.fullname;


/*
* Get students and their module name who didnt pass (grade < 40%)
*/
SELECT s.fullname, s.number as student_number, m.name as module_name, g.grade
FROM grades g
JOIN students s on g.student_id = s.id
JOIN modules m on g.module_id = m.id
WHERE g.grade < 40
GROUP BY s.number, m.name, s.fullname, g.grade
ORDER BY g.grade ASC;


/*
* Get average grades per each module
*/
SELECT m.name as module_name, AVG(g.grade) AS avg_grade
FROM grades g
JOIN modules m on g.module_id = m.id
GROUP BY m.name, g.grade;

/*
* Set student's grade to 78% and while setting no one else can write to database
*/
LOCK TABLES grades WRITE;
UPDATE grades SET grade = 78 WHERE student_id = 67 AND module_id = 97;
UNLOCK TABLES;
COMMIT;

SELECT 
    s.fullname AS student_name,
    s.number AS student_number,
    c.name AS programme,
    GROUP_CONCAT(DISTINCT m_enrolled.name ORDER BY m_enrolled.name ASC SEPARATOR ', ') AS enrolled_modules,
    GROUP_CONCAT(DISTINCT CONCAT(m_completed.name, ' (Grade: ', g.grade, ')') ORDER BY m_completed.name ASC SEPARATOR ', ') AS completed_modules_with_grades,
    GROUP_CONCAT(DISTINCT m_repeated.name ORDER BY m_repeated.name ASC SEPARATOR ', ') AS modules_to_repeat
FROM 
    students s
JOIN 
    enrollments e ON s.id = e.student_id
JOIN 
    modules m_enrolled ON e.course_id = m_enrolled.course_id
JOIN 
    courses c ON m_enrolled.course_id = c.id
LEFT JOIN 
    grades g ON e.student_id = g.student_id AND m_enrolled.id = g.module_id
LEFT JOIN 
    (
        SELECT 
            e.student_id,
            m.name,
            MAX(CASE WHEN g.grade >= 40 THEN m.name END) AS completed_module,
            MAX(CASE WHEN g.grade < 40 THEN m.name END) AS repeated_module
        FROM 
            enrollments e
        JOIN 
            modules m ON e.course_id = m.course_id
        LEFT JOIN 
            grades g ON e.student_id = g.student_id AND m.id = g.module_id
        GROUP BY 
            e.student_id, m.name
    ) AS grades ON s.id = grades.student_id
LEFT JOIN 
    modules m_completed ON grades.completed_module = m_completed.name
LEFT JOIN 
    modules m_repeated ON grades.repeated_module = m_repeated.name
GROUP BY 
    s.fullname, s.number, c.name;
    
    
    SELECT 
    l.fullname AS lecturer_name,
    l.role AS lecturer_role,
    GROUP_CONCAT(DISTINCT m.name ORDER BY m.name ASC SEPARATOR ', ') AS taught_modules,
    COUNT(DISTINCT e.student_id) AS enrolled_students,
    GROUP_CONCAT(DISTINCT m.room ORDER BY m.room ASC SEPARATOR ', ') AS classes_taught
FROM 
    lecturers l
JOIN 
    modules m ON l.id = m.lecturer_id
LEFT JOIN 
    enrollments e ON m.id = e.course_id
GROUP BY 
    l.fullname, l.role;
    
    SELECT id, COUNT(*) as count FROM users WHERE `name` = 'admin' AND `password` = '9HOHOreTq+ISOQGcFPYnbQ==:dBUlbyqsYl6x3zKyliWOiTVTN6Qi2NjVw5r+DmG3w1Y=';
    
    UPDATE users set `password` = 'W0JAMTg2Zjg3MTY=:UNCxu94RX8w4I1hGSoxo6MbpQCwBlLywQMJ/MMvXGsU=' WHERE name='admin';
    
    INSERT INTO users (name, password, type) VALUES ('admin','9HOHOreTq+ISOQGcFPYnbQ==:dBUlbyqsYl6x3zKyliWOiTVTN6Qi2NjVw5r+DmG3w1Y=',99);
    
    
SELECT l.fullname AS lecturer_name, l.role AS lecturer_role,
    GROUP_CONCAT(DISTINCT m.name ORDER BY m.name ASC SEPARATOR ', ') AS taught_modules,
    COUNT(DISTINCT e.student_id) AS enrolled_students,
    GROUP_CONCAT(DISTINCT m.room ORDER BY m.room ASC SEPARATOR ', ') AS classes_taught
FROM lecturers l
JOIN modules m ON l.id = m.lecturer_id
LEFT JOIN enrollments e ON m.id = e.course_id
WHERE l.id = 2
GROUP BY l.fullname, l.role;		