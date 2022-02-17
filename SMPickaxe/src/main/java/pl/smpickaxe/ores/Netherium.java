package pl.smpickaxe.ores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Netherium {
    private Netherium() {
    }

    public static ItemStack createNetherium() {
        ItemStack net = new ItemStack(Material.NETHER_BRICK);
        ItemMeta meta = net.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Netherium? Do czego służy ta ruda?!");
        lore.add("Używany do stworzenia kilofa mogącego wykopać spawner!");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GOLD + "Netherium");
        net.setItemMeta(meta);

        return net;
    }
}
