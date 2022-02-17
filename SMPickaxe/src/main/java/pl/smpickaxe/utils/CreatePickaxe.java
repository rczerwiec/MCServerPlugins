package pl.smpickaxe.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import pl.smpickaxe.Smpickaxe;

import java.util.ArrayList;

public class CreatePickaxe {
    public static ItemStack newPickaxe(String nazwa,String ile, ItemStack A, ItemStack B, ItemStack C, ItemStack I, ItemStack out) {
        NamespacedKey klucz = new NamespacedKey(Smpickaxe.getInstance(), "k" + ile);

        ItemMeta meta = out.getItemMeta();

        ArrayList<String> lorek1 = new ArrayList<>();
        if (ile.equals("1")) {
            lorek1.add("Siekiera zcinajaca drzewa");
            meta.setDisplayName(ChatColor.BLUE + "Rubinowa Siekiera");
        }
        else if (ile.equals("2x2spawnery")) {
            lorek1.add("Kilof potrafiący wykopać Spawner");
            meta.setDisplayName(ChatColor.GOLD + "Netheriumowy Kilof");
        }
        else {
            lorek1.add("Kilof kopiący tunel " + ile);
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&4&l"+ nazwa + " Kilof ") );
        }


        meta.setLore(lorek1);
        if (Bukkit.getRecipe(klucz(ile)) == null) {
            RecipeChoice k1 = new RecipeChoice.ExactChoice(A);
            RecipeChoice k2 = new RecipeChoice.ExactChoice(B);
            RecipeChoice k3 = new RecipeChoice.ExactChoice(C);
            RecipeChoice k4 = new RecipeChoice.ExactChoice(I);
            out.setItemMeta(meta);
            ShapedRecipe gp = new ShapedRecipe(klucz, out);
            gp.shape("AAB", "CI ", " I ");
            gp.setIngredient('A', k1);
            gp.setIngredient('B', k2);
            gp.setIngredient('C', k3);
            gp.setIngredient('I', k4);

            Bukkit.addRecipe(gp);
        } else {
            Bukkit.broadcastMessage("Recepta jest juz zaladowana");
        }

        return out;
    }

    public static NamespacedKey klucz(String ile) {
        return new NamespacedKey(Smpickaxe.getInstance(), "k" + ile);
    }

    public static boolean recipe_unregister(String ile) {
        return Bukkit.removeRecipe(klucz(ile));
    }

}
