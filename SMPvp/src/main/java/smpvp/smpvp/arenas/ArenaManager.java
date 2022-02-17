package smpvp.smpvp.arenas;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import smpvp.smpvp.SMPvp;
import smpvp.smpvp.Statics;
import smpvp.smpvp.events.playerkits.PlayerKitsEvents;
import smpvp.smpvp.inventories.InventoryData;
import smpvp.smpvp.kits.Kit;

public class ArenaManager {
    public static HashMap<String, Arena> arenas = new HashMap();
    public static HashMap<String, Arena> playersInArenas = new HashMap();
    public static HashMap<Arena, Sign> arenasSigns = new HashMap();
    private static SMPvp plugin = SMPvp.getInstance();


    public static Arena createArena(int ID, String name, int maxPlayers, List<Location> loc, String kit) {
        Arena arena = new Arena(ID, name, maxPlayers, loc, kit);
        arenas.put(name, arena);
        return arena;
    }

    public static Arena createArena(int ID, String name,int currentPlayers, int maxPlayers, List<Location> loc,Player p, Player t) {
        Arena arena = new Arena(ID, name,currentPlayers,maxPlayers,loc, p, t);
        arenas.put(name, arena);
        return arena;
    }

    public static Arena getFreeArena(String arenaName){
        Location loc = null;
        boolean found = false;
        for(int i = 0; i < plugin.freearenas.getConfig().getKeys(false).size(); i++) {
            //Bukkit.broadcastMessage("Sprawdzam arene:"+i);
            float yaw = (float)plugin.freearenas.getConfig().getLong(i + ".look");
            int currentPlayer = plugin.freearenas.getConfig().getInt(i + ".currentplayer");
            World world = Bukkit.getWorld("world");
            List<Location> allSpawnLocations = new ArrayList();
            List<String> locationList = plugin.freearenas.getConfig().getStringList(i + ".location");

            for(Iterator var8 = locationList.iterator(); var8.hasNext(); yaw = -yaw) {
                String s = (String)var8.next();
                String x = s.split("\\.")[0];
                String y = s.split("\\.")[1];
                String z = s.split("\\.")[2];
                //Bukkit.broadcastMessage(String.valueOf(yaw));
                allSpawnLocations.add(new Location(world, (double)Integer.parseInt(x), (double)Integer.parseInt(y), (double)Integer.parseInt(z), yaw, 0.0F));
                Bukkit.getLogger().info(x + " " + y + " " + z);
            }
            int maxPlayers = plugin.freearenas.getConfig().getInt(i + ".maxplayers");
            if(currentPlayer==0){
                found= true;
                //Bukkit.broadcastMessage("Znaleziono wolną arene nr"+i);
                plugin.freearenas.getConfig().set(i + ".currentplayer",2);
                plugin.freearenas.saveConfig();
                Arena arena = createArena(i,arenaName,2,allSpawnLocations,arenaName);
                return arena;
            }
            else{
                //Bukkit.broadcastMessage("Arena jest zajęta");
            }
        }
        if(!found){
            //Bukkit.broadcastMessage("Nie znaleziono wolnej areny!");
        }
        return null;
    }

    public static Kit getCustomKit(Player p, Inventory inv) {
        PlayerInventory playerInv = p.getInventory();
        List<ItemStack> inventoryList = new ArrayList<>();

        for (int i = Statics.EQREST_MIN; i<Statics.EQREST_MAX; i++){
            if(inv.getItem(i) != null){
                inventoryList.add(inv.getItem(i));
                //Bukkit.broadcastMessage(inv.getItem(i).toString());
            }

        }
        return new Kit(playerInv,inv.getItem(12), inv.getItem(9), inv.getItem(10), inv.getItem(11),inventoryList);
    }



