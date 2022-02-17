package com.stylowamc.obc.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class DarkPiece {

    public static ItemStack create(){
        ItemStack item = new ItemStack(Material.BLACK_DYE);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Tajemniczy odłamek monolitu");
        lore.add("Wygląda na wartościowy");
        meta.addEnchant(Enchantment.CHANNELING,1,true);
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Ciemny Odłamek");
        item.setItemMeta(meta);
        return item;
    }

}
