/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cms;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class CMS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Config currentConfig = new Config();
        currentConfig.Init();
        currentConfig.getDb_host();
    }
    
}
