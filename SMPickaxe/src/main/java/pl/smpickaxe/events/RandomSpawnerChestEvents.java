package pl.smpickaxe.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.smpickaxe.spawners.RandomSpawnerChest;
import pl.smpickaxe.spawners.SpawnersUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RandomSpawnerChestEvents implements Listener {
    //place spawner loot box event
    @EventHandler
    public void RandomSpawnerChestPlace(BlockPlaceEvent e){
        int amount = e.getPlayer().getInventory().getItemInMainHand().getAmount();
        if(e.getItemInHand().getType().equals(Material.CHEST)){
            ItemStack item = e.getItemInHand();
            ArrayList<String> lore = new ArrayList<>();
            lore.add("Skrzynka z randomowym spawnerem!");
            lore.add("Masz 50% szans, że wypadnie Ci spawner!");
            if(Objects.equals(item.getItemMeta().getLore(), lore)){
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
                SpawnersUtil.GetRandomSpawner(e.getPlayer());
                e.getBlock().setType(Material.AIR);
            }
        }

    }
    //**Spawner Destroy Event
    @EventHandler
    public void SpawnerDestroy(BlockBreakEvent e){
        if(e.isCancelled()) return;
        if(!e.getBlock().getType().equals(Material.SPAWNER))return;
        if(!e.getPlayer().getItemInHand().getItemMeta().hasLore()) return;
        if(!e.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) return;
        if(e.isCancelled()) return;
        ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
        List<String> lore = meta.getLore();

        if(lore.get(0).equals("Kilof potrafiący wykopać Spawner")){
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), RandomSpawnerChest.createRandomSpawnerChest());
        }



    }

    //**Crouch spawner loot box opening event
    @EventHandler
    public void OnCrouchRightclick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();

        if (!player.isSneaking()) return;
        if (!action.equals(Action.RIGHT_CLICK_AIR)) return;
        if (player.getInventory().getItemInMainHand().getType() != Material.CHEST) return;
        if (!player.getInventory().getItemInMainHand().getItemMeta().hasLore()) return;

        try {
            e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
            SpawnersUtil.GetRandomSpawner(e.getPlayer());
        } catch (Exception ex) {
            return;
        } //you didn't drop anything

    }
}
