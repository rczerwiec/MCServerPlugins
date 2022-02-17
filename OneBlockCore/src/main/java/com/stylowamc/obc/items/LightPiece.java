package com.stylowamc.obc.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class LightPiece {
    public static ItemStack create(){
        ItemStack item = new ItemStack(Material.LIGHT_GRAY_DYE);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW+"Tajemniczy odłamek gwiazdy");
        lore.add(ChatColor.YELLOW+"Wygląda na wartościowy");
        meta.addEnchant(Enchantment.CHANNELING,1,true);
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GOLD+"Jasny Odłamek");
        item.setItemMeta(meta);
        return item;
    }
}
