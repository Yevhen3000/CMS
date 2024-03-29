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
import database.DataProcessor;

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
        appConfig.currentUser = new User(appConfig);
        //appConfig.currentUser.Add("admin", appConfig.getAdminPassword(), userType.ADMIN , false );
        //appConfig.currentUser.Add("lecturer", "pass", userType.LECTURER, true );
        appConfig.currentUser.Add("office", "pass", userType.OFFICE, true );
                
        if (appConfig.currentUser == null) {
            do {
                // Do while user enters
            } while(!UserLogin());
        }
        
        if (menuList.isEmpty()) InitUserMenu();
        MainMenuLoop();
    }

    @Override
    public void hideMenu() {
        // Hide menu
    }
    
    public boolean UserLogin(){
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
    
    public void MainMenuLoop(){
        String choice;
        boolean  inLoop = true;
        Scanner sc  = new Scanner(System.in);

        while (inLoop) {
            ShowUserMenu();
            try {
                if (sc.hasNext()) {
                    choice = sc.nextLine();
                    int menuValue = Integer.parseInt(choice);
                    if (menuValue==0) {
                        inLoop=false;
                    } else {
                       if (!MenuDispatcher(menuValue)) System.out.println("Invalid command!");
                    }
                }
            } catch (Exception e){
                System.out.println("Invalid command!");
            }                   
        }

    }
    
    private boolean MenuDispatcher(int menuValue){
        
        boolean returnVal = true;
        if (menuValue > menuAction.size()) return false;
        
        DataProcessor generator =  new DataProcessor(appConfig);
        
        switch (menuAction.get(menuValue-1)) {
            case "add_user":
                break;
            case "modify_user":
                break;
            case "delete_user":
                break;
            case "can_change_own":
                break;
            case "report_course":
                generator.GenerateCourseReport();
                break;
            case "report_student":
                break;
            case "report_lecturer":
                break;
            case "report_lecturer_own":
                break;
        }

        return returnVal;
    }
    
    private void ShowUserMenu(){
        for (String item : menuList) {
            System.out.println(item);
        }
        System.out.println("Please, enter the menu command number:"); 
    }
    
    public void InitUserMenu(){
        if (appConfig.currentUser == null) {
            System.out.println("Error: user is not authorized");
            return;
        }

        
        menuCounter = 1;
        userPerm = new UserPermissions(appConfig);
        
        MenuBuildItem("","#################### MENU ##################");
        MenuBuildItem("","#                                          #");
        MenuBuildItem("add_user","   Add new user                      #");
        MenuBuildItem("modify_user","   Modify user                       #");
        MenuBuildItem("delete_user","   Delete user                       #");
        MenuBuildItem("can_change_own","   Change credencials                #");
        MenuBuildItem("report_course","   Generate Course Report            #");
        MenuBuildItem("report_student","   Generate Student Report           #");
        MenuBuildItem("report_lecturer","   Generate Lecturer Report          #");
        MenuBuildItem("report_lecturer_own","   Generate my Lecturer Report       #");
        MenuBuildItem("","#  [0]   Exit                              #");
        MenuBuildItem("","#                                          #");
        MenuBuildItem("","############################################");
   
    }
    
    private void MenuBuildItem(String userPermission, String menuTitle){
        if (userPermission.isEmpty()) {
            menuList.add(menuTitle);
        } else {
            if (userPerm.hasPermission(appConfig.currentUser, userPermission)) {
                menuList.add("#  ["+menuCounter+"]" + menuTitle);
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
