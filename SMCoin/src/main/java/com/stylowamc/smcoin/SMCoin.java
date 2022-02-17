package com.stylowamc.smcoin;

import com.stylowamc.smcoin.Commands.AddCoinsCMD;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class SMCoin extends JavaPlugin {

    String host;
    String port;
    String database;
    String username;
    String password;
    public static Connection connection;
    private static SMCoin plugin;
    private static FileConfiguration config;

    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        config = this.getConfig();
        this.getCommand("smc").setExecutor(new AddCoinsCMD());
        this.host = config.getString("DB_ip");
        this.port = config.getString("DB_port");
        this.database = config.getString("DB_db");
        this.username = config.getString("DB_login");
        this.password = config.getString("DB_password");

        try {
            this.openConnection();
            Statement var1 = connection.createStatement();
        } catch (SQLException | ClassNotFoundException var2) {
            var2.printStackTrace();
        }

        this.runnable();
    }

    public void onDisable() {
    }

    public static FileConfiguration getConfigIstance() {
        return config;
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

    public void runnable() {
        (new BukkitRunnable() {
            public void run() {
                try {
                    boolean connect = SMCoin.connection.isValid(0);
                    if (!connect) {
                        try {
                            SMCoin.this.openConnection();
                        } catch (ClassNotFoundException var3) {
                            var3.printStackTrace();
                        } catch (SQLException var4) {
                            var4.printStackTrace();
                        }
                    }
                } catch (SQLException var5) {
                    Bukkit.getServer().broadcastMessage(var5.toString());
                }

            }
        }).runTaskTimerAsynchronously(this, 0L, 3000L);
    }

    public static SMCoin getInstance() {
        return plugin;
    }
}
