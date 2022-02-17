package pl.stylowamc.commands;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.stylowamc.Events;
import pl.stylowamc.Stylowamc;
import pl.stylowamc.Data.CooldownManager;
import pl.stylowamc.Data.PlayerData;
import pl.stylowamc.Data.PlayerManager;

public class Stats implements CommandExecutor {
    private final CooldownManager cooldownManager = new CooldownManager();
    private final Stylowamc plugin;

    public Stats(Stylowamc plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            int timeLeft = this.cooldownManager.getCooldown(p.getUniqueId());
            if (timeLeft == 0) {
                PlayerData pstats;
                if (args.length == 0) {
                    pstats = PlayerManager.getPlayer(p.getName());
                    Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                        try {
                            this.plugin.UpdateDatabase(p);
                        } catch (SQLException var3) {
                            var3.printStackTrace();
                        }

                    });

                    try {
                        HashMap<String, Integer> rank = Stylowamc.getPlayerRanking(p);
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "SMStats - Statystyki");
                        p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Gracz: " + ChatColor.LIGHT_PURPLE + p.getName());
                        int rank1 = (Integer)rank.get("Wykopane_Bloki");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wykopane bloki: " + ChatColor.GRAY + pstats.BlocksDestroyed + ChatColor.GOLD + " (TOP " + rank1 + ")");
                        rank1 = (Integer)rank.get("Postawione_Bloki");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Polozone bloki: " + ChatColor.GRAY + pstats.BlockPlaced + ChatColor.GOLD + " (TOP " + rank1 + ")");
                        rank1 = (Integer)rank.get("Wykopane_Zelazo");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wykopane zelazo: " + ChatColor.GRAY + pstats.IronDestroyed + ChatColor.GOLD + " (TOP " + rank1 + ")");
                        rank1 = (Integer)rank.get("Wykopane_Zloto");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wykopane zloto: " + ChatColor.GRAY + pstats.GoldDestroyed + ChatColor.GOLD + " (TOP " + rank1 + ")");
                        rank1 = (Integer)rank.get("Wykopane_Diamenty");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wykopane diamenty: " + ChatColor.GRAY + pstats.DiamondDestroyed + ChatColor.GOLD + " (TOP " + rank1 + ")");
                        rank1 = (Integer)rank.get("Wykopany_Netherite");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wykopane netherite: " + ChatColor.GRAY + pstats.NetheriteDestroyed + ChatColor.GOLD + " (TOP " + rank1 + ")");
                        rank1 = (Integer)rank.get("Zabojstwa");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Zabojstwa: " + ChatColor.GRAY + pstats.Kills + ChatColor.GOLD + " (TOP " + rank1 + ")");
                        rank1 = (Integer)rank.get("Smierci");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Smierci: " + ChatColor.GRAY + pstats.DeathCount + ChatColor.GOLD + " (TOP " + rank1 + ")");
                        rank1 = (Integer)rank.get("Zabite_Potwory");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Zabite moby: " + ChatColor.GRAY + pstats.MobsKilled + ChatColor.GOLD + " (TOP " + rank1 + ")");
                        rank1 = (Integer)rank.get("Zabite_Withery");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Zabite withery: " + ChatColor.GRAY + pstats.KilledWithers + ChatColor.GOLD + " (TOP " + rank1 + ")");
                        rank1 = (Integer)rank.get("Zabite_Smoki");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Zabite smoki: " + ChatColor.GRAY + pstats.KilledDragons + ChatColor.GOLD + " (TOP " + rank1 + ")");
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Spedzone minuty na serwerze: " + ChatColor.GRAY + PlayerManager.getTimeplayed(p.getName()));
                    } catch (SQLException var10) {
                        var10.printStackTrace();
                    }
                } else if (args.length == 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "SMStats - Statystyki");
                        p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Gracz: " + ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(args[0]).getName());
                        pstats = PlayerManager.getPlayer(Bukkit.getPlayer(args[0]).getName());
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wykopane bloki: " + ChatColor.GRAY + pstats.BlocksDestroyed);
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Polozone bloki: " + ChatColor.GRAY + pstats.BlockPlaced);
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wykopane zelazo: " + ChatColor.GRAY + pstats.IronDestroyed);
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wykopane zloto: " + ChatColor.GRAY + pstats.GoldDestroyed);
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wykopane diamenty: " + ChatColor.GRAY + pstats.DiamondDestroyed);
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wykopane netherite: " + ChatColor.GRAY + pstats.NetheriteDestroyed);
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Zabojstwa: " + ChatColor.GRAY + pstats.Kills + ChatColor.GOLD);
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Smierci: " + ChatColor.GRAY + pstats.DeathCount + ChatColor.GOLD);
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Zabite moby: " + ChatColor.GRAY + pstats.MobsKilled + ChatColor.GOLD);
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Zabite withery: " + ChatColor.GRAY + pstats.KilledWithers + ChatColor.GOLD);
                        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Zabite smoki: " + ChatColor.GRAY + pstats.KilledDragons + ChatColor.GOLD);
                    } else {
                        p.sendMessage("Gracz jest offline");
                    }
                } else {
                    Player p1;
                    if (args.length == 2 && args[1].equals("fix")) {
                        for(Iterator var7 = Bukkit.getOnlinePlayers().iterator(); var7.hasNext(); Events.getfromdatabase(p1.getName())) {
                            p1 = (Player)var7.next();
                            if (PlayerManager.getPlayer(p1.getName()) == null) {
                                PlayerManager.addPlayer(p1.getName());
                            }
                        }
                    }
                }

                this.cooldownManager.setCooldown(p.getUniqueId(), 2);
                (new BukkitRunnable() {
                    public void run() {
                        int timeLeft = Stats.this.cooldownManager.getCooldown(p.getUniqueId());
                        CooldownManager var10000 = Stats.this.cooldownManager;
                        UUID var10001 = p.getUniqueId();
                        --timeLeft;
                        var10000.setCooldown(var10001, timeLeft);
                        if (timeLeft == 0) {
                            this.cancel();
                        }

                    }
                }).runTaskTimer(this.plugin, 20L, 1200L);
            } else {
                p.sendMessage(ChatColor.RED.toString() + "Odczekaj " + timeLeft + " minute przed użyciem tej komendy.");
            }
        }

        return false;
    }
}