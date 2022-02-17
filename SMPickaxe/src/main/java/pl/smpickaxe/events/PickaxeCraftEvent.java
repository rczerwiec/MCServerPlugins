package pl.smpickaxe.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.smpickaxe.loggers.CustomPickaxeCrafting;
import pl.smpickaxe.loggers.Platinum_Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PickaxeCraftEvent implements Listener {

    @EventHandler
    public void CraftCustomItem (CraftItemEvent e){
        ItemStack item = e.getRecipe().getResult();
        if(item.hasItemMeta()){
            try {
                Date now = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                CustomPickaxeCrafting log = new CustomPickaxeCrafting();
                //NIE DZIALA|String podpis = "#"+e.getView().getPlayer().getName()+now.getMinutes()+now.getHours()+now.getDay();
                String sum = "[" + format.format(now) + "]" + e.getView().getPlayer().getName() + " stworzył " + item.getItemMeta().getDisplayName();
                log.logToFile(sum);

            }
            catch(NullPointerException ex){
                //Blade
            }
        }


    }


}
