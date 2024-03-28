/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import cms.Config;
import cms.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import users.User;
import users.UserPermissions;
import cms.Config.userType;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

/**
* Abstract Class is to implement console based menu UI
*/
public class MenuConsole extends AbstractMenu{
    
    private static List<String> menuList = new ArrayList<>();;
    private static List<String> menuAction = new ArrayList<>();;
    private UserPermissions userPerm;
    private int menuCounter = 1;
    
    public MenuConsole(Config appConfig){
       this.appConfig = appConfig;
    }
    
    @Override
    public void showMenu() {
         
        // Debug 
        User currentUser = new User(appConfig);
        currentUser.Add("admin", appConfig.getAdminPassword(), userType.ADMIN , false );
                
        if (currentUser == null) {
            ShowLogin();
            return;
        }
        
        if (menuList.isEmpty()) InitUserMenu();
        ShowUserMenu();
        //MenuMainLoop();
        
    }

    @Override
    public void hideMenu() {
        // Hide menu
    }
    
    public boolean ShowLogin(){
        boolean loginOK = false;
        
        System.out.println("=== Login menu ===");
        String login = getUserInput("Login:");
        String password = getUserInput("Password:");
        
        Security sec = new Security();
        if (login.equalsIgnoreCase("admin") && sec.verifyPassword(password,appConfig.getAdminPassword())) {
            loginOK = true;
        }
        
        return loginOK;
    }
    
    public void ShowUserMenu(){
        for (String item : menuList) {
            System.out.println(item);
        }
    }
    
    public void InitUserMenu(){
        if (currentUser == null) {
            System.out.println("Error: user is not authorized");
            return;
        }

        
        menuCounter = 1;
        userPerm = new UserPermissions(appConfig);
        
        MenuBuildItem("","#################### MENU ##################");
        MenuBuildItem("","#                                          #");
        MenuBuildItem("add_user","#             Add new user                   #");
        MenuBuildItem("modify_user","#             Modify user                   #");
        MenuBuildItem("delete_user","#             Delete user                   #");
        MenuBuildItem("can_change_own","#             Change credencials                   #");
        MenuBuildItem("report_all","#             Generate Course Report                   #");
        MenuBuildItem("report_all","#             Generate Student Report                   #");
        MenuBuildItem("report_all","#             Generate Lecturer Report                   #");
        MenuBuildItem("report_lecturer_own","#             Generate my Lecturer Report                   #");
        MenuBuildItem("","#  [0] Exit                                #");
        MenuBuildItem("","#                                          #");
        MenuBuildItem("","############################################");
   
    }
    
    private void MenuBuildItem(String userPermission, String menuTitle){
        if (userPermission.isEmpty()) {
            menuList.add(menuTitle);
        } else {
            if (userPerm.hasPermission(currentUser, userPermission)) {
                menuList.add("#  ["+menuCounter+"] " + menuTitle);
                menuAction.add(userPermission);
                menuCounter++;
            }            
        }
    }
    
    private String getUserInput(String message){
    /**
    * Takes a string from console
    * @param message - a promt string for user
    * @return Returns a string from console input
    */        
        
        String useInput = "";
        Scanner sc  = new Scanner(System.in);
        boolean bInputOk = false;
        while (!bInputOk) {
            if (!message.isEmpty()) System.out.println(message);
            try {
                useInput = sc.nextLine();
                bInputOk = true;
            } catch (Exception e){
                System.out.println("[getUserInput] Ops, something went wrong!");
            }
        }
        return useInput;
    }
}
