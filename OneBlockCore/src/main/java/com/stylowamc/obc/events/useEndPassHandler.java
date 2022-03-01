package com.stylowamc.obc.events;

import com.stylowamc.obc.items.EndPass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class useEndPassHandler implements Listener {

    @EventHandler
    public void OnPassUse(PlayerInteractEvent e){
        if (e.getPlayer().getInventory().getItemInMainHand().equals(EndPass.create())){
            Player p = e.getPlayer();
            p.sendMessage("§a§lZostałeś przeteleportowany do endu!");
            p.getInventory().removeItem(p.getInventory().getItemInMainHand());
            Location loc = new Location(Bukkit.getWorld("new_end"),0,65,0);
            p.teleport(loc);
        }
    }

}
