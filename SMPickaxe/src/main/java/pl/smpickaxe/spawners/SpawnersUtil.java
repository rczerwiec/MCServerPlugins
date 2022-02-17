package pl.smpickaxe.spawners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import pl.smpickaxe.spawners.EntityTypesInSpawner;

import java.util.ArrayList;

public class SpawnersUtil {
    //spawner's lore
    static ArrayList<String> lore = new ArrayList<>();
    //spawner creator
    public static ItemStack CreateSpawner(EntityType type,String mobName){
                ItemStack item = new ItemStack(Material.SPAWNER);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("Spawner "+mobName);
                lore.add("Oryginalny spawner "+ mobName);
                meta.setLore(lore);
                lore.clear();
                BlockStateMeta bsm = (BlockStateMeta) meta ;
                BlockState bs = bsm.getBlockState();
                CreatureSpawner spawner = (CreatureSpawner)bs;
                spawner.setSpawnedType(type);
                bsm.setBlockState(spawner);

                item.setItemMeta(bsm);
                return item;
    }
    //It's getting random spawner from chcest using EntityTypeInSpawner
    public static void GetRandomSpawner(Player p){
        int rand = (int) (Math.random() * (100 - 1 + 1) + 1);
        if(rand>=90){
                    EntityType entity = EntityTypesInSpawner.RandomEntityType();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&4&lGratulacje wylosowałeś &6&lSpawner "+ entity.name()+"!"));
                    p.getInventory().addItem(CreateSpawner(entity,entity.name()));
        }
        else{
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&4&lPrzykro mi, skrzynka była pusta! &6&lMasz 10% na zdobycie spawnera!"));
        }
    }
}
