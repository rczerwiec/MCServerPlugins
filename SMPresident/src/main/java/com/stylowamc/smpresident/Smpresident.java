package com.stylowamc.smpresident;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Smpresident extends JavaPlugin {

    private static FileConfiguration config;
    private static Smpresident plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        config = this.getConfig();

        this.getCommand("wybory").setExecutor(new OpenGuiCommand());
        getServer().getPluginManager().registerEvents(new MainGUI(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Smpresident getInstance(){
        return plugin;
    }

    public static FileConfiguration getConfigFile(){
        return config;
    }
}
