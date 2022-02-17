package pl.smpickaxe.spawners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class RandomSpawnerChest {
    private RandomSpawnerChest() {
    }

    //Random chest creator
    public static ItemStack createRandomSpawnerChest() {
        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta meta = chest.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Skrzynka z randomowym spawnerem!");
        lore.add("Masz 50% szans, że wypadnie Ci spawner!");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GOLD + "Skrzynka Spawnerów");
        chest.setItemMeta(meta);

        return chest;
    }
}
