package smpvp.smpvp.inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import smpvp.smpvp.SMPvp;
import smpvp.smpvp.Statics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InventoryData {
    static SMPvp plugin = SMPvp.getInstance();
    public String name = "test";
    public Inventory inv;
    public Player p;
    public static HashMap<Player,Inventory> inventories = new HashMap<>();
    public static HashMap<Player,Inventory> admininventories = new HashMap<>();
    public static HashMap<String,Inventory> arenasinventories = new HashMap<>();
    public boolean editing = false;


    public InventoryData(String name,Inventory inv,Player p, boolean editing, boolean custom) throws IOException {
        this.name = name;
        this.inv = inv;
        this.p = p;
        this.editing = editing;


        SaveInventory(custom);
    }

    //new InventoryData(e.getView().getTitle(),e.getInventory(),p,true,false);
    void SaveInventory(boolean custom) throws IOException {
        YamlConfiguration c = new YamlConfiguration();
        c.set("inventory.content", inv.getContents());

        if(custom){
            List<String> ConfigList = plugin.kitlist.getConfig().getStringList(p.getName()+".name");

            if(plugin.kitlist.getConfig().getStringList(p.getName()+".name").size()>=26)
            {
                p.sendMessage("§4§lNie możesz zapisać więcej kitów!");
            }

            if(ConfigList.contains(name)){
                if(!editing){
                    name = name+1;
                }
            }
            c.save(new File(plugin.getDataFolder()+"/kits", name+".yml"));
            AllKits.AddKit(name,p.getName());
            p.sendMessage("§a§lZapisano pomyślnie!");
        }
        if(!custom){
            List<String> ConfigList = plugin.adminKitList.getConfig().getStringList("name");

            if(plugin.adminKitList.getConfig().getStringList("name").size()>=26)
            {
                p.sendMessage("§4§lNie możesz zapisać więcej kitów!");
            }

            if(ConfigList.contains(name)){
                if(!editing){
                    name = name+1;
                }
            }
            c.save(new File(plugin.getDataFolder()+"/adminkits", name+".yml"));
            AdminAllKits.AddKit(name);
            p.sendMessage("§a§lZapisano pomyślnie, zmień nazwę w kitu w configu. Inaczej będzie:"+name);

        }
        //debugs
        //Bukkit.broadcastMessage("Kit gracza:"+AllKits.getKitName(p.getName()));
    }

    public static void RemoveKit(String Name,Player p){
        File dir = new File(plugin.getDataFolder()+"/kits", Name+".yml");
        dir.delete();



        List<String> ConfigList = plugin.kitlist.getConfig().getStringList(p.getName()+".name");

        //Bukkit.broadcastMessage(String.valueOf(ConfigList.indexOf(Name)));
        //Bukkit.broadcastMessage(Name);

        ConfigList.remove(Name);
        plugin.kitlist.getConfig().set(p.getName()+".name",ConfigList);
        plugin.kitlist.saveConfig();

    }
    @SuppressWarnings("unchecked")
    public static void RestoreInventory(String Name,Player p,boolean custom){
        //Bukkit.broadcastMessage(Name+".yml");
        YamlConfiguration c;
        //pobieranie wartosci zależnie czy kit customowy/adminowski
        if(custom){//customowy (tworzony przez graczy)
            c = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder()+"/kits", Name+".yml"));
        }
        else{//adminowski
            c = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder()+"/adminkits", Name+".yml"));
        }

        ItemStack[] content = ((List<ItemStack>) Objects.requireNonNull(c.get("inventory.content"))).toArray(new ItemStack[0]);

        //Bukkit.broadcastMessage(content[0].toString());
        //Bukkit.broadcastMessage(String.valueOf(content.length));

        Inventory inv = Bukkit.createInventory(null,45, Name);
        ItemStack item = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add("test");
        //ODWZORUJ WYGLĄD KITU
        for(int i=0; i<content.length; i++){

            if(i==41){
                assert meta != null;
                item.setType(Material.RED_STAINED_GLASS_PANE);
                meta.setDisplayName(ChatColor.DARK_BLUE + "USUN");
                lore.set(0,ChatColor.GRAY + "Kliknij zeby usunąć swój kit");
                meta.setLore(lore);
                item.setItemMeta(meta);
                inv.setItem(Statics.DELETE_BUTTON,item);
            }
            else if(i==42){
                assert meta != null;
                item.setType(Material.PURPLE_STAINED_GLASS_PANE);
                meta.setDisplayName(ChatColor.DARK_BLUE + "WEŹ KIT");
                lore.set(0,ChatColor.GRAY + "Kliknij zeby sprawdzić swój kit");
                meta.setLore(lore);
                item.setItemMeta(meta);
                inv.setItem(Statics.GETKIT_BUTTON,item);
            }
            else{
                inv.setItem(i,content[i]);
            }

        }
        if(custom){
            //Bukkit.broadcastMessage("put into inventories");
            inventories.put(p,inv);
        }
        if(!custom){
            //Bukkit.broadcastMessage("put into admininventories");
            admininventories.put(p,inv);
            arenasinventories.put(Name,inv);
        }
        //Bukkit.broadcastMessage(admininventories.toString());
        //Bukkit.broadcastMessage(inventories.toString());
    }



}
