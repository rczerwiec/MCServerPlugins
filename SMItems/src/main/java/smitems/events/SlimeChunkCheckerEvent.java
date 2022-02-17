package smitems.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import smitems.util.SlimeChunkCheckerUtil;

import java.util.Objects;
import java.util.Random;

public class SlimeChunkCheckerEvent implements Listener {

    @EventHandler
    public void RightClickEvent (PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if(!a.equals(Action.RIGHT_CLICK_AIR)) return;
        if (p.getInventory().getItemInMainHand().getType() != Material.FEATHER) return;
        if (!p.getInventory().getItemInMainHand().getItemMeta().hasLore()) return;
        try{
            e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
            Chunk chunk = p.getLocation().getBlock().getChunk();
            int x = chunk.getX();
            int z = chunk.getZ();
            boolean isSlimy = isSlimeChunk(p, x, z);
            revealResult(isSlimy, p);
            e.setCancelled(true);

        }catch(NullPointerException ex) {return;}
    }

    private void revealResult(boolean isSlimy, Player player)
    {
        if(isSlimy)
            player.sendMessage(" " + ChatColor.GREEN + "✔" + ChatColor.AQUA + " To " + ChatColor.UNDERLINE + "jest" + ChatColor.RESET + ChatColor.AQUA + " slimechunk." + ChatColor.RESET + "");
        else
            player.sendMessage(" " + ChatColor.RED + "✘" + ChatColor.AQUA + " To " + ChatColor.UNDERLINE + "nie jest" + ChatColor.RESET + ChatColor.AQUA + " slimechunk." + ChatColor.RESET + "");
    }


    private boolean isSlimeChunk(Player player, int x, int z)
    {
        long seed = player.getWorld().getSeed();

        Random rnd = new Random(seed + (long) (x * x * 0x4c1906) + (long) (x * 0x5ac0db) + (long) (z * z) * 0x4307a7L + (long) (z * 0x5f24f) ^ 0x3ad8025f);

        return rnd.nextInt(10) == 0;
    }



}
