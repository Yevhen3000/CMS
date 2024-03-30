/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;

import cms.Config;
import cms.Security;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* Class is to implement to manage users
*/
public class User {
    
    private Config app;
    private String username;
    private Config.userType role;
    private int userId = 0;
    private UserPermissions userPerm;
    private Security sec;

    public User(Config appConfig) {
        app = appConfig;
        userPerm = new UserPermissions(app);
        sec = new Security(app);
    }
    
    public void Add(String sUsername, String sPassword, Config.userType role ) {
        String query = "INSERT INTO users (name, password, type) VALUES ('"+sUsername+"','"+sec.hashPassword(sPassword)+"'," + resolveUserRole(role) + ")";
        app.db.makeQuery(query);
    }

    public void Delete(String sUsername) {
        if (sUsername.equalsIgnoreCase("admin")) {
            System.out.println("You cannot delete `admin` user");
            return;
        }
        if (!userPerm.hasPermission(app.currentUser, "delete_user")) {
            System.out.println("You have not rights to delete");
            return;            
        }
        app.db.makeQuery("DELETE FROM Users WHERE name ='"+sUsername+"'");
        if (username.equalsIgnoreCase(sUsername) ) userId = 0;
    }
    
    public void Modify(String oldUsername, String newUsername, String newPassword) {
        app.db.makeQuery("UPDATE users set `name` = '"+newUsername+"', `password` = '"+sec.hashPassword(newPassword)+"' WHERE name='"+oldUsername+"'");
    }

    public void ModifySuper(String oldUsername, String newUsername, String newPassword, Config.userType role) {
        app.db.makeQuery("UPDATE users set `name` = '"+newUsername+"', `password` = '"+sec.hashPassword(newPassword)+"', type = "+resolveUserRole(role)+"  WHERE name='"+oldUsername+"'");
    }
    
    public boolean Authenticate(String username, String password){
        ResultSet rs;
        int count = 0;
        
        String query = "SELECT id, type, COUNT(*) as count FROM users WHERE `name` = '"+ username +"' AND `password` = '"+password+"'";
        rs = app.db.getResultSet(query);
        try {
            rs.next();
            count = rs.getInt("count");
            this.userId = rs.getInt("id");
            this.username = username;
            this.role =  encodeUserRole(rs.getInt("type"));
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } 
        return (count != 0);
    }
    
    public void LogOut(){
        this.userId = 0;
        this.username = "";
        this.role =  null;
    }

    public List<String> GetUserList(){
        ResultSet rs;
        List<String> userList = new ArrayList<>();
        rs = app.db.getResultSet("SELECT name FROM users ORDER BY name ASC");
        try {
            while (rs.next()) {
                userList.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return userList;
    } 
    
    public String getUsername() {
        return username;
    }
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int newUserID) {
        userId = newUserID;
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
    
    public Config.userType StringToUserRole(String userType){
        switch(userType){
            case "admin":
                return Config.userType.ADMIN;
            case "office":
                return Config.userType.OFFICE;
            case "lecturer":
                return Config.userType.LECTURER;
        }
        return null;
    }       
    
}
