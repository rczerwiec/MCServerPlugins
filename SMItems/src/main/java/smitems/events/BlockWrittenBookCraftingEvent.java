package smitems.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class BlockWrittenBookCraftingEvent implements Listener {
    @EventHandler
    public void craftItem(PrepareItemCraftEvent e) {
        Material itemType;
        try{
            itemType = e.getRecipe().getResult().getType();
            Byte itemData = e.getRecipe().getResult().getData().getData();
            if(itemType== Material.WRITABLE_BOOK||itemType==Material.WRITTEN_BOOK && itemData==1) {
                e.getInventory().setResult(new ItemStack(Material.AIR));
                for(HumanEntity he:e.getViewers()) {
                    if(he instanceof Player) {
                        ((Player)he).sendMessage(ChatColor.RED+"Niestety, nie możesz tego stworzyć!");
                    }
                }
            }
        }
        catch(NullPointerException ex){
            return;
        }
    }
}
