/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/*
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* This Class is to handle all security features of this app
*/
public class Security {

    private Config config;
    
    public Security(Config appConfig) {
       config = appConfig;
    }
    
    /*
    * Hashes a password with dynamic salt
    * 
    */
    public String hashPassword(String password) {
        try {
            byte[] salt = config.security_salt.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error hashPassword: " + e.getMessage());
            return null;
        }
    }
    
    /*
    * Hashes a password with dynamic salt
    * 
    */
    public String hashPasswordDynamic(String password) {
        try {
            byte[] salt = generateSalt();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error hashPassword: " + e.getMessage());
            return null;
        }
    }    
    

    public boolean verifyPassword(String inputPassword, String storedPassword) {
    /**
    * Verifyies a password with hashed one
    * @param String inputPassword - plain string of password
    * @param String storedPassword - hashed password string to compare with
    * @return true if password are the same otherwise false
    */        
        try {
            String[] parts = storedPassword.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            System.out.println("salt:"+salt);
            byte[] storedHashedPassword = Base64.getDecoder().decode(parts[1]);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] inputHashedPassword = md.digest(inputPassword.getBytes());
            return MessageDigest.isEqual(storedHashedPassword, inputHashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error verifyPassword: " + e.getMessage());
            return false;
        }
    }

    /**
    * Creates a salt
    * 
    */
    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }
    
    
}
