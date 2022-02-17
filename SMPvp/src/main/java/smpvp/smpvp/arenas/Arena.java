package smpvp.smpvp.arenas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import smpvp.smpvp.SMPvp;
import smpvp.smpvp.events.playerkits.PlayerKitsEvents;
import smpvp.smpvp.inventories.InventoryData;

public class Arena {
    public int ID;
    public int currentPlayers;
    public String arenaName;
    public int maxPlayers;
    public List<Location> spawnLocations;
    public String kitName;
    public ArenaStatus status;
    public List<String> players = new ArrayList<String>();
    static SMPvp plugin = SMPvp.getInstance();

    public Arena(int ID, String Name, int maxplayers, List<Location> loc, String kit) {
        this.ID = ID;
        this.maxPlayers = maxplayers;
        this.arenaName = Name;
        this.spawnLocations = loc;
        this.kitName = kit;
        this.status = ArenaStatus.NOTSTARTED;
        //Bukkit.broadcastMessage("TWORZE ARENE "+ID);

    }

    public Arena(int ID, String Name, int currentPlayers, int maxplayers, List<Location> loc, Player p, Player t) {
        this.ID = ID;
        this.maxPlayers = maxplayers;
        this.arenaName = Name;
        this.spawnLocations = loc;
        this.currentPlayers = currentPlayers;
        this.status = ArenaStatus.NOTSTARTED;
        startArena(p,t,Name);
        //Bukkit.broadcastMessage("TWORZE ARENE "+ID);
    }

    public void reset() {
        this.currentPlayers = 0;
        //Bukkit.broadcastMessage("Arena ID"+ID);
        ///Bukkit.broadcastMessage(String.valueOf(plugin.freearenas.getConfig().getInt(ID + ".currentplayer")));
        plugin.freearenas.getConfig().set(ID + ".currentplayer",0);
        plugin.freearenas.saveConfig();
        //Bukkit.broadcastMessage(String.valueOf(plugin.freearenas.getConfig().getInt(ID + ".currentplayer")));
        players.clear();
        this.status = ArenaStatus.NOTSTARTED;
        //Bukkit.broadcastMessage("RESTARTUJE ARENE"+ID);
    }

    public void startArena(Player p, Player t,String invToGetName) {

            if (players.size() < maxPlayers) {
                //Bukkit.broadcastMessage(arenaName);
                //Bukkit.broadcastMessage("WARUNEK1:");
                //Bukkit.broadcastMessage("Liczba graczy na arenie:"+arena.players.size());
                status = ArenaStatus.STARTED;
                players.add(p.getName());
                players.add(t.getName());
                currentPlayers = 2;
                //Bukkit.broadcastMessage(String.valueOf(arena.currentPlayers));
                InventoryData.RestoreInventory(invToGetName,p,true);
                Inventory inv = InventoryData.inventories.get(p);
                //daj ekwipunek
                PlayerKitsEvents.getCustomKit(p, inv);
                PlayerKitsEvents.getCustomKit(t, inv);


                NewArenas.customArenas.put(p, this);
                NewArenas.customArenas.put(t, this);

                if (spawnLocations.size() < maxPlayers - 1) {
                    p.sendMessage(String.valueOf(spawnLocations.size()));
                    p.sendMessage("Brak dostępnych spawnów");
                } else {
                    for(int i=0; i<currentPlayers; i++){
                        if(i==0){
                            p.teleport((Location) spawnLocations.get(i));
                            p.setHealth(20.0D);
                        }
                        if(i==1){
                            t.teleport((Location) spawnLocations.get(i));
                            t.setHealth(20.0D);
                        }
                    }
                    /*Bukkit.broadcastMessage("Na arenie są:");
                    for (int i=0; i<arena.players.size(); i++){
                        Bukkit.broadcastMessage(arena.players.get(i));
                    }*/
                    Bukkit.broadcastMessage("§b" + p.getName() + "§7 dołączył do §b" + arenaName + "§4 " + players.size() + "/" + maxPlayers);

                }
            }
            }
        }