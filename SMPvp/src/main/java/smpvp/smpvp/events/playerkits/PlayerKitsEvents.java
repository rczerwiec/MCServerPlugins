package smpvp.smpvp.events.playerkits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import smpvp.smpvp.SMPvp;
import smpvp.smpvp.Statics;
import smpvp.smpvp.commands.openPlayerKitsList;
import smpvp.smpvp.inventories.InventoryData;
import smpvp.smpvp.kits.Kit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerKitsEvents implements Listener {
    SMPvp plugin = SMPvp.getInstance();
    Inventory newInv;



    @EventHandler
    public void onInventoryClick (InventoryClickEvent e) throws IOException {
        Inventory inv = openPlayerKitsList.inventories.get(e.getWhoClicked());
        if (e.getInventory() != inv) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        Player p = (Player) e.getWhoClicked();
        List<String> mykits = plugin.kitlist.getConfig().getStringList(p.getName()+".name");

        if(e.getRawSlot() < mykits.size()){
            //p.sendMessage("Kliknąłeś slot " + e.getRawSlot());
            p.closeInventory();
            InventoryData.RestoreInventory(clickedItem.getItemMeta().getDisplayName(),p,true);
            newInv = InventoryData.inventories.get(p);
            p.openInventory(newInv);
        }

        //p.sendMessage("Kliknąłeś slot " + e.getRawSlot());
    }

    @EventHandler
    public void onEditInventory (InventoryClickEvent e) throws IOException {
        newInv = InventoryData.inventories.get(e.getView().getPlayer());
        if (e.getInventory() != newInv) return;

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
            InventoryData inventoryData = new InventoryData(e.getView().getTitle(),e.getInventory(),p,true,true);
            p.closeInventory();
            p.sendMessage("ekwipunek playerkitevents");
        }
        else if(e.getRawSlot() == Statics.GETKIT_BUTTON){
            //Nadaj kit
            getCustomKit(p,newInv);
        }

        else if(e.getRawSlot() == Statics.DELETE_BUTTON){
            InventoryData.RemoveKit(e.getView().getTitle(),p);
            p.closeInventory();
        }

        else if(e.getRawSlot() == Statics.CANCEL_BUTTON){
            p.closeInventory();
        }

        //p.sendMessage("Kliknąłeś slot " + e.getRawSlot());
        }



    public static Kit getCustomKit(Player p,Inventory inv) {
        PlayerInventory playerInv = p.getInventory();
        List<ItemStack> inventoryList = new ArrayList<>();

        for (int i=Statics.EQREST_MIN; i<Statics.EQREST_MAX; i++){
            if(inv.getItem(i) != null){
                inventoryList.add(inv.getItem(i));
                //Bukkit.broadcastMessage(inv.getItem(i).toString());
            }

        }

        return new Kit(playerInv,inv.getItem(12), inv.getItem(9), inv.getItem(10), inv.getItem(11),inventoryList);
    }


}
