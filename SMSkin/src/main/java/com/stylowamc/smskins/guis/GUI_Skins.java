package com.stylowamc.smskins.guis;

import com.stylowamc.smskins.SMSkins;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sun.security.krb5.Config;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;

public class GUI_Skins implements Listener {
    private final Inventory inv;
    private static SMSkins plugin = SMSkins.getInstance();

    public GUI_Skins() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example

        inv = Bukkit.createInventory(null, 36, "Example");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        for(int i=0; i < plugin.getConfig().getKeys(false).size(); i++){
            {
                String lore = plugin.getConfig().getString(i+".itelore");
                String name = plugin.getConfig().getString(i+".itemname");
                String material = plugin.getConfig().getString("items."+i+".material");
                Bukkit.broadcastMessage(name);
                inv.addItem(createGuiItem(Material.getMaterial(material.toUpperCase(Locale.ROOT)), name, lore));
            }
    }

    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != inv) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        p.sendMessage("You clicked at slot " + e.getRawSlot());
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }
}
