package smitems.perms;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import smitems.SMItems;

import java.util.ArrayList;

public class Perms {
    private static final ArrayList<Permission> perms  = new ArrayList<>();
    public static final String COMPOSTER_USE = "sm.composter";

    public static void registerPermissions(){
        //Dodawanie permisji do tablicy
        perms.add(new Permission(COMPOSTER_USE,"Allows player to use Composters", PermissionDefault.TRUE));

        //Dodanie kazdej permisji z tablicy do stosu.
        for (Permission perm : perms){
            Bukkit.getPluginManager().addPermission(perm);
            SMItems.getInstance().getLogger().fine("Registered Permission:"+perm.getName());
        }
    }

    //Usuniecie wszystkich permisji z stosu
    public static void unregisterPermissions(){
        for (Permission perm : perms){
            Bukkit.getPluginManager().removePermission(perm);
            SMItems.getInstance().getLogger().fine("Unregistered Permission:"+perm.getName());

        }
        //wyczyść liste
        perms.clear();
    }


}
