package smpvp.smpvp.events.playerkits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import smpvp.smpvp.SMPvp;
import smpvp.smpvp.Statics;
import smpvp.smpvp.commands.openPlayerKitGui;
import smpvp.smpvp.inventories.InventoryData;

import java.io.IOException;
import java.util.List;

public class NewKitsEvents implements Listener {

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
        Inventory inv = openPlayerKitGui.inventories.get(e.getWhoClicked());
        if (e.getInventory() != inv) return;
        if(e.getRawSlot() < Statics.EQUIPABLE_SLOTS || e.getRawSlot() == 18 || e.getRawSlot() == 43 || e.getRawSlot() == 44 ||
                (e.getRawSlot() >= 4 && e.getRawSlot() <=8) ||
                (e.getRawSlot() >= 13 && e.getRawSlot() <=17) ||
                (e.getRawSlot() >= 36 && e.getRawSlot() <43))
        {

            e.setCancelled(true);
        }
        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        final Player p = (Player) e.getWhoClicked();




        if(e.getRawSlot() == Statics.SAVE_BUTTON){
            String kitName = p.getName()+"new";
            if(plugin.kitlist.getConfig().contains(p.getName()+".name")){
                List<String> ConfigList = plugin.kitlist.getConfig().getStringList(p.getName()+".name");
                kitName = p.getName()+ ConfigList.size();
            }
            new InventoryData(kitName,e.getInventory(),p,false,true);
            p.closeInventory();
        }
        else if(e.getRawSlot() == Statics.CANCEL_BUTTON){
            p.closeInventory();
        }


        p.sendMessage("Klikn????e?? slot " + e.getRawSlot());
    }

}
