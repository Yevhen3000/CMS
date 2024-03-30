/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;

import cms.Config;
import cms.Security;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* Class is to implement to manage users
*/
public class User {
    
    private Config config;
    
    public User(Config appConfig) {
        config = appConfig;
    }
    private String username;
    private String password;
    private Config.userType role;
    private int userId = 0;

    public void Add(String sUsername, String sPassword, Config.userType role ) {
        Security sec = new Security(config);
        config.db.makeQuery("INSERT INTO Users (name, password, type) VALUES ('"+sUsername+"','"+sec.hashPassword(sPassword)+"'," + resolveUserRole(role));
    }

    public void Delete(String sUsername) {
        if (sUsername.equalsIgnoreCase("admin")) {
            System.out.println("You cannot delete admin user");
            return;
        }
        config.db.makeQuery("DELETE FROM Users WHERE name ='"+sUsername+"'");
    }
    
    public boolean IsUserExists(String username, String password){
        ResultSet rs;
        int count = 0;
        
        String query = "SELECT id, type, COUNT(*) as count FROM users WHERE `name` = '"+ username +"' AND `password` = '"+password+"'";
        System.out.println(query);
        rs = config.db.getResultSet(query);
        try {
            rs.next();
            count = rs.getInt("count");
            userId = rs.getInt("id");
            this.username = username;
            role =  encodeUserRole(rs.getInt("type"));
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } 
        return (count != 0);
    }
    
    public String getUsername() {
        return username;
    }
    
    public int getUserId() {
        return userId;
    }    

    public Config.userType getRole() {
        return role;
    }    

    public void loadFromDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private int resolveUserRole(Config.userType userRole){
        switch(userRole){
            case Config.userType.ADMIN:
                return 99;
            case Config.userType.OFFICE:
                return 10;
            case Config.userType.LECTURER:
                return 20;
        }
        return 0;
    }
    
    private Config.userType encodeUserRole(int userType){
        switch(userType){
            case 99:
                return Config.userType.ADMIN;
            case 20:
                return Config.userType.OFFICE;
            case 10:
                return Config.userType.LECTURER;
        }
        return null;
    }    
    
}
