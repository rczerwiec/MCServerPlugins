package smpvp.smpvp.inventories;

import smpvp.smpvp.SMPvp;

import java.util.ArrayList;
import java.util.List;

//Dodaje do listy kitów w pliku kits.yml
public class AllKits {
     static SMPvp plugin = SMPvp.getInstance();

    public static void AddKit(String name,String PlayerName){
        if(plugin.kitlist.getConfig().contains(PlayerName+".name")){
            List<String> ConfigList = plugin.kitlist.getConfig().getStringList(PlayerName+".name");
            if(ConfigList.contains(name)){
                return;
            }
            ConfigList.add(name);
            plugin.kitlist.getConfig().set(PlayerName+".name",ConfigList);
            plugin.kitlist.saveConfig();
        }
        else{
            List<String> ConfigList = new ArrayList<String>();
            ConfigList.add(name);
            plugin.kitlist.getConfig().set(PlayerName+".name",ConfigList);
            plugin.kitlist.saveConfig();
        }
    }
}
