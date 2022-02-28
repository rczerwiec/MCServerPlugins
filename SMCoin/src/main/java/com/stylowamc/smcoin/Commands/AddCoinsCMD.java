package com.stylowamc.smcoin.Commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
import org.bukkit.command.CommandException;
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
        ItemStack coin = createCoin();
        ArrayList<Integer> serverWithCoins = new ArrayList<>();

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
        }
        else{
            Player p;
            String playerName;
            PreparedStatement stat;

            int coinsValue;
            PaymentsLogToFile log;
            if (args[0].equals("dodaj")) {
                if (args.length <= 2) {
                    if (sender instanceof Player) {
                        p = (Player)sender;
                        p.sendMessage("Ta komenda może zostać wykonana wyłącznie przez operatora serwera");
                    } else {
                        Bukkit.getLogger().info("Ta komenda może zostać wykonana wyłącznie przez operatora serwera");
                    }
                }
                else if (!(sender instanceof Player)) {
                    coinsValue = Integer.parseInt(args[1]);
                    playerName = args[2];
                    try {
                        //Jeżeli jest juz w bazie danych
                        if (this.isInDatabase(playerName)) {
                            stat = SMCoin.connection.prepareStatement("UPDATE coins SET Coins=Coins+?,CoinsSum=CoinsSum+? WHERE Name = ?");
                            stat.setInt(1, coinsValue);
                            stat.setInt(2, coinsValue);
                            stat.setString(3, playerName);
                            stat.executeUpdate();
                            log = new PaymentsLogToFile();
                            log.logToFile(playerName + " doładował coinsy w ilości:" + coinsValue);
                            Bukkit.getLogger().info(playerName + " doładował coinsy w ilości:" + coinsValue);

                        }
                        //Jeżeli gracz zakupił po raz pierwszy SMCoiny
                        else
                        {
                            stat = SMCoin.connection.prepareStatement("INSERT INTO coins(Name,Coins,CoinsSum) VALUES (?,?,?)");
                            stat.setString(1, playerName);
                            stat.setInt(2, coinsValue);
                            stat.setInt(3, coinsValue);
                            stat.executeUpdate();
                            Bukkit.getLogger().info(playerName + " doładował po raz pierwszy coinsy w ilości:" + coinsValue);
                            log = new PaymentsLogToFile();
                            log.logToFile(playerName + " doładował po raz pierwszy coinsy w ilości:" + coinsValue);
                            Bukkit.getLogger().info(playerName + " doładował po raz pierwszy coinsy w ilości:" + coinsValue);

                        }
                        this.UpdateGlobalStats(coinsValue);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    sender.sendMessage("Ta komenda może zostać wywołana jedynie z konsoli!");
                }
            }
            else if (args[0].equals("wyplac") && sender instanceof Player) {
                p = (Player)sender;
                coinsValue = 0;

                try {
                    stat = SMCoin.connection.prepareStatement("SELECT Coins FROM coins WHERE Name = ?");
                    stat.setString(1, p.getName());

                    //Do sprawdzenia
                    for(ResultSet result = stat.executeQuery(); result.next(); coinsValue = result.getInt("Coins")) {
                    }

                    if (coinsValue >= Integer.parseInt(args[1]) && Integer.parseInt(args[1]) > 0) {
                        check(p, coin, Integer.parseInt(args[1]));
                    } else {
                        if (Integer.parseInt(args[1]) == 0)  p.sendMessage("Wartość musi być większa niż 0!");
                        else p.sendMessage("Nie masz wystarczająco coinsów!");
                    }
                    log = new PaymentsLogToFile();
                    log.logToFile(p.getName() + " wypłacił coinsy w ilości:" + args[1]);
                    Bukkit.getLogger().info(p.getName() + " wypłacił coinsy w ilości:" + args[1]);
                } catch (SQLException | CommandException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    p.sendMessage("Podaj prawidłowe wartości!");
                }
            }
            else if (args[0].equals("info")) {
                //smc info Stylowy
                if (args.length >= 2) {
                    getPlayerStatistics(args[1],sender ,serverWithCoins);
                }
                //smc info
                else if (sender instanceof Player) {
                    getPlayerStatistics("-1",sender ,serverWithCoins);
                }
            }
        }
        return true;
    }
    public void getPlayerStatistics(String player,CommandSender sender, ArrayList<Integer> serverWithCoins){
        if (Objects.equals(player, "-1")){
            player = sender.getName();
        }

        if (this.isInDatabase(player)) {
            int coinsValue = 0;
            int coinsSum = 0;

            try {
                PreparedStatement stat = SMCoin.connection.prepareStatement("SELECT * FROM coins WHERE Name = ?");
                stat.setString(1, player);

                for(ResultSet result = stat.executeQuery(); result.next(); coinsSum = result.getInt("CoinsSum")) {
                    coinsValue = result.getInt("Coins");
                    ResultSetMetaData resultMeta = result.getMetaData();
                    for (int i=5; i<=resultMeta.getColumnCount(); i++){
                        int value = result.getInt(i);
                        serverWithCoins.add(value);
                    }
                }

                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "SMCoins");
                sender.sendMessage("●" + player + " posiada " + ChatColor.AQUA + coinsValue + ChatColor.WHITE + " SMCoinsów●");
                sender.sendMessage("●Razem zakupił już " + ChatColor.AQUA + coinsSum + ChatColor.WHITE +" SMCoinsów●");
                sender.sendMessage("●Coiny wydane na OneBlock S2 "+ ChatColor.AQUA + serverWithCoins.get(0));
                sender.sendMessage("●Coiny wydane na OneBlock S3 " + ChatColor.AQUA + serverWithCoins.get(1));
                sender.sendMessage("●Coiny wydane na CaveBlocku " + ChatColor.AQUA + serverWithCoins.get(2));
                sender.sendMessage("●Coiny wydane na MegaDropie " + ChatColor.AQUA + serverWithCoins.get(3));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            sender.sendMessage("Nie ma takiego gracza w bazie danych");
        }
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
                    player.getInventory().addItem(coin);
                    String server = config.getString("server");

                    PreparedStatement status = SMCoin.connection.prepareStatement("UPDATE coins SET `"+server+"`=`"+server+"`+? WHERE Name = ?");
                    status.setInt(1, coins);
                    status.setString(2, player.getName());
                    status.executeUpdate();
                    player.sendMessage("Wypłaciłeś " + coins + " SMCoinsów!");
                    return true;
                }
            } catch (SQLException | NumberFormatException | CommandException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            player.sendMessage("Zrób miejsce w ekwipunku!.");
            return false;
        }
    }

    //Tworzenie coina
    public ItemStack createCoin(){
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

        return coin;
    }

    //Sprawdza czy gracz istnieje w bazie danych
    boolean isInDatabase(String p) {
        int query_result = 0;

        try {
            PreparedStatement stat = SMCoin.connection.prepareStatement("SELECT COUNT(Name) AS Result FROM coins WHERE Name = ?");
            stat.setString(1, p);

            for(ResultSet result = stat.executeQuery(); result.next(); query_result = result.getInt("Result")) {
            }

            return query_result >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    //Aktualizuje informacje o SMCoinach w bazie danych
    void UpdateGlobalStats(int coinsAmount) {
        try {
            PreparedStatement stat = SMCoin.connection.prepareStatement("UPDATE info SET Coins=Coins+?");
            stat.setInt(1, coinsAmount);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
