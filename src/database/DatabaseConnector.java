/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class DatabaseConnector {
    private final String DB_URL = "jdbc:mysql://localhost"; //    private final String DB_URL = "jdbc:mysql://localhost/bank";
    private final String USER = "pooa2024";
    private final String PASSWORD = "pooa2024";
    
    private Connection conn;
    
    public void connect(String dbUrl, String dbUser, String dbPassword){
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE bank;");
            System.out.println("Database sucessfully created;");
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: cannot connect to DataBase: " + e.getMessage());
        }
    }
}
