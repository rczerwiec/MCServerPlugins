package com.stylowamc.obc.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EndFlower {
    public static ItemStack create(){
        ItemStack item = new ItemStack(Material.BUBBLE_CORAL_FAN);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Roślina z nieznanego wymiaru");
        lore.add("Otocza ją przedziwna aura");
        meta.addEnchant(Enchantment.CHANNELING,1,true);
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Kwiat Endu");
        item.setItemMeta(meta);
        return item;
    }
}
