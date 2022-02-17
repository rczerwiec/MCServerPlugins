package pl.stylowamc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.stylowamc.Data.PlayerData;
import pl.stylowamc.Data.PlayerManager;
import pl.stylowamc.commands.Stats;

public final class Stylowamc extends JavaPlugin {
    static Connection connection;
    private String host;
    private String database;
    private String username;
    private String password;
    private int port;
    PlayerManager playermanager = new PlayerManager();
    Stylowamc plugin;

    public void onEnable() {
        this.getCommand("statystyki").setExecutor(new Stats(this));
        this.plugin = this;
        this.host = "ip";
        this.port = 3306;
        this.database = "";
        this.username = "";
        this.password = "!";

        try {
            this.openConnection();
        } catch (SQLException | ClassNotFoundException var3) {
            var3.printStackTrace();
        }

        this.getServer().getPluginManager().registerEvents(new Events(this), this);
        Iterator var1 = Bukkit.getOnlinePlayers().iterator();

        while(var1.hasNext()) {
            Player p = (Player)var1.next();
            PlayerManager.addPlayer(p.getName());
            Events.getfromdatabase(p.getName());
        }

    }

    public void onDisable() {
        try {
            Iterator var1 = Bukkit.getOnlinePlayers().iterator();

            while(var1.hasNext()) {
                Player p = (Player)var1.next();
                this.UpdateDatabase(p);
                this.playermanager.removePlayer(p.getName());
            }

            connection.close();
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            synchronized(this) {
                if (connection == null || connection.isClosed()) {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
                }
            }
        }
    }

    public void AddToDb(String player) throws SQLException {
        PreparedStatement stat = connection.prepareStatement("INSERT INTO Statystyki(Name) VALUES (?)");
        stat.setString(1, player);
        stat.executeUpdate();
    }

    public boolean CheckPlayer(String player) throws SQLException {
        int query_result = 0;
        PreparedStatement stat = connection.prepareStatement("SELECT COUNT(Name) AS Result FROM Statystyki WHERE Name = ?");
        stat.setString(1, player);

        for(ResultSet result = stat.executeQuery(); result.next(); query_result = result.getInt("Result")) {
        }

        return query_result >= 1;
    }

    public void UpdateDatabase(Player p) throws SQLException {
        PlayerData stats = PlayerManager.getPlayer(p.getName());
        PreparedStatement stat = connection.prepareStatement("UPDATE Statystyki SET Wykopane_Bloki=?, Wykopane_Diamenty=?, Wykopane_Zloto=?, Wykopane_Zelazo=?, Postawione_Bloki=?, Smierci=?, Zabite_Potwory=?, Zabite_Smoki=?, Zabite_Withery=?, Zabojstwa=?, Wykopany_Netherite=?, Przegrane_Minuty=? WHERE Name = ?");
        stat.setInt(1, stats.BlocksDestroyed);
        stat.setInt(2, stats.DiamondDestroyed);
        stat.setInt(3, stats.GoldDestroyed);
        stat.setInt(4, stats.IronDestroyed);
        stat.setInt(5, stats.BlockPlaced);
        stat.setInt(6, stats.DeathCount);
        stat.setInt(7, stats.MobsKilled);
        stat.setInt(8, stats.KilledDragons);
        stat.setInt(9, stats.KilledWithers);
        stat.setInt(10, stats.Kills);
        stat.setInt(11, stats.NetheriteDestroyed);
        stat.setInt(12, PlayerManager.getTimeplayed(p.getName()));
        stat.setString(13, p.getName());
        stat.executeUpdate();
        Bukkit.getLogger().info("[§6SM§f]Zaktualizowano staystyki dla: " + p.getName());
    }

