package smitems.util;

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
import smitems.SMItems;

import java.util.ArrayList;
import java.util.List;

public class SlimeChunkCheckerUtil {
    public static final NamespacedKey SLIME_CHEKER = new NamespacedKey(SMItems.getInstance(), "slimechecker");
    static ArrayList<String> lore = new ArrayList<>();


    public static ItemStack createItem() {
        ItemStack sc = new ItemStack(Material.FEATHER);

        ItemMeta meta = sc.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"Sprawdzacz Slime-Chunka");
        lore.add("Za pomocą tego przedmiotu, możesz");
        lore.add("sprawdzić lokalizacje slime-chunka");
        lore.add("Zużywa się po użyciu");
        meta.setLore(lore);
        sc.setAmount(3);
        sc.setItemMeta(meta);

        return sc;
    }

    public static void registerRecipe() {
        ItemStack sc = SlimeChunkCheckerUtil.createItem();


        ShapedRecipe rec = new ShapedRecipe(SLIME_CHEKER,sc);
        rec.shape("AAA", "AGA", "AAA");
        rec.setIngredient('A', Material.DIAMOND);
        rec.setIngredient('G', Material.SLIME_BALL);


        boolean success = Bukkit.addRecipe(rec);
        if (success)
            SMItems.getInstance().getLogger().fine("Dodany przepis na " + SLIME_CHEKER.getNamespace() + ":" + SLIME_CHEKER.getKey());
        else
            SMItems.getInstance().getLogger().fine("NIEPOMYSLNE dodanie przepisu na: " + SLIME_CHEKER.getNamespace() + ":" + SLIME_CHEKER.getKey());

    }

    public static boolean unregisterRecipe() {
        boolean success = Bukkit.removeRecipe(SLIME_CHEKER);

        if (success)
            SMItems.getInstance().getLogger().fine("Usunieto przepis na:" + SLIME_CHEKER.getNamespace() + ":" + SLIME_CHEKER.getKey());

        else
            SMItems.getInstance().getLogger().fine("Nie udalo sie usunac przepisu na: " + SLIME_CHEKER.getNamespace() + ":" + SLIME_CHEKER.getKey());

        return success;
    }
}
