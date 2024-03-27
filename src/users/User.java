/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;

import cms.Config;
import cms.Config.userType;
import cms.Security;

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
    private userType role;

    public void Add(String username, String password, userType role, boolean hashUserPassword ) {
        this.username = username;
        this.role = role;
        
        if (hashUserPassword) {    
            Security security = new Security();
            this.password = security.hashPassword(password);
        } else {
            this.password = password;
        }
    }

    public String getUsername() {
        return username;
    }

    public userType getRole() {
        return role;
    }    

    public void loadFromDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
