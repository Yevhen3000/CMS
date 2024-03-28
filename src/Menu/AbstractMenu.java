/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import cms.Config;
import interfaces.MenuInterface;
import users.User;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* Abstract Class is to implement different menu UI
*/
public class AbstractMenu implements MenuInterface {

    public String currentItem;
    public User currentUser = null;
    public Config appConfig;
    
    @Override
    public void showMenu() {
        // Show menu
    }

    @Override
    public void hideMenu() {
        // Hide menu
    }        
}
