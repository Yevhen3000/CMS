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
import database.DataProcessorController;
import interfaces.DataOutputInterface;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */


public class MenuConsole extends AbstractMenu{
    /**
    * Implements console based menu UI
    */

    private static List<String> menuList = new ArrayList<>();;
    private static List<String> menuAction = new ArrayList<>();;
    private UserPermissions userPerm;
    private int menuCounter = 1;
    
    private Config.outputFormat outFormat;
       
    /**
    * Initialization
    * @param appConfig (Config) - object of current application config 
    */
    public MenuConsole(Config appConfig){
       this.app = appConfig;
       app.currentUser = new User(app);
    }
    
    /**
    * Launches main menu
    */ 
    @Override
    public void showMenu() {
        StartMenu();
    }

    /**
    * Exiting the application
    */ 
    @Override
    public void hideMenu() {
        System.out.println("Exiting...");
        System.exit(0);
    }
    
    /**
    * Checks for UserId if it is 0 then user must login
    * Otherwise it shows user menu
    */
    public void StartMenu(){
       
        if (app.currentUser.getUserId() == 0) {
            if (!UserLogin()) hideMenu();
        }
        
        InitUserMenu();
        MainMenuLoop();        
    }
    
    /**
    * Login menu. it checks login and password with database
    * @return false if auth failed or true if login ok
    */
    public boolean UserLogin(){
        
        boolean loginOK = false;
        Security sec = new Security(app);
        
        do{
            System.out.println("=========== Login menu ==========");
            System.out.println("[Enter 'exit' to quit application]");
            String login = getUserInput("Login:");
            if(login.equalsIgnoreCase("exit")) return false;
            String password = getUserInput("Password:");
            if(password.equalsIgnoreCase("exit")) return false;
            
            boolean ok = app.currentUser.Authenticate(login,sec.hashPassword(password));
            if (!ok) {
                System.out.println("Login or password are incorrect!");
            } else {
                loginOK = true;
                System.out.println("Welcome," + login + " ["+app.currentUser.getRole()+"]");
            }
        } while(!loginOK);
        return true;
    }
    
    /**
    * Shows a list of all users in database
    */
    public void ShowUserList(){
         
        List<String> userList = app.currentUser.GetUserList();
        int count = 1;
        System.out.print("====== Available users =======");
        for (String one : userList) {
            System.out.println();
            String lineString = String.format("%-1s %-3s %-20s %-1s", "| ", count, one, " |");
            System.out.print(lineString);
            count++;
        }
        System.out.println("\n==============================");
    }
    
    /**
    * Deletes a user by user's name
    */  
    public void UserDelete(){
       
        ShowUserList();
        String login = getUserInput("Enter user name to delete:");
        app.currentUser.Delete(login);
    }

   /**
    * Creates a new user
    */  
    public void NewUser(){
        
        System.out.println("======== New user ========");
        String login = getUserInput("Enter new user login:");
        if (login.isEmpty()) {
            System.out.println("Error: invalid user name");
            return;
        }        
        String password = getUserInput("Enter new user password:");
        if (password.isEmpty()) {
            System.out.println("Error: invalid password");
            return;
        }                
        String role = getUserInput("Enter new user role [admin or office or lecturer]:");
        Config.userType userRole = app.currentUser.StringToUserRole(role);
        if (userRole==null) {
            System.out.println("Error: invalid user role");
            return;
        }
        app.currentUser.Add(login, password, userRole);
    }
    
    /**
    * Makes modifications to user: login and password. 
    * In this method user can't change his role
    */ 
    public void ModifyUserOwn(){
           
        System.out.println("======== Modify Own ========");
        String login = getUserInput("Enter your own new login:");
        if (login.isEmpty()) {
            System.out.println("Error: invalid user name");
            return;
        }
       
        String password = getUserInput("Enter your own new password:");
        if (password.isEmpty()) {
            System.out.println("Error: invalid password");
            return;
        }                
        app.currentUser.Modify(app.currentUser.getUsername(), login, password);
        
        app.currentUser.LogOut();
        StartMenu();
    }    

    /**
    * Makes modifications to user: login and password. 
    * In this method user CAN change his role as well
    */
    public void ModifyUser(){
                    
        System.out.println("======== Modify User ========");
        String loginOld = getUserInput("Enter user's current login:");
        if (loginOld.isEmpty()) {
            System.out.println("Error: invalid user name");
            return;
        }
        
        String loginNew = getUserInput("Enter user's new login:");
        if (loginNew.isEmpty()) {
            System.out.println("Error: invalid user name");
            return;
        } 
        
        String password = getUserInput("Enter new user's password:");
        if (password.isEmpty()) {
            System.out.println("Error: invalid password");
            return;
        }                
        
        String role = getUserInput("Enter new user role [admin or office or lecturer]:");
        Config.userType userRole = app.currentUser.StringToUserRole(role);
        if (userRole==null) {
            System.out.println("Error: invalid user role");
            return;
        }
        app.currentUser.ModifySuper(loginOld, loginNew, password, userRole);
        
        if (app.currentUser.getUsername().equalsIgnoreCase(loginOld) ) {
            app.currentUser.LogOut();
            StartMenu();
        }
    }     
    
