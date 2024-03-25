
/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 * Integrated CA
 */


/**
* Create a new DataBase if there is still no
*/
CREATE DATABASE IF NOT EXISTS cms;

/**
* Delete DB for Debug only
* DROP DATABASE cms;
*/

/**
* Create table "courses"
*/
CREATE TABLE IF NOT EXISTS courses (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    hours INT NOT NULL,
    UNIQUE KEY (name)
)