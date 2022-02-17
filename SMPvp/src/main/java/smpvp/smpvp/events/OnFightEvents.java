package smpvp.smpvp.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.PlayerInventory;
import smpvp.smpvp.SMPvp;
import smpvp.smpvp.arenas.Arena;
import smpvp.smpvp.arenas.ArenaManager;
import smpvp.smpvp.arenas.NewArenas;

public class OnFightEvents implements Listener {

    SMPvp plugin = SMPvp.getInstance();


    @EventHandler
    public void signClickEvent(PlayerInteractEvent event) {
        try {
            Location lobbyAllLocation = new Location(event.getPlayer().getWorld(), -5.0D, 151.0D, -192.0D);
            Location startLobbyLocation = new Location(event.getPlayer().getWorld(), -5.0D, 151.0D, -192.0D);
            Player p = event.getPlayer();
            Block b = event.getClickedBlock();
            if (b.getState() instanceof Sign) {
                event.setCancelled(true);
                PlayerInventory inv = p.getInventory();
                Sign sign = (Sign)b.getState();
                String line0 = sign.getLine(0);
                String line1 = sign.getLine(1);
                String line2 = sign.getLine(2);
                if (line0.equals("Powrót") && line1.equals("[Wcisnij]")) {
                    ArenaManager.resetPlayer(p);
                    p.teleport(startLobbyLocation);
                }

                if (inv.isEmpty()) {
                    if (line0.equals("KAZDY") && line1.equals("NA KAZDEGO") && line2.equals("[WCISNIJ]")) {
                        p.teleport(lobbyAllLocation);
                    }

                    if (line0.equals("1 VS 1") && line2.equals("[WCISNIJ]")) {
                        p.teleport(lobbyAllLocation);
                    }
                    else{
                        ArenaManager.joinArena(p,line1,sign);
                    }
                        //ArenaManager.joinArena(p, line1, sign);


                    /*if (line1.equals("BESTIA")) {
                        Kits.bestia(inv);
                        this.RandomTeleport(p);
                    }

                    if (line1.equals("ŁUCZNIK")) {
                        Kits.lucznik(inv);
                        this.RandomTeleport(p);
                    }

                    if (line1.equals("WOJOWNIK")) {
                        Kits.wojownik(inv);
                        this.RandomTeleport(p);
                    }

                    if (line1.equals("SAMURAJ")) {
                        Kits.samuraj(inv);
                        this.RandomTeleport(p);
                    }*/
                }
            }
        } catch (NullPointerException var11) {
        }

    }

    @EventHandler
    public void pde(PlayerDeathEvent e) {

        try{
            Player killer = e.getEntity().getKiller();
            Player player = e.getEntity();
            //Bukkit.broadcastMessage("Zabójca istnieje");

            setKDA(killer,"kills",false);
            setKDA(player,"death",true);
            if(ArenaManager.playerIsInArena(player)){
                //Bukkit.broadcastMessage("jezeli zabojca jest na arenie to restartuj wszystkich na arenie + samą arene");
                //jezeli zabojca jest na arenie to restartuj wszystkich na arenie + samą arene
                ArenaManager.resetPlayer(killer);
                ArenaManager.arenaUpdate(killer);
            }
            else if(NewArenas.playerIsInCustomArena(player) != null){
                ArenaManager.resetPlayer(player);
                ArenaManager.customArenaUpdate(player);
            }
            else{
                //Bukkit.broadcastMessage("jezeli zabojca nie jest na arenie to restartuj tylko zabitego");
                //jezeli zabojca nie jest na arenie to restartuj tylko zabitego
                ArenaManager.resetPlayer(player);
            }
        }
        catch (NullPointerException err){
            //Bukkit.broadcastMessage("Zabójca nie istnieje");
            Player player = e.getEntity();
            if(ArenaManager.playerIsInArena(player)){
                //Bukkit.broadcastMessage("Samobojstwo, zwykła arena");
                //jezeli zabojca nie istnieje, a gracz jest na arenie restartuj tylko jego i arene
                ArenaManager.resetPlayer(player);
                ArenaManager.arenaUpdate(player);
            }
            else if(NewArenas.playerIsInCustomArena(player) != null){
                //Bukkit.broadcastMessage("Samobojstwo, customowa arena");
                ArenaManager.resetPlayer(player);
                ArenaManager.customArenaUpdate(player);
            }
            else{
                //Bukkit.broadcastMessage("Samobojstwo, gracza nie ma na arenie");
                //jezeli zabojca nie istnieje, a gracz nie jest na arenie, restartuj tylko jego
                ArenaManager.resetPlayer(player);
            }

        }
    }

