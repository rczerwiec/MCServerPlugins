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
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EndPass {
    public static final NamespacedKey ENDPASS_KEY =
            new NamespacedKey(OBC.getInstance(), "endpass");

    public static ItemStack create(){
        ItemStack item = new ItemStack(Material.SKULL_BANNER_PATTERN);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Przepustka do nieznanego wymiaru");
        lore.add("Po przeczytaniu ulega zniszczeniu");
        lore.add("§6Wpisz /przepustka lub PPM żeby użyć!");
        meta.addEnchant(Enchantment.CHANNELING,1,true);
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GOLD+"Przepustka do Endu");
        item.setItemMeta(meta);
        return item;
    }

    public static void register(){
        ItemStack pass = create();
        RecipeChoice.ExactChoice piece = new RecipeChoice.ExactChoice(EndFlower.create());

        ShapedRecipe rec = new ShapedRecipe(ENDPASS_KEY, pass);
        rec.shape("AAA", "ABA", "AAA");
        rec.setIngredient('A', piece);
        rec.setIngredient('B',Material.WITHER_SKELETON_SKULL);

        Bukkit.addRecipe(rec);
    }

    public static void unregister(){
        Bukkit.removeRecipe(ENDPASS_KEY);
    }
}
