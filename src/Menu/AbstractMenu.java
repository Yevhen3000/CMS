/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import interfaces.MenuInterface;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* Abstract Class is to implement different menu UI
*/
public class AbstractMenu implements MenuInterface {

    public String current_item;
       
    
    @Override
    public void showMenu() {
        // Show menu
    }

    @Override
    public void hideMenu() {
        // Hide menu
    }        
}
