package chaossmp.chaossmp;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class HeartsHandler implements Listener {

    @EventHandler
    public void playerKill(PlayerDeathEvent e){
        if(e.getEntity().getKiller() != null)
        {
            Player killer = e.getEntity().getKiller();
            Player player = e.getEntity();
            double killerHealth = (Objects.requireNonNull(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()/2 + 1);
            double playerHealth = (Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()/2 - 1);

            Bukkit.broadcastMessage("§4§l"+killer.getName()+"("+ killerHealth +") zabił "+player.getName()+" kradnąc mu serduszko" +"("+ playerHealth +")");
            addHearts(killer,2);
            removeHearts(player, 2);

        }
        else{
            Player player = e.getEntity();
            double playerHealth = (Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()/2 - 1);

            removeHearts(e.getEntity(),2);
            Bukkit.broadcastMessage("§4§l"+player.getName()+" umarł i stracił serduszko ("+ playerHealth +")");
        }
    }

    public void addHearts(Player player, int amount){
        if(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() < 80){

            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() + amount);
        }
    }

    public void removeHearts(Player player, int amount){
        if(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() <= 2){
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20);
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ltempban " + player.getName() + " 3d Straciłeś wszystkie serduszka, wróć za 3 dni!" );
        }
        else{
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() - amount);
        }

    }
}
