/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import cms.Config;
import interfaces.DataOutputInterface;

/**
 *
 * @author Admin
 */
public class DataProcessorController {
    
    public DataOutputInterface SetDataOutput( Config appConfig ) {

        switch (appConfig.output_type.toLowerCase()) {
            case "console" -> {
                return new DataProcessorConsole(appConfig);
            }
//            case "file" -> {
//                return new DataProcessorFile(appConfig);
//            }
//            case "api" -> {
//                return new DataProcessorAPI(appConfig);
//            }            
        }
        return null;
    }
    
}
