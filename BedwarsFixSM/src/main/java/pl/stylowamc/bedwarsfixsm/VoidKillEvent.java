package pl.stylowamc.bedwarsfixsm;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class VoidKillEvent implements Listener {

    @EventHandler
    public void GetPlayer(PlayerMoveEvent e) {
        if (e.getFrom().getBlockY() <=0 && e.getFrom().getBlockY() >= -1 ) {
            e.getPlayer().setHealth(0);
            e.getPlayer().getLocation().setY(10);
            e.setCancelled(true);
        }
    }

}
