/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import cms.Config;
import interfaces.MenuInterface;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */


/**
* This Class is to implement agile menu inisialization
*/
public class MenuController {
    
    public MenuInterface menu;
    
    public MenuController(Config appConfig) {
        
        String menu_type = appConfig.getMenuType().toLowerCase();
        
        menu = switch (menu_type) {
            case "console" -> new MenuConsole(appConfig);
            case "web" -> new MenuHttp(appConfig);
            default -> new MenuConsole(appConfig);
        };        
 
    }
}
