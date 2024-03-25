/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cms;

/* 
 * Downloaded library for JSON from here:
 * https://code.google.com/archive/p/json-simple/downloads 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class Config {
    
    private final String configFileName = "cms.config.json";
    private JSONObject jsonConfigObject;
    private String db_host;
    private String db_user;
    private String db_password;
    
    public String getDb_host() {
        return db_host;
    }

    public String getDb_user() {
        return db_user;
    }

    public String getDb_password() {
        return db_password;
    }

    public void Init(){
        
        String configJsonString = readFromFile(configFileName);
        
        try {
            JSONParser parser = new JSONParser();
            jsonConfigObject = (JSONObject) parser.parse(configJsonString);
            db_user = (String) jsonConfigObject.get("database_url");
            db_host = (String) jsonConfigObject.get("database_username");
            db_password = (String) jsonConfigObject.get("database_password");
        
        } catch (ParseException e) {
            System.out.println("Error: cannot parse config JSON: " + e.getMessage());
        }

    }
    
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
