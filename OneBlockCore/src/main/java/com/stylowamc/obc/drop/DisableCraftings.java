package com.stylowamc.obc.drop;

import com.stylowamc.obc.items.Beacon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class DisableCraftings implements Listener {

    @EventHandler
    public void craftItem(PrepareItemCraftEvent e) {
        Material itemType = e.getRecipe().getResult().getType();
        ItemStack item = e.getRecipe().getResult();
        //Byte itemData = e.getRecipe().getResult().getData().getData();
        if(itemType==Material.BEACON && !item.getItemMeta().getDisplayName().equals(Beacon.create().getItemMeta().getDisplayName())) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
            for(HumanEntity he:e.getViewers()) {
                if(he instanceof Player) {
                    ((Player)he).sendMessage(ChatColor.RED+"Ten crafting jest wyłączony!");
                }
            }
        }
    }
}
