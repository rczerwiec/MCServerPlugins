package com.stylowamc.obc.items;

import com.stylowamc.obc.OBC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Beacon {
    public static final NamespacedKey BEACON_KEY =
            new NamespacedKey(OBC.getInstance(), "beacon");

    public static ItemStack create(){
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Niezwykle cenny artefakt");
        meta.addEnchant(Enchantment.CHANNELING,1,true);
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"BEACON");
        item.setItemMeta(meta);
        return item;
    }

    public static void register(){
        ItemStack item = create();
        ShapedRecipe rec = new ShapedRecipe(BEACON_KEY, item);
        //RecipeChoice.ExactChoice star = new RecipeChoice.ExactChoice(StarBook.create());
        rec.shape("AAA","AAA","AAA");
        rec.setIngredient('A', Material.NETHER_STAR);
        Bukkit.addRecipe(rec);
    }

    public static void unregister(){
        Bukkit.removeRecipe(BEACON_KEY);
    }
}
