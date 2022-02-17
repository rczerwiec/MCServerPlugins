package smpvp.smpvp.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import smpvp.smpvp.SMPvp;
import smpvp.smpvp.arenas.ArenaManager;
import smpvp.smpvp.arenas.NewArenas;
import smpvp.smpvp.commands.PvpGUI;
import smpvp.smpvp.commands.openPlayerKitGui;

import java.io.IOException;

public class MainGUIEvents implements Listener {
    SMPvp plugin = SMPvp.getInstance();
    //cancel drag
    @EventHandler
    public void onInventoryDrag (InventoryDragEvent e){
        Inventory inv = openPlayerKitGui.inventories.get(e.getWhoClicked());
        if(e.getInventory() == inv){
            if(Integer.parseInt(e.getInventorySlots().toString()) > 0){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick (InventoryClickEvent e) throws IOException {
        Inventory inv = PvpGUI.inventories.get(e.getWhoClicked());
        if (e.getInventory() != inv) return;
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        final Player p = (Player) e.getWhoClicked();




        if(e.getRawSlot() == 11){
            //Przenieś do trybu tworzenia x:-18 y:199 z:-239
            if(NewArenas.playerIsInCustomArena(p)!=null) {
                p.sendMessage("§4§lNie możesz tego użyć podczas walki");
                return;
            }
            else if(ArenaManager.playerIsInArena(p)) {
                p.sendMessage("§4§lNie możesz tego użyć podczas walki|zwykla");
                return;
            }
            else{
                p.teleport(new Location(p.getWorld(),-18,199,-239));
            }

        }
        if(e.getRawSlot() == 12){
            ((Player) e.getWhoClicked()).performCommand("kit");
        }
        if(e.getRawSlot() == 13){
            ((Player) e.getWhoClicked()).performCommand("mojekity");
        }
        if(e.getRawSlot() == 14){
            ((Player) e.getWhoClicked()).sendMessage("§a§lWpisz §4§l/pojedynek <nick>");
        }
        if(e.getRawSlot() == 15){
            p.setHealth(0);
            ArenaManager.resetPlayer(p);
        }
        else if(e.getRawSlot() == 26){
            p.closeInventory();
        }


        //p.sendMessage("Kliknąłeś slot " + e.getRawSlot());
    }
}
