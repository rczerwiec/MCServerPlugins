package com.stylowamc.obc.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class StarBook {

    public static ItemStack create(){
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Starożytna księga Withera");
        lore.add("Ponoć z 9 można utworzyć gwiazdę netheru");
        meta.addEnchant(Enchantment.CHANNELING,1,true);
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Księga Gwiazdy");
        item.setItemMeta(meta);
        return item;
    }
}
