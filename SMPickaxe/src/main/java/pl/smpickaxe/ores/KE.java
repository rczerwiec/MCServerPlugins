package pl.smpickaxe.ores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class KE {
    private KE() {
    }

    public static ItemStack createEmber() {
        ItemStack bd = new ItemStack(Material.LIME_DYE);
        ItemMeta meta = bd.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Czuć od niego dziwną energię!");
        lore.add("Używany do ???!");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GREEN + "Kamień Ember");
        bd.setItemMeta(meta);

        return bd;
    }
}
