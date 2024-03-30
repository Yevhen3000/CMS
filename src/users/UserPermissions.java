/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;

import cms.Config;
import cms.Config.userType;
import java.util.Map;
import java.util.HashMap;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */


public class UserPermissions {
/**
* Class is to implement users's permissions in the app
*/    
    private Config config;
    private Map<userType, String[]> permissionsMap;
    
     public UserPermissions(Config appConfig) {
        config = appConfig;
        permissionsMap = new HashMap<>();
        // Define permissions for each role
        permissionsMap.put(userType.ADMIN, config.admin_permissions);
        permissionsMap.put(userType.OFFICE, config.office_permissions);
        permissionsMap.put(userType.LECTURER, config.lecturer_permissions);
    }

    // Method to check if a user has permission to perform a certain action
    public boolean hasPermission(User user, String action) {
        String[] permissions = permissionsMap.get(user.getRole());
        if (permissions != null) {
            for (String permission : permissions) {
                if (permission.equals(action)) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
