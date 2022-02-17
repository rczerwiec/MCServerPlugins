package smitems;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import smitems.events.BlockWrittenBookCraftingEvent;
import smitems.events.SlimeChunkCheckerEvent;
import smitems.perms.Perms;
import smitems.util.*;

import java.util.logging.Logger;

public final class SMItems extends JavaPlugin {

    private static SMItems plugin;

    //private static Config config;
    private final Logger logger = Logger.getLogger("SMItems");


    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Perms.registerPermissions();
        SlimeChunkCheckerUtil.registerRecipe();
        BeaconCraftingUtil.registerBeaconRecipe();
        Bukkit.getPluginManager().registerEvents(new SlimeChunkCheckerEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BlockWrittenBookCraftingEvent(), this);

        //VillagerEggUtil.registerVillagerEggRecipe();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Perms.unregisterPermissions();
        SlimeChunkCheckerUtil.unregisterRecipe();
        BeaconCraftingUtil.unregisterBeaconRecipe();
        //VillagerEggUtil.unregisterVillagerEggRecipe();
    }

    public Logger getLogger() {
        return logger;
    }
    public static SMItems getInstance() {
        return plugin;
    }

}
