/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientType2;

import java.util.ArrayList;
import java.util.Map;
import static java.util.Map.entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ss
 */
public class Planners {

    public static String username;
    public static String password;

    public Map<String, String> allUserInformations = Map.ofEntries(
            entry("admin", "admin")
    );

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setUsername(String username) {
        Planners.username = username;
    }

    public static void setPassword(String password) {
        Planners.password = password;
    }

}
