package com.stylowamc.obc.items;

import com.stylowamc.obc.OBC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class SkeletonSkull {
    public static final NamespacedKey WITHERSKELETON_KEY =
            new NamespacedKey(OBC.getInstance(), "witherskeletonkey");


    public static void register(){
        ItemStack item = new ItemStack(Material.WITHER_SKELETON_SKULL);
        ShapedRecipe rec = new ShapedRecipe(WITHERSKELETON_KEY, item);
        RecipeChoice.ExactChoice darkpiece = new RecipeChoice.ExactChoice(DarkPiece.create());

        rec.shape("AAA","ABA","AAA");
        rec.setIngredient('A', darkpiece);
        rec.setIngredient('B',Material.SKELETON_SKULL);
        Bukkit.addRecipe(rec);
    }

    public static void unregister(){
        Bukkit.removeRecipe(WITHERSKELETON_KEY);
    }
}
