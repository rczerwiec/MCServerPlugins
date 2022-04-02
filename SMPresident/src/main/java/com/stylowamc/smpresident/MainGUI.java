package com.stylowamc.smpresident;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainGUI implements Listener {
    private static Inventory inv;
    static Smpresident plugin = Smpresident.getInstance();

    public MainGUI() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, (9*4), "§6§lWYBORY PREZYDENCKIE");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.setItem(0,createGuiItem(Material.BOOK,"§8§l§k|| §f§lINFO §8§l§K||"
                ,"§7Witam w menu wyborów prezydenckich 2022"
                ,"§8» §cPamiętaj że wybór jest jeden a odwrotu niema"
                ,"§8» §cDlatego pamiętaj by wybierać rozważnie"
                ,"§8» §cMiłego głosowania."
                ,""
                ,"§8§l» §e§lAby dokonać wyboru §c§lkliknij §e§lna główke"
                ,"§e§lz nickiem kandydata"

                ));
        for(int i=1; i<=7; i++){;
            inv.setItem(i,createGuiItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE,"-"));
        }
        for(int i=8; i<36; i+=9){
            inv.setItem(i,createGuiItem(Material.LIME_STAINED_GLASS_PANE,"-"));
        }
        for(int i=9; i<36; i+=9){
            inv.setItem(i,createGuiItem(Material.LIME_STAINED_GLASS_PANE,"-"));
        }
        for(int i=10; i<36; i+=9){
            inv.setItem(i,createGuiItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE,"-"));
        }
        for(int i=16; i<36; i+=9){
            inv.setItem(i,createGuiItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE,"-"));
        }
        for(int i=29; i<=33; i++){;
            inv.setItem(i,createGuiItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE,"-"));
        }
        for(int i=11; i<=15; i++){
            inv.setItem(i,getPlayerHead(plugin.getConfig().getString("kandydaci."+(i-11)+".name"),i-11));
        }
        for(int i=20; i<=24; i++){
            inv.setItem(i,getPlayerHead(plugin.getConfig().getString("kandydaci."+(i-15)+".name"),i-15));
        }
    }

    protected ItemStack getPlayerHead(String playerName,int i){
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1 , (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setDisplayName(playerName);
        List<String> lore = plugin.getConfig().getStringList("kandydaci."+(i)+".lore");
        meta.setLore(lore);
        meta.setOwner(playerName);
        item.setItemMeta(meta);

        return item;
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName("§6§l"+name);
        meta.addEnchant(Enchantment.CHANNELING,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public static void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;
        //Bukkit.broadcastMessage("click");
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        if((e.getRawSlot() >= 11 && e.getRawSlot() <=15) || (e.getRawSlot() >= 20 && e.getRawSlot() <= 24)){
            List<String> list = plugin.getConfig().getStringList("glosowali");
            if(list.contains(p.getName())) {
                p.sendMessage("§4§lJuż głosowałeś!"); return;
            }
            else{
                list.add(p.getName());
                plugin.getConfig().set("glosowali",list);
                int slot = e.getRawSlot();
                if(slot>=20) slot = slot-15;
                else slot = slot-11;
                int g = plugin.getConfig().getInt("kandydaci."+(slot)+".glosy");
                plugin.getConfig().set("kandydaci."+(slot)+".glosy",g+1);
                List<String> whoVoted = plugin.getConfig().getStringList("kandydaci."+(slot)+".kto");
                whoVoted.add(p.getName());
                plugin.getConfig().set("kandydaci."+(slot)+".kto", whoVoted);
                plugin.getConfig().options().copyDefaults(true);
                plugin.saveConfig();
                p.sendMessage("§6§lPomyślnie zagłosowałeś!");
            }

        }
        //p.sendMessage("You clicked at slot " + e.getRawSlot());
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }
}
