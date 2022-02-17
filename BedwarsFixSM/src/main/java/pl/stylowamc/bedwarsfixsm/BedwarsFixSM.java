package pl.stylowamc.bedwarsfixsm;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EventListener;

public final class BedwarsFixSM extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new VoidKillEvent(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
