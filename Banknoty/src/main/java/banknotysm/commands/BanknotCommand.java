package banknotysm.commands;

import banknotysm.BanknotySM;
import banknotysm.configs.Config;
import banknotysm.util.BanknotyUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Objects;

public class BanknotCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Config config = BanknotySM.getInternalConfig();
        Player p = (Player) sender;
        PlayerInventory inv = p.getInventory();

        if (args.length == 0) {
            p.sendMessage(ChatColor.AQUA + "Poprawne uzycie: /banknoty kup/sprzedaj (liczba)");
            return false;
        } //err brak argumentów


        if (args[0].equalsIgnoreCase("kup")) {
            if (!p.hasPermission(BanknotyUtil.BANKNOT_GIVE_SELF_PERM)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.RED + config.getNoPermMessage()));
                return false;
            } //err brak permisji

            if (args.length < 2) {
                p.sendMessage(ChatColor.RED + "Podaj cenę!");
                return false;
            } // err brak podanej ceny

            if (inv.firstEmpty() == -1) {
                p.sendMessage(ChatColor.RED + "Nie masz wolnego miejsca w ekwipunku!");
                return false;
            } //err no inv space

            int price = 0;

            try {
                price = Integer.parseInt(args[1]);
                if (price < 1) throw new Exception();
            } catch (Exception ee) {
                p.sendMessage(ChatColor.RED + "Podaj dodatnią liczbę jako cenę!");
                return false;
            } //err nie numer jako argument

            if (BanknotySM.getEconomy().getBalance(p) < price) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.RED + config.getNoMoneyMessage()));
                return false;
            } //err nie stać

            ItemStack banknot = BanknotyUtil.createBanknot(price);
            inv.addItem(banknot);
            BanknotySM.getEconomy().withdrawPlayer(p, price);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.AQUA + config.getBuyBanknotMessage()).replaceAll("%PLAYER%", p.getDisplayName()));
            return true;
        }

        if (args[0].equalsIgnoreCase("sprzedaj")) {
            String lore = "";
            try {
                lore = Objects.requireNonNull(p.getInventory().getItemInMainHand().getItemMeta().getLore()).get(0);
                if (!p.getInventory().getItemInMainHand().getType().toString().equals("PAPER")) throw new Exception();
            } catch (Exception e) {

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.RED + config.getNoBanknotInHandMessage()));
                return false;
            } //err item has no lore


            String mess = ChatColor.translateAlternateColorCodes('&', config.getSellBanknotMessage()).replaceAll("%PLAYER%", p.getDisplayName());
            ItemStack item = p.getInventory().getItemInMainHand();
            int howmany = item.getAmount();
            int price = 0;

            try {
                price = Integer.parseInt(lore.replaceAll("\\D+", ""));
            } catch (Exception e) {
                p.sendMessage(ChatColor.RED + "Twój banknot nic nie kosztował??");
            }  //err nigdy nie powinien się wywołać

            inv.removeItem(item);
            BanknotySM.getEconomy().depositPlayer(p, howmany * price);
            p.sendMessage(ChatColor.AQUA + mess);
            return true;

        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!p.hasPermission(BanknotyUtil.BANKNOT_RELOAD_PERM)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.RED + config.getNoPermMessage()));
                return false;
            } //err brak permisji

            config.reloadConfig();
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.AQUA + config.getReloadMessage()));

            return true;
        }

        p.sendMessage(ChatColor.AQUA + "Poprawne uzycie: /banknoty kup/sprzedaj (liczba)");
        return false;
    }
}