    public static boolean joinArena(Player p, String arenaName, Sign sign_){
        Arena arena;
        Inventory inv;
        if(arenas.containsKey(arenaName)){
            arena = arenas.get(arenaName);
            if(arena.status == ArenaStatus.STARTED){
                p.sendMessage("§4§lArena jest już pełna!");
                return false;
            }
            //Bukkit.broadcastMessage("Dołączam do istniejącej już areny:"+arena.arenaName + arena.ID);
            InventoryData.RestoreInventory(arenaName,p,false);
            inv = InventoryData.arenasinventories.get(arenaName);
        }
        else{
            arena = getFreeArena(arenaName);
            //Bukkit.broadcastMessage("Tworze arene,"+p.getName()+" dołącza jako pierwszy");
            if(arena.status == ArenaStatus.STARTED){
                p.sendMessage("§4§lArena jest już pełna!");
                return false;
            }
            arenas.put(arenaName,arena);
            InventoryData.RestoreInventory(arenaName,p,false);
            inv = InventoryData.arenasinventories.get(arenaName);
        }

            if(arena.players.size() == 0){
                //Bukkit.broadcastMessage("WARUNEK1:");
                //Bukkit.broadcastMessage("Liczba graczy na arenie:"+arena.players.size());
                arena.status = ArenaStatus.WAITING;
                arenasSigns.put(arena,sign_);
                arena.players.add(p.getName());
                ++arena.currentPlayers;
                //Bukkit.broadcastMessage(String.valueOf(arena.currentPlayers))
                //player conf
                //p.sendMessage("Otrzymales ekwipunek g1");
                getCustomKit(p,inv);
                playersInArenas.put(p.getName(), arena);
                if (arena.spawnLocations.size() < arena.maxPlayers -1) {
                    p.sendMessage(String.valueOf(arena.spawnLocations.size()));
                    p.sendMessage("Brak dostępnych spawnów");
                    return false;}
                 else {
                    p.teleport((Location)arena.spawnLocations.get(arena.currentPlayers-1));
                    p.setHealth(20.0D);
                    /*Bukkit.broadcastMessage("Na arenie są:");
                    for (int i=0; i<arena.players.size(); i++){
                        Bukkit.broadcastMessage(arena.players.get(i));
                    }*/
                    Bukkit.broadcastMessage("§b" + p.getName() + "§7 dołączył do §b" + arenaName + "§4 " + arena.players.size() + "/" + arena.maxPlayers);
                    sign_.setLine(2, arena.players.size() + "/" + arena.maxPlayers);
                    sign_.update();

                }
            }
            else if(arena.players.size() < arena.maxPlayers){
                //Bukkit.broadcastMessage("WARUNEK2:");
                //Bukkit.broadcastMessage("Liczba graczy na arenie:"+arena.players.size());
                //p.sendMessage("Otrzymales ekwipunek g2");
                getCustomKit(p,inv);
                arena.players.add(p.getName());
                playersInArenas.put(p.getName(), arena);

                ++arena.currentPlayers;
                //Bukkit.broadcastMessage(String.valueOf(arena.currentPlayers));
                if (arena.spawnLocations.size() < arena.maxPlayers - 1) {
                    p.sendMessage(String.valueOf(arena.spawnLocations.size()));
                    p.sendMessage("Brak dostępnych spawnów");
                    return false;}
                else {
                    p.teleport((Location)arena.spawnLocations.get(arena.currentPlayers-1));
                    p.setHealth(20.0D);
                    /*Bukkit.broadcastMessage("Na arenie są:");
                    for (int i=0; i<arena.players.size(); i++){
                        Bukkit.broadcastMessage(arena.players.get(i));
                    }*/
                    Bukkit.broadcastMessage("§b" + p.getName() + "§7 dołączył do §b" + arenaName + "§4 " + arena.players.size() + "/" + arena.maxPlayers);
                    sign_.setLine(2, arena.players.size() + "/" + arena.maxPlayers);
                    sign_.update();

                if (arena.currentPlayers == arena.maxPlayers) {
                    arena.status = ArenaStatus.STARTED;
                }
            }
    }return true;
        }

    public static String arenaUpdate(Player p) {
        Arena arena = (Arena)playersInArenas.get(p.getName());
        //Bukkit.broadcastMessage("Aktualne areny:");
        //Bukkit.broadcastMessage(arenas.toString());
        arenas.remove(arena.arenaName);
        if (arena.players.size() > 0){
            for (int i=0; i<arena.players.size(); i++){
                //Bukkit.broadcastMessage("Restartuje itemki gracza:"+arena.players.get(i));
                try {
                    resetPlayer(Bukkit.getPlayer(arena.players.get(i)));
                    //Bukkit.broadcastMessage("Usuwam arene z listy aren:"+arena.ID);
                    //Bukkit.broadcastMessage("Aktualne areny po usunięciu:");
                    //Bukkit.broadcastMessage(arenas.toString());
                    playersInArenas.remove(Bukkit.getPlayer(arena.players.get(i)).getName());
                }
                catch(NullPointerException err){
                    //Bukkit.broadcastMessage("Nie wykryto gracza");
                }

                //resetPlayer(Bukkit.getPlayer(arena.players.get(i)));
            }
            arena.reset();
            //Bukkit.broadcastMessage("Powinno ustawic 0 dla areny"+arena.arenaName);
            Sign sign = (Sign)arenasSigns.get(arena);
            sign.setLine(2, arena.currentPlayers + "/" + arena.maxPlayers);
            sign.update();
            return arena.arenaName;
        }
        return arena.arenaName;
    }

    public static String customArenaUpdate(Player p) {
        Arena arena = NewArenas.customArenas.get(p);
        //Bukkit.broadcastMessage("Restartuje customową arene");
        if (arena.players.size() > 0){
            for (int i=0; i<arena.players.size(); i++){
                //Bukkit.broadcastMessage("Restartuje itemki gracza:"+arena.players.get(i));
                try {
                    resetPlayer(Bukkit.getPlayer(arena.players.get(i)));
                    //Bukkit.broadcastMessage(Bukkit.getPlayer(arena.players.get(i)).getName());
                    NewArenas.customArenas.remove(Bukkit.getPlayer(arena.players.get(i)),arena);
                }
                catch(NullPointerException err){
                    //Bukkit.broadcastMessage("Nie wykryto gracza");
                }

                //resetPlayer(Bukkit.getPlayer(arena.players.get(i)));
            }
            arena.reset();
            //Bukkit.broadcastMessage("Powinno ustawic 0 dla areny"+arena.arenaName);
            return arena.arenaName;
        }
        return arena.arenaName;
    }

    public static void resetPlayer(Player p) {
        p.setHealth(20.0D);
        p.setFoodLevel(20);
        //Bukkit.broadcastMessage(p.getName()+"został zabity");
        p.getInventory().clear();
        Iterator var2 = p.getActivePotionEffects().iterator();

        while(var2.hasNext()) {
            PotionEffect t = (PotionEffect)var2.next();
            p.removePotionEffect(t.getType());
        }

        Location loc = p.getWorld().getSpawnLocation();
        loc.setYaw(180.0F);
        p.teleport(loc);
    }


    public static boolean playerIsInArena(Player p) {
        //Bukkit.broadcastMessage("ZWYKLA:");
        /*for (Map.Entry me : playersInArenas.entrySet()) {
            Bukkit.broadcastMessage("Key: "+me.getKey() + " & Value: " + me.getValue());
        }*/

        if(playersInArenas.get(p.getName())!= null)
        {
            return true;
        }
        else return false;



    }
}