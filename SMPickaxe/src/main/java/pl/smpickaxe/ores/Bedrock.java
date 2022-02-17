package pl.smpickaxe.ores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Bedrock {
    private Bedrock() {
    }

    public static ItemStack createBedrock() {
        ItemStack bd = new ItemStack(Material.BEDROCK);
        ItemMeta meta = bd.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Najtwardsza skała na serwerze!");
        lore.add("Używany do stworzenia kilofa 3x3!");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.BLACK + "Bedrock");
        bd.setItemMeta(meta);

        return bd;
    }
}
