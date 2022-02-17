package banknotysm.events;

import banknotysm.BanknotySM;
import banknotysm.configs.Config;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;


public class SellBanknotOnCrouchRightclick implements Listener {


    @EventHandler
    public void OnCrouchRightclick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (!player.isSneaking()) return;
        if (!action.equals(Action.RIGHT_CLICK_AIR)) return;
        if (player.getInventory().getItemInMainHand().getType() != Material.PAPER) return;

        String lore = "";
        try {
            lore = Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta().getLore()).get(0);
        } catch (Exception e) {
            return;
        } //err item has no lore

        Config config = BanknotySM.getInternalConfig();

        String mess = ChatColor.translateAlternateColorCodes('&', config.getSellBanknotMessage()).replaceAll("%PLAYER%", player.getDisplayName());
        ItemStack item = player.getInventory().getItemInMainHand();
        int howmany = item.getAmount();
        int price = 0;

        try {
            price = Integer.parseInt(lore.replaceAll("\\D+", ""));
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Twój banknot nic nie kosztował??");
            return;
        }  //err nigdy nie powinien się wywołać

        event.getItem().setAmount(0);
        BanknotySM.getEconomy().depositPlayer(player, howmany * price);
        player.sendMessage(ChatColor.AQUA + mess);

    }
}


