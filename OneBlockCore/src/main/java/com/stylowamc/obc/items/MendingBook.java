package com.stylowamc.obc.items;

import com.stylowamc.obc.OBC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class MendingBook {
    public static final NamespacedKey MENDING_KEY =
            new NamespacedKey(OBC.getInstance(), "mending");


    public static void register(){
        ItemStack mending = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) mending.getItemMeta();
        meta.addStoredEnchant(Enchantment.MENDING, 1, true);
        mending.setItemMeta(meta);
        RecipeChoice.ExactChoice piece = new RecipeChoice.ExactChoice(LightPiece.create());

        ShapedRecipe rec = new ShapedRecipe(MENDING_KEY, mending);
        rec.shape("AAA", "ABA", "AAA");
        rec.setIngredient('A', piece);
        rec.setIngredient('B',Material.BOOK);

        Bukkit.addRecipe(rec);
    }

    public static void unregister(){
        Bukkit.removeRecipe(MENDING_KEY);
    }
}
