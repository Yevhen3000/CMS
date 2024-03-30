/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cms;

/* 
 * Downloaded library for JSON from here:
 * https://code.google.com/archive/p/json-simple/downloads 
 */
import interfaces.DatabaseInterface;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import users.User;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* This Class is to handle config data of this app
*/
public class Config {
    
    private final String configFileName = "cms.config.json";
    private JSONObject jsonConfigObject;
    private String db_host;
    private String db_user;
    private String db_password;
    private String db_type;
    private String database_name;
    private String admin_password;
    
    public String http_server_ip;
    public int http_server_port;
    
    public DatabaseInterface db;
    public boolean verbose_output;
        
    public String output_type;
    public String input_type;
    public User currentUser = null;
    
    public String security_salt = "[B@186f8716";
    
    public enum outputType {
        FILE,
        CONSOLE,
        REST
    }
    
    public enum outputFormat {
        CONSOLE,
        TXT,
        CVS,
        JSON
    }
    
    public enum userType {
        ADMIN,
        OFFICE,
        LECTURER
    }
    
    public String[] output_formats = {"console", "txt", "cvs", "json"};
    
    public String[] admin_permissions = {"list_users", "add_user", "modify_user", "delete_user", "can_change_own" };
    public String[] office_permissions = {"report_course", "report_student", "report_lecturer","can_change_own"};
    public String[] lecturer_permissions = {"report_lecturer_own", "can_change_own"};

    /**
     *
     * @return admin password
     */
    public String getAdminPassword() {
        return admin_password;
    }
    
    /**
     *
     * @return  databasename
     */
    public String getDbName() {
        return database_name;
    }
    
    public String getUrlHost() {
        return db_host;
    }

    /**
     *
     * @return database user
     */
    public String getUser() {
        return db_user;
    }

    /**
     *
     * @return database password
     */
    public String getPassword() {
        return db_password;
    }
    
    /**
     *
     * @return database type
     */
    public String getDbType() {
        return db_type;
    }    

    /**
     *
     * @return menu type
     */
    public String getMenuType() {
        return input_type;
    }   
    
     /**
     * Parses a content of JSON string and initializes variables 
     * for DB connction
     *
     */
    public void Init(){
        
        String configJsonString = readFromFile(configFileName);

        try {
            JSONParser parser = new JSONParser();
            jsonConfigObject = (JSONObject) parser.parse(configJsonString);
            db_host = (String) jsonConfigObject.get("database_url");
            db_user = (String) jsonConfigObject.get("database_username");
            db_password = (String) jsonConfigObject.get("database_password");
            database_name = (String) jsonConfigObject.get("database_name");
            db_type = (String) jsonConfigObject.get("database_type");
            
            input_type = (String) jsonConfigObject.get("input_type");
            output_type = (String) jsonConfigObject.get("output_type");
            admin_password = (String) jsonConfigObject.get("admin_password");
            
            http_server_ip = (String) jsonConfigObject.get("http_server_ip");
            
            String temp_int = (String) jsonConfigObject.get("http_server_port");
            http_server_port =  Integer.parseInt(temp_int);
            
        } catch (ParseException e) {
            System.out.println("Error: cannot parse config JSON: " + e.getMessage());
        }

    }
    
    /**
     * Gets a content from the confog file with path as fileName parameter
     *
     * @return a content of file
     * @throws IOException if a file access error occurs
     */
    
    private String readFromFile(String fileName){
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error: cannot read config file: " + e.getMessage());
        }

        return contentBuilder.toString();        
    }


    
}