    @EventHandler
    public void playerRespawnevent(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if(ArenaManager.playerIsInArena(p)){
            //Jezeli gracz byl na arenie - restart areny i gracza
            ArenaManager.arenaUpdate(p);
            ArenaManager.resetPlayer(p);
        }
        else if(NewArenas.customArenas.get(p)!=null){
            //Jezeli gracz byl na arenie - restart areny i gracza
            ArenaManager.customArenaUpdate(p);
            ArenaManager.resetPlayer(p);
        }
        else{
            ArenaManager.resetPlayer(p);
        }


    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (ArenaManager.playerIsInArena(p)) {
            Arena arena = ArenaManager.arenas.get(ArenaManager.arenaUpdate(p));
            arena.reset();
            ArenaManager.arenaUpdate(p);

        }
        else if(NewArenas.playerIsInCustomArena(p) != null){
            ArenaManager.customArenaUpdate(p);
            Arena a = NewArenas.playerIsInCustomArena(p);
            a.reset();
            ArenaManager.resetPlayer(p);

        }

        else {
            try {
                ArenaManager.resetPlayer(p);
            } catch (NullPointerException var4) {
            }
        }

    }

    void setKDA(Player p,String kod, boolean message){
        if(p.getKiller() != null){
            if(kod.equals("kills") || kod.equals("death")){
                int amount = 0;
                //Bukkit.broadcastMessage("Zabity:"+p.getName());
                //Bukkit.broadcastMessage("Zabójca:"+p.getKiller().getName());
                if(plugin.data.getConfig().contains("kda."+p.getUniqueId().toString()+"."+kod)){
                    amount = plugin.data.getConfig().getInt("kda."+p.getUniqueId().toString()+"."+kod);

                }
                plugin.data.getConfig().set("kda."+p.getUniqueId().toString()+"."+kod,(amount +1));
                plugin.data.saveConfig();
                int kills = 0;
                int death = 0;
                int kkills = 0;
                int kdeath = 0;
                if(plugin.data.getConfig().contains("kda."+p.getUniqueId().toString()+".kills")){
                    kills = plugin.data.getConfig().getInt("kda."+p.getUniqueId().toString()+".kills");
                }
                if(plugin.data.getConfig().contains("kda."+p.getUniqueId().toString()+".death")){
                    death = plugin.data.getConfig().getInt("kda."+p.getUniqueId().toString()+".death");
                }
                if(plugin.data.getConfig().contains("kda."+p.getKiller().getUniqueId().toString()+".kills")){
                    kkills = plugin.data.getConfig().getInt("kda."+p.getKiller().getUniqueId().toString()+".kills");
                }
                if(plugin.data.getConfig().contains("kda."+p.getKiller().getUniqueId().toString()+".death")){
                    kdeath = plugin.data.getConfig().getInt("kda."+p.getKiller().getUniqueId().toString()+".death");
                }

                if(message){
                    Bukkit.broadcastMessage("§7Gracz §b" + p.getKiller().getName() +"§f§l [§a§l"+ kkills+"§f§l/§4§l"+kdeath+"§f§l]"+" §7zabija §b" + p.getName() + "§4§l [" + (int)p.getKiller().getHealth() + "HP] §f§l[§a§l"+kills+"§f§l/§4§l"+death+"§f§l]");
                }
            }
        }

    }

    public void RandomTeleport(Player p) {
        int rand = (int)(Math.random() * 4.0D + 0.0D);
        ArrayList<Location> locations = new ArrayList();
        locations.add(new Location(p.getWorld(), -34.0D, 121.0D, -180.0D));
        locations.add(new Location(p.getWorld(), -105.0D, 121.0D, -181.0D));
        locations.add(new Location(p.getWorld(), -86.0D, 121.0D, -260.0D));
        locations.add(new Location(p.getWorld(), -29.0D, 121.0D, -251.0D));
        p.teleport((Location)locations.get(rand));
    }

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager().getName().equals(e.getEntity().getName())) {
            e.setCancelled(true);
        }

    }
}