package pl.smpickaxe.ores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Platinium {
    private Platinium() {
    }

    public static ItemStack createPlatinium() {
        ItemStack plat = new ItemStack(Material.IRON_NUGGET);
        ItemMeta meta = plat.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Piekielnie wartościowy materiał!");
        lore.add("Używany do stworzenia kilofa 2x3!");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.AQUA + "Platyna");
        plat.setItemMeta(meta);

        return plat;
    }
}
