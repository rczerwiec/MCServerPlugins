package com.stylowamc.obc.items;

import com.stylowamc.obc.OBC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class NetherStar {
    public static final NamespacedKey NETHERSTAR_KEY =
            new NamespacedKey(OBC.getInstance(), "netherstar");


    public static void register(){
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ShapedRecipe rec = new ShapedRecipe(NETHERSTAR_KEY, item);
        RecipeChoice.ExactChoice star = new RecipeChoice.ExactChoice(StarBook.create());

        rec.shape("AAA","AAA","AAA");
        rec.setIngredient('A', star);
        Bukkit.addRecipe(rec);
    }

    public static void unregister(){
        Bukkit.removeRecipe(NETHERSTAR_KEY);
    }
}
