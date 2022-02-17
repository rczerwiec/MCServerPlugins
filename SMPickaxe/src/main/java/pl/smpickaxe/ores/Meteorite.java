package pl.smpickaxe.ores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Meteorite {
    private Meteorite() {
    }

    public static ItemStack createMeteorite() {
        ItemStack meteor = new ItemStack(Material.BROWN_DYE);
        ItemMeta meta = meteor.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Starożytny Meteoryt! Może jest z SM3?");
        lore.add("Używany do stworzenia kilofa 2x2!");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GOLD + "Meteoryt");
        meteor.setItemMeta(meta);

        return meteor;
    }
}
