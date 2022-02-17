package com.stylowamc.smcoin.Commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.stylowamc.smcoin.FileLoggers.PaymentsLogToFile;
import com.stylowamc.smcoin.SMCoin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddCoinsCMD implements CommandExecutor {
    SMCoin plugin = SMCoin.getInstance();
    private static FileConfiguration config;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ItemStack coin = new ItemStack((Material)Objects.requireNonNull(Material.getMaterial((String)Objects.requireNonNull(SMCoin.getConfigIstance().getString("SMC_material")))));
        ItemMeta meta = coin.getItemMeta();
        config = plugin.getConfig();
        List<String> lore = new ArrayList();
        lore.add(ChatColor.GRAY + "Oficjalna waluta " + ChatColor.YELLOW + "SM");
        lore.add(ChatColor.GRAY + "Ten" + ChatColor.GOLD + " Coin " + ChatColor.GRAY + "jest wart " + ChatColor.RED + "1zł");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "SM" + ChatColor.GOLD + "Coin");
        coin.addUnsafeEnchantment(Enchantment.LUCK, 1);
        coin.setItemMeta(meta);
        if (args.length < 1) {
            if (sender instanceof Player) {
                sender.sendMessage("Poprawne użycia:");
                sender.sendMessage("/smc wyplac <ilosc>");
                sender.sendMessage("/smc info");
                sender.sendMessage("/smc info <nick>");
            } else {
                Bukkit.getLogger().info("Poprawne użycia:");
                Bukkit.getLogger().info("/smc info");
                Bukkit.getLogger().info("/smc info <nick>");
            }
        } else {
            Player p;
            String playerName;
            PreparedStatement stat;
            int coinsValue;
            PaymentsLogToFile log;
            if (args[0].equals("dodaj")) {
                if (args.length <= 2) {
                    if (sender instanceof Player) {
                        p = (Player)sender;
                        p.sendMessage("Wprowadz wszystkie dane /smc dodaj <nick> <ilosc>");
                    } else {
                        Bukkit.getLogger().info("Wprowadz wszystkie dane /smc dodaj <nick> <ilosc>");
                    }
                } else if (!(sender instanceof Player)) {
                    coinsValue = Integer.parseInt(args[2]);
                    playerName = args[1];

                    try {
                        if (this.IsInDb(playerName)) {
                            stat = SMCoin.connection.prepareStatement("UPDATE coins SET Coins=Coins+?,CoinsSum=CoinsSum+? WHERE Name = ?");
                            stat.setInt(1, coinsValue);
                            stat.setInt(2, coinsValue);
                            stat.setString(3, playerName);
                            stat.executeUpdate();
                            log = new PaymentsLogToFile();
                            log.logToFile(playerName + " doładował coinsy w ilości:" + coinsValue);
                            Bukkit.getLogger().info(playerName + " doładował coinsy w ilości:" + coinsValue);
                            this.UpdateGlobalStats(coinsValue);

                            try {
                                if (((Player)Objects.requireNonNull(Bukkit.getPlayerExact(playerName))).isOnline()) {
                                    Bukkit.getPlayerExact(playerName).sendMessage("Doładowałeś coinsy w ilości:" + coinsValue);
                                }
                            } catch (NullPointerException var17) {
                                Bukkit.getLogger().info("Gracza aktualnie nie ma na serwerze");
                            }
                        } else {
                            stat = SMCoin.connection.prepareStatement("INSERT INTO coins(Name,Coins,CoinsSum) VALUES (?,?,?)");
                            stat.setString(1, playerName);
                            stat.setInt(2, coinsValue);
                            stat.setInt(3, coinsValue);
                            stat.executeUpdate();
                            Bukkit.getLogger().info(playerName + " doładował po raz pierwszy coinsy w ilości:" + coinsValue);
                            log = new PaymentsLogToFile();
                            log.logToFile(playerName + " doładował po raz pierwszy coinsy w ilości:" + coinsValue);
                            Bukkit.getLogger().info(playerName + " doładował po raz pierwszy coinsy w ilości:" + coinsValue);
                            this.UpdateGlobalStats(coinsValue);

                            try {
                                if (((Player)Objects.requireNonNull(Bukkit.getPlayerExact(playerName))).isOnline()) {
                                    Bukkit.getPlayerExact(playerName).sendMessage("Doładowałeś coinsy w ilości:" + coinsValue);
                                }
                            } catch (NullPointerException var16) {
                                Bukkit.getLogger().info("Gracza aktualnie nie ma na serwerze");
                            }
                        }
                    } catch (SQLException var18) {
                        var18.printStackTrace();
                    }
                } else {
                    sender.sendMessage("Musisz wykonać tą komendę z konsoli!");
                }
            } else if (args[0].equals("wyplac") && sender instanceof Player) {
                p = (Player)sender;
                coinsValue = 0;

                try {
                    stat = SMCoin.connection.prepareStatement("SELECT Coins FROM coins WHERE Name = ?");
                    stat.setString(1, p.getName());

                    for(ResultSet result = stat.executeQuery(); result.next(); coinsValue = result.getInt("Coins")) {
                    }

                    if (coinsValue >= Integer.parseInt(args[1]) && Integer.parseInt(args[1]) > 0) {
                        check(p, coin, Integer.parseInt(args[1]));
                    } else {
                        p.sendMessage("Nie masz wystarczająco coinów!");
                    }
                } catch (SQLException var21) {
                    var21.printStackTrace();
                }

                log = new PaymentsLogToFile();
                log.logToFile(p.getName() + " wypłacił coinsy w ilości:" + args[1]);
                Bukkit.getLogger().info(p.getName() + " wypłacił coinsy w ilości:" + args[1]);
            } else if (args[0].equals("info")) {
                int coinsSum;
                int ob3,ob2,cb1,md;
                if (args.length >= 2) {
                    if (this.IsInDb(args[1])) {
                        String name = args[1];
                        coinsValue = 0;
                        coinsSum = 0;
                        ob3 = 0;
                        ob2 = 0;
                        cb1 = 0;
                        md = 0;

                        try {
                            stat = SMCoin.connection.prepareStatement("SELECT Coins, CoinsSum, ob3, ob2, md, cb1 FROM coins WHERE Name = ?");
                            stat.setString(1, name);

                            for(ResultSet result = stat.executeQuery(); result.next(); coinsSum = result.getInt("CoinsSum")) {
                                coinsValue = result.getInt("Coins");
                                ob3 = result.getInt("ob3");
                                ob2  = result.getInt("ob2");
                                md = result.getInt("md");
                                cb1 = result.getInt("cb1");
                            }

                            if (sender instanceof Player) {
                                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "SMCoins");
                                sender.sendMessage("●" + name + " posiada " + ChatColor.AQUA + coinsValue + ChatColor.WHITE + " SMCoinsów●");
                                sender.sendMessage("●Razem zakupił już " + ChatColor.AQUA + coinsSum + ChatColor.WHITE +" SMCoinsów●");
                                sender.sendMessage("●Coiny wydane na OneBlock S2 "+ ChatColor.AQUA + ob2);
                                sender.sendMessage("●Coiny wydane na OneBlock S3 " + ChatColor.AQUA + ob3);
                                sender.sendMessage("●Coiny wydane na CaveBlocku " + ChatColor.AQUA + cb1);
                                sender.sendMessage("●Coiny wydane na MegaDropie " + ChatColor.AQUA + md);
                            } else {
                                Bukkit.getLogger().info(ChatColor.RED + "" + ChatColor.BOLD + "SMCoins");
                                Bukkit.getLogger().info("●" + name + " posiada " + ChatColor.AQUA + coinsValue + ChatColor.WHITE + " SMCoinsów●");
                                Bukkit.getLogger().info("●Razem zakupił już " + ChatColor.AQUA + coinsSum + ChatColor.WHITE + " SMCoinsów●");
                                Bukkit.getLogger().info("●Coiny wydane na OneBlock S2 " + ChatColor.AQUA + ob2);
                                Bukkit.getLogger().info("●Coiny wydane na OneBlock S3 " + ChatColor.AQUA + ob3);
                                Bukkit.getLogger().info("●Coiny wydane na CaveBlocku " + ChatColor.AQUA + cb1);
                                Bukkit.getLogger().info("●Coiny wydane na MegaDropie " + ChatColor.AQUA + md);
                            }
                        } catch (SQLException var19) {
                            var19.printStackTrace();
                        }
                    } else if (args.length <= 1) {
                        Bukkit.getLogger().info("Podaj nazwe gracza, którego chcesz sprawdzić");
                        if (sender instanceof Player) {
                            sender.sendMessage("Podaj nazwe gracza, którego chcesz sprawdzić");
                        }
                    } else if (sender instanceof Player) {
                        sender.sendMessage("Nie ma takiego gracza w bazie danych");
                    } else {
                        Bukkit.getLogger().info("Nie ma takiego gracza w bazie danych");
                    }
                } else if (sender instanceof Player) {
                    p = (Player)sender;
                    playerName = p.getName();
                    coinsSum = 0;
                    coinsValue = 0;
                    ob3 = 0;
                    ob2 = 0;
                    cb1 = 0;
                    md = 0;

                    try {
                        stat = SMCoin.connection.prepareStatement("SELECT Coins, CoinsSum, ob3, ob2, md, cb1 FROM coins WHERE Name = ?");
                        stat.setString(1, playerName);

                        for(ResultSet result = stat.executeQuery(); result.next(); coinsSum = result.getInt("CoinsSum")) {
                            coinsValue = result.getInt("Coins");
                            ob3 = result.getInt("ob3");
                            ob2  = result.getInt("ob2");
                            md = result.getInt("md");
                            cb1 = result.getInt("cb1");
                        }

                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "SMCoins");
                        p.sendMessage("●Posiadasz " + ChatColor.AQUA + coinsValue + ChatColor.WHITE + " SMCoinsów●");
                        p.sendMessage("●Razem zakupiłeś już " + ChatColor.AQUA + coinsSum + ChatColor.WHITE + " SMCoinsów●");
                        p.sendMessage("●Coiny wydane na OneBlock S2 " + ChatColor.AQUA + ob2);
                        p.sendMessage("●Coiny wydane na OneBlock S3 " + ChatColor.AQUA + ob3);
                        p.sendMessage("●Coiny wydane na CaveBlocku " + ChatColor.AQUA + cb1);
                        p.sendMessage("●Coiny wydane na MegaDropie " + ChatColor.AQUA + md);

                    } catch (SQLException var20) {
                        var20.printStackTrace();
                    }
                }
            }
        }

        return true;
    }

    public static boolean check(Player player, ItemStack coin, int coins) {
        if (player.getInventory().firstEmpty() != -1) {
            try {
                if (coins > 64) {
                    player.sendMessage("Nie możesz wypłacić więcej niż 64 coiny!");
                    return false;
                } else {
                    PreparedStatement stat = SMCoin.connection.prepareStatement("UPDATE coins SET Coins=Coins-? WHERE Name = ?");
                    stat.setInt(1, coins);
                    stat.setString(2, player.getName());
                    stat.executeUpdate();
                    coin.setAmount(coins);
                    player.getInventory().addItem(new ItemStack[]{coin});
                    if(Objects.equals(config.getString("server"), "ob3"))
                    {
                        PreparedStatement status = SMCoin.connection.prepareStatement("UPDATE coins SET ob3=ob3+? WHERE Name = ?");

                        status.setInt(1, coins);
                        status.setString(2, player.getName());
                        status.executeUpdate();
                    }
                    if(Objects.equals(config.getString("server"), "ob2"))
                    {
                        PreparedStatement status = SMCoin.connection.prepareStatement("UPDATE coins SET ob2=ob2+? WHERE Name = ?");

                        status.setInt(1, coins);
                        status.setString(2, player.getName());
                        status.executeUpdate();
                    }
                    if(Objects.equals(config.getString("server"), "md"))
                    {
                        PreparedStatement status = SMCoin.connection.prepareStatement("UPDATE coins SET md=md+? WHERE Name = ?");

                        status.setInt(1, coins);
                        status.setString(2, player.getName());
                        status.executeUpdate();
                    }
                    if(Objects.equals(config.getString("server"), "cb1"))
                    {
                        PreparedStatement status = SMCoin.connection.prepareStatement("UPDATE coins SET cb1=cb1+? WHERE Name = ?");

                        status.setInt(1, coins);
                        status.setString(2, player.getName());
                        status.executeUpdate();
                    }
                    player.sendMessage("Wypłaciłeś " + coins + " SMCoinsów!");
                    return true;
                }
            } catch (SQLException var4) {
                var4.printStackTrace();
                return false;
            }
        } else {
            player.sendMessage("Zrób miejsce w ekwipunku!.");
            return false;
        }
    }

    boolean IsInDb(String p) {
        int query_result = 0;

        try {
            PreparedStatement stat = SMCoin.connection.prepareStatement("SELECT COUNT(Name) AS Result FROM coins WHERE Name = ?");
            stat.setString(1, p);

            for(ResultSet result = stat.executeQuery(); result.next(); query_result = result.getInt("Result")) {
            }

            return query_result >= 1;
        } catch (SQLException var5) {
            var5.printStackTrace();
            return true;
        }
    }

    void UpdateGlobalStats(int coinsAmount) {
        try {
            PreparedStatement stat = SMCoin.connection.prepareStatement("UPDATE info SET Coins=Coins+?");
            stat.setInt(1, coinsAmount);
            stat.executeUpdate();
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }
}
