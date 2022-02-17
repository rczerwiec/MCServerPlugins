package sm.afterdeath;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Events implements Listener {

    @EventHandler
    public void ClearAfterDeath(PlayerDeathEvent e){
        Location loc = e.getEntity().getLocation();
        Player p = e.getEntity();
        List<ItemStack> itemList = Arrays.asList(e.getEntity().getInventory().getContents());
        int rand = new Random().nextInt(itemList.size()-1);
        int rand2 = new Random().nextInt(itemList.size()-1);
        int rand3 = new Random().nextInt(itemList.size()-1);
        int counter = 0;
        for (ItemStack item : e.getEntity().getInventory().getContents()) {
            counter++;
            if (item == null) {
                return;
            }
            else{
                if(counter == rand || counter == rand2 ||counter ==rand3){
                    if(!item.getType().equals(Material.AIR)){
                        loc.add(0.5,0.5,0.5);
                        p.sendMessage(ChatColor.BLUE+"Zgubiłeś: "+ item.getType().name() +" x"+item.getAmount());
                        p.getInventory().removeItem(item);
                        p.getWorld().dropItemNaturally(loc,item);

                    }
                }

            }
        }
        p.sendMessage(ChatColor.BLUE+"Przedmioty leżą na ziemi w miejscu Twojej śmierci!");

    }


}