    public static HashMap getPlayerRanking(Player p) throws SQLException {
        HashMap<String, Integer> ranking = new HashMap();
        Statement st = connection.createStatement();
        String sql_ranking = "SELECT Name, FIND_IN_SET( Wykopane_Bloki,( SELECT GROUP_CONCAT( Wykopane_Bloki ORDER BY Wykopane_Bloki DESC ) FROM Statystyki)) AS rankWykopane_Bloki, FIND_IN_SET(Postawione_Bloki,( SELECT GROUP_CONCAT( Postawione_Bloki ORDER BY Postawione_Bloki DESC ) FROM Statystyki) ) AS rankPostawione_Bloki ,FIND_IN_SET(`Wykopane_Diamenty`,( SELECT GROUP_CONCAT( `Wykopane_Diamenty` ORDER BY `Wykopane_Diamenty` DESC ) FROM Statystyki) ) AS rankWykopane_Diamenty ,FIND_IN_SET(`Wykopane_Zloto`,( SELECT GROUP_CONCAT( `Wykopane_Zloto` ORDER BY `Wykopane_Zloto` DESC ) FROM Statystyki) ) AS rankWykopane_Zloto ,FIND_IN_SET(`Wykopane_Zelazo`,( SELECT GROUP_CONCAT( `Wykopane_Zelazo` ORDER BY `Wykopane_Zelazo` DESC ) FROM Statystyki) ) AS rankWykopane_Zelazo ,FIND_IN_SET(`Smierci`,( SELECT GROUP_CONCAT( `Smierci` ORDER BY `Smierci` DESC ) FROM Statystyki) ) AS rankSmierci ,FIND_IN_SET(`Zabite_Potwory`,( SELECT GROUP_CONCAT( `Zabite_Potwory` ORDER BY `Zabite_Potwory` DESC ) FROM Statystyki) ) AS rankZabite_Potwory ,FIND_IN_SET(`Zabite_Smoki`,( SELECT GROUP_CONCAT( `Zabite_Smoki` ORDER BY `Zabite_Smoki` DESC ) FROM Statystyki) ) AS rankZabite_Smoki ,FIND_IN_SET(`Zabite_Withery`,( SELECT GROUP_CONCAT( `Zabite_Withery` ORDER BY `Zabite_Withery` DESC ) FROM Statystyki) ) AS rankZabite_Withery ,FIND_IN_SET(`Wykopany_Netherite`,( SELECT GROUP_CONCAT( `Wykopany_Netherite` ORDER BY `Wykopany_Netherite` DESC ) FROM Statystyki) ) AS rankWykopany_Netherite ,FIND_IN_SET(`Zabojstwa`,( SELECT GROUP_CONCAT( `Zabojstwa` ORDER BY `Zabojstwa` DESC ) FROM Statystyki) ) AS rankZabojstwa, FIND_IN_SET(`Przegrane_Minuty`,( SELECT GROUP_CONCAT( `Przegrane_Minuty` ORDER BY `Przegrane_Minuty` DESC ) FROM Statystyki) ) AS rankPrzegrane_Minuty FROM Statystyki Where Name = '" + p.getName() + "'";
        ResultSet rs = st.executeQuery(sql_ranking);
        if (rs.next()) {
            ranking.put("Wykopane_Bloki", rs.getInt("rankWykopane_Bloki"));
            ranking.put("Postawione_Bloki", rs.getInt("rankPostawione_Bloki"));
            ranking.put("Wykopane_Diamenty", rs.getInt("rankWykopane_Diamenty"));
            ranking.put("Wykopane_Zloto", rs.getInt("rankWykopane_Zloto"));
            ranking.put("Wykopane_Zelazo", rs.getInt("rankWykopane_Zelazo"));
            ranking.put("Zabite_Potwory", rs.getInt("rankZabite_Potwory"));
            ranking.put("Smierci", rs.getInt("rankSmierci"));
            ranking.put("Zabite_Smoki", rs.getInt("rankZabite_Smoki"));
            ranking.put("Zabite_Withery", rs.getInt("rankZabite_Withery"));
            ranking.put("Wykopany_Netherite", rs.getInt("rankWykopany_Netherite"));
            ranking.put("Zabojstwa", rs.getInt("rankZabojstwa"));
            ranking.put("Przegrane_Minuty", rs.getInt("rankPrzegrane_Minuty"));
        }

        return ranking;
    }
}