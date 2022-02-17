package pl.smpickaxe;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pl.smpickaxe.commands.GivePickaxe;
import pl.smpickaxe.config.ArmorsConfig;
import pl.smpickaxe.events.Events;
import pl.smpickaxe.events.PickaxeCraftEvent;
import pl.smpickaxe.events.RandomSpawnerChestEvents;
import pl.smpickaxe.utils.CreatePickaxe;

import java.io.File;
import java.io.IOException;

public final class Smpickaxe extends JavaPlugin {

    private static Smpickaxe plugin;
    private File settingsConfigFile;
    private FileConfiguration settingsConfig;

    public static Smpickaxe getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        //Config
        getConfig().options().copyDefaults(true);
        saveConfig();
        createSettingsConfig();

        ArmorsConfig.setup();
        this.getCommand("smpickaxe").setExecutor(new GivePickaxe());
        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getPluginManager().registerEvents(new RandomSpawnerChestEvents(), this);
        getServer().getPluginManager().registerEvents(new PickaxeCraftEvent(), this);


    }
    public FileConfiguration getSettingsConfig() {
        return this.settingsConfig;
    }

    private void createSettingsConfig() {
        settingsConfigFile = new File(getDataFolder(), "settings.yml");
        if (!settingsConfigFile.exists()) {
            settingsConfigFile.getParentFile().mkdirs();
            saveResource("settings.yml", false);
        }

        settingsConfig= new YamlConfiguration();
        try {
            settingsConfig.load(settingsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CreatePickaxe.recipe_unregister("1x2");
        CreatePickaxe.recipe_unregister("2x2");
        CreatePickaxe.recipe_unregister("2x3");
        CreatePickaxe.recipe_unregister("3x3_");
        CreatePickaxe.recipe_unregister("2x2spawnery");
        CreatePickaxe.recipe_unregister("1");

        //Armors Rubinium
        /*RubinumHelmet.unregister();
        RubinumChestplate.unregister();
        RubinumLeggins.unregister();
        RubinumBoots.unregister();*/
    }

}