    /**
     * Main menu method 
     */
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
                        app.currentUser.LogOut();
                        StartMenu();
                    } else {
                       if (!MenuDispatcher(menuValue)) System.out.println("Invalid command!");
                    }
                }
//                if (app.currentUser.getUserId() == 0) {
//                    inLoop=false;
//                    StartMenu();
//                }
            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }                   
        }

    }
    
    /**
    * Shows a output type format of the report
    */    
    private Config.outputFormat MenuOutputType(){
         
        int count = 1;
        System.out.print("======= Format output ========");
        for (String oneFormat : app.output_formats) {
            System.out.println();
            String lineString = String.format("%-1s %-3s %-20s %-1s", "| ", count, oneFormat.toUpperCase(), " |");
            System.out.print(lineString);
            count++;
        }
        System.out.println("\n==============================");
        String outType = getUserInput("Enter output type:");
        if (outType.isEmpty()) {
            System.out.println("Error: invalid type");
            return null;
        }                
          
        return StringOutputFormat(outType);
    }

    private Config.outputFormat StringOutputFormat(String outputFormat){
        switch(outputFormat.toLowerCase()){
            case "console":
                return Config.outputFormat.CONSOLE;
            case "txt":
                return Config.outputFormat.TXT;
            case "cvs":
                return Config.outputFormat.CVS;
            case "json":
                return Config.outputFormat.JSON;                
        }
        return null;
    }

    /**
    * Upon user's input executes certain methods
    * @param menuValue - a number user has entered
    */     
    private boolean MenuDispatcher(int menuValue){
                   
        boolean returnVal = true;
        if (menuValue > menuAction.size()) return false;
        
        DataOutputInterface generator = new DataProcessorController().SetDataOutput(app);
        
        switch (menuAction.get(menuValue-1)) {
            
            case "list_users":
                ShowUserList();
                break;            
            case "add_user":
                NewUser();
                break;
            case "modify_user":
                ModifyUser();
                break;
            case "delete_user":
                UserDelete();
                break;
            case "can_change_own":
                ModifyUserOwn();
                break;
            case "report_course":
                generator.GenerateCourseReport(MenuOutputType());
                break;
            case "report_student":
                generator.GenerateStudentReport(MenuOutputType());
                break;
            case "report_lecturer":
                generator.GenerateLecturerReport(MenuOutputType());
                break;                
            case "report_lecturer_own":
                generator.GenerateLecturerReportOwn(MenuOutputType());
                break;
        }

        return returnVal;
    }
    
    /**
    * Shows a list of menu
    */    
    private void ShowUserMenu(){
       
        for (String item : menuList) {
            System.out.println(item);
        }
        System.out.println("Please, enter the menu command number:"); 
    }
    
    /**
    * Creates main menu acording to user's rights 
    */   
    public void InitUserMenu(){
     
        menuList.clear();
        menuAction.clear();
        if (app.currentUser == null) {
            System.out.println("Error: user is not authorized");
            return;
        }
       
        menuCounter = 1;
        userPerm = new UserPermissions(app);
        
        MenuBuildItem("","#################   MENU  ##################");
        MenuBuildItem("","#                                          #");
        MenuBuildItem("list_users","   List all users                    #");
        MenuBuildItem("add_user","   Add new user                      #");
        MenuBuildItem("modify_user","   Modify user                       #");
        MenuBuildItem("delete_user","   Delete user                       #");
        MenuBuildItem("can_change_own","   Change credencials                #");
        MenuBuildItem("report_course","   Generate Course Report            #");
        MenuBuildItem("report_student","   Generate Student Report           #");
        MenuBuildItem("report_lecturer","   Generate Lecturer Report          #");
        MenuBuildItem("report_lecturer_own","   Generate my Lecturer Report       #");
        MenuBuildItem("","#  [0]   Logout                            #");
        MenuBuildItem("","#                                          #");
        MenuBuildItem("","############################################");
   
    }
 
    /**
    * Adds menu item
    * @param String userPermission - a single user's permission
    * @param String menuTitle - name of the item menu
    */ 
    private void MenuBuildItem(String userPermission, String menuTitle){
        
        if (userPermission.isEmpty()) {
            menuList.add(menuTitle);
        } else {
            if (userPerm.hasPermission(app.currentUser, userPermission)) {
                menuList.add("#  ["+menuCounter+"]" + menuTitle);
                menuAction.add(userPermission);
                menuCounter++;
            }            
        }
    }
    
     /**
    * Takes a string from console
    * @param message - a promt string for user
    * @return Returns a string from console input
    */   
    private String getUserInput(String message){
        
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
