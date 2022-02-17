package banknotysm.util;

import banknotysm.BanknotySM;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;

public class BanknotyUtil {
    public static final NamespacedKey Banknot = new NamespacedKey(BanknotySM.getInstance(),"banknot");

    public static final String BANKNOT_GIVE_SELF_PERM = "banknot.give.self";
    public static final String BANKNOT_RELOAD_PERM = "banknot.reload";
    public static int numer;

    private static final ArrayList<Permission> perms = new ArrayList<org.bukkit.permissions.Permission>();

    private BanknotyUtil(){
    }

    public static ItemStack createBanknot(int nominal){
        ItemStack banknot = new ItemStack(Material.PAPER);
        ItemMeta meta = banknot.getItemMeta();

        ArrayList<String> lorebanknotu = new ArrayList<>();
        lorebanknotu.add("Banknot o wartości "+nominal+"$");

           meta.setLore(lorebanknotu);
           meta.setDisplayName(ChatColor.DARK_GREEN+"Banknot "+ ChatColor.DARK_RED+nominal+"$");

            banknot.setItemMeta(meta);
            numer++;
            return banknot;

    }

    public static void registerPermissions(){
        perms.add(new Permission(BANKNOT_GIVE_SELF_PERM,"Allows player to give Banknot", PermissionDefault.OP));
        perms.add(new Permission(BANKNOT_RELOAD_PERM,"Pozwala reloadowac plugin",PermissionDefault.OP));

        for (Permission perm : perms){
            Bukkit.getPluginManager().addPermission(perm);
            BanknotySM.getInstance().getLogger().fine("Registered Permission:"+perm.getName());
        }
    }

    public static void unregisterPermissions(){
        perms.remove(new Permission(BANKNOT_GIVE_SELF_PERM,"Allows player to give Banknot", PermissionDefault.FALSE));
        perms.remove(new Permission(BANKNOT_RELOAD_PERM,"Pozwala reloadowac plugin",PermissionDefault.OP));
        for (Permission perm : perms){
            Bukkit.getPluginManager().removePermission(perm);
            BanknotySM.getInstance().getLogger().fine("Unregistered Permission:"+perm.getName());
        }

        perms.clear();
    }
}
