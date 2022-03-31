package smgang.smgang;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import smgang.smgang.events.OnServerJoin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class SMGang extends JavaPlugin {

    String host;
    String port;
    String database;
    String username;
    String password;
    public static Connection connection;

    private static SMGang plugin;
    private static FileConfiguration config;

    private File countryConfigFile;
    private FileConfiguration countryConfig;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        config = this.getConfig();
        createCountryConfig();

        this.getServer().getPluginManager().registerEvents(new OnServerJoin(),this);

        this.host = config.getString("ip");
        this.port = config.getString("port");
        this.database = config.getString("database");
        this.username = config.getString("user");
        this.password = config.getString("password");

        try {
            this.openConnection();
            Statement e = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.runnable();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
                    boolean connect = SMGang.connection.isValid(0);
                    if (!connect) {
                        try {
                            SMGang.this.openConnection();
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SQLException e) {
                    Bukkit.getServer().broadcastMessage(e.toString());
                }

            }
        }).runTaskTimerAsynchronously(this, 0L, 3000L);
    }

    public FileConfiguration getCountryConfig() {
        return this.countryConfig;
    }

    public File getCountryConfigFile() {
        return this.countryConfigFile;
    }

    public static SMGang getInstance() {
        return plugin;
    }

    private void createCountryConfig() {
        countryConfigFile = new File(getDataFolder(), "country.yml");
        if (!countryConfigFile.exists()) {
            countryConfigFile.getParentFile().mkdirs();
            saveResource("country.yml", false);
        }

        countryConfig = new YamlConfiguration();
        try {
            countryConfig.load(countryConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
