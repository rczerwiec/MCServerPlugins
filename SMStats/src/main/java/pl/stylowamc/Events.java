package pl.stylowamc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import pl.stylowamc.Data.PlayerData;
import pl.stylowamc.Data.PlayerManager;

public class Events implements Listener {
    private static Stylowamc plugin = null;
    PlayerManager playermanager = new PlayerManager();

    public Events(Stylowamc plugin) {
        Events.plugin = plugin;
    }

    public static void getfromdatabase(String p) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            PlayerData stats = PlayerManager.getPlayer(p);

            try {
                Statement st = Stylowamc.connection.createStatement();
                String sql = "SELECT * FROM Statystyki WHERE Name = '" + p + "'";
                ResultSet rs = st.executeQuery(sql);
                if (rs.next()) {
                    int wb = rs.getInt("Wykopane_Bloki");
                    stats.BlocksDestroyed = wb;
                    wb = rs.getInt("Postawione_Bloki");
                    stats.BlockPlaced = wb;
                    wb = rs.getInt("Wykopany_Netherite");
                    stats.NetheriteDestroyed = wb;
                    wb = rs.getInt("Wykopane_Diamenty");
                    stats.DiamondDestroyed = wb;
                    wb = rs.getInt("Wykopane_Zloto");
                    stats.GoldDestroyed = wb;
                    wb = rs.getInt("Wykopane_Zelazo");
                    stats.IronDestroyed = wb;
                    wb = rs.getInt("Smierci");
                    stats.DeathCount = wb;
                    wb = rs.getInt("Zabojstwa");
                    stats.Kills = wb;
                    wb = rs.getInt("Zabite_Potwory");
                    stats.MobsKilled = wb;
                    wb = rs.getInt("Zabite_Withery");
                    stats.KilledWithers = wb;
                    wb = rs.getInt("Zabite_Smoki");
                    stats.KilledDragons = wb;
                    wb = rs.getInt("Przegrane_Minuty");
                    stats.minuty = wb;
                }

            } catch (SQLException var6) {
            }
        });
    }

    @EventHandler
    public void BlockPlace(BlockPlaceEvent e) {
        if (!e.isCancelled()) {
            ++PlayerManager.getPlayer(e.getPlayer().getName()).BlockPlaced;
        }

    }

    @EventHandler
    public void MobKill(EntityDeathEvent e) {
        Player p = e.getEntity().getKiller();
        if (e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player) {
            ++PlayerManager.getPlayer(p.getName()).MobsKilled;
            if (e.getEntityType() == EntityType.WITHER) {
                ++PlayerManager.getPlayer(p.getName()).KilledWithers;
            } else if (e.getEntityType() == EntityType.ENDER_DRAGON) {
                ++PlayerManager.getPlayer(p.getName()).KilledDragons;
            }
        }

    }

    @EventHandler
    public void OnDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        ++PlayerManager.getPlayer(p.getName()).DeathCount;
        Player killer = e.getEntity().getKiller();
        if (killer != null) {
            ++PlayerManager.getPlayer(killer.getName()).Kills;
        }

    }

    @EventHandler
    public void BlockDestroy(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if ((double)e.getBlock().getType().getHardness() > 0.2D && !e.isCancelled()) {
            ++PlayerManager.getPlayer(p.getName()).BlocksDestroyed;
            if (e.getBlock().getType() == Material.DIAMOND_ORE) {
                ++PlayerManager.getPlayer(p.getName()).DiamondDestroyed;
            } else if (e.getBlock().getType() == Material.GOLD_ORE) {
                ++PlayerManager.getPlayer(p.getName()).GoldDestroyed;
            } else if (e.getBlock().getType() == Material.IRON_ORE) {
                ++PlayerManager.getPlayer(p.getName()).IronDestroyed;
            } else if (e.getBlock().getType() == Material.ANCIENT_DEBRIS) {
                ++PlayerManager.getPlayer(p.getName()).NetheriteDestroyed;
            }
        }

    }

    @EventHandler
    public void OnServerJoin(AsyncPlayerPreLoginEvent e) throws SQLException {
        if (e.getLoginResult() == Result.ALLOWED) {
            String p = e.getName();
            PlayerManager.addPlayer(p);
            boolean checking_result = plugin.CheckPlayer(p);
            if (!checking_result) {
                try {
                    plugin.AddToDb(p);
                } catch (Exception var5) {
                    Bukkit.getServer().broadcastMessage(var5.getMessage());
                }
            } else {
                getfromdatabase(p);
            }
        }

    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        PlayerManager.getPlayer(e.getPlayer().getName()).timestampJoin = System.currentTimeMillis();
    }

    @EventHandler
    public void OnServerLeave(PlayerQuitEvent e) throws SQLException {
        Player p = e.getPlayer();
        PlayerManager.getPlayer(e.getPlayer().getName()).timestampleave = System.currentTimeMillis();
        plugin.UpdateDatabase(p);
        this.playermanager.removePlayer(e.getPlayer().getName());
    }
}