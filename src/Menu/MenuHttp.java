/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import cms.Config;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* Abstract Class is to implement Web based menu UI
*/
public class MenuHttp extends AbstractMenu{
    
    public MenuHttp(Config appConfig){
        this.appConfig = appConfig;
    }    
    
    @Override
    public void showMenu() {
        // Todo: Show menu
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void hideMenu() {
        // Todo: Hide menu
        throw new UnsupportedOperationException("Not supported yet.");
    }      
}
        //        Expose the server to: http://127.0.0.1:8080/
        //        HttpServer srv = new HttpServer(appConfig);
        //        srv.start();