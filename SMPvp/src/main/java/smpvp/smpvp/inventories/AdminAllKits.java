package smpvp.smpvp.inventories;

import smpvp.smpvp.SMPvp;

import java.util.ArrayList;
import java.util.List;

public class AdminAllKits {
    static SMPvp plugin = SMPvp.getInstance();

    public static void AddKit(String name){
        if(plugin.adminKitList.getConfig().contains("name")){
            List<String> ConfigList = plugin.adminKitList.getConfig().getStringList("name");
            if(ConfigList.contains(name)){
                return;
            }
            ConfigList.add(name);
            plugin.adminKitList.getConfig().set("name",ConfigList);
            plugin.adminKitList.saveConfig();
        }
        else{
            List<String> ConfigList = new ArrayList<String>();
            ConfigList.add(name);
            plugin.adminKitList.getConfig().set("name",ConfigList);
            plugin.adminKitList.saveConfig();
        }
    }
}
