package com.stylowamc.smskins;

import com.stylowamc.smskins.commands.CMD_Skins;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMSkins extends JavaPlugin {

    private static SMSkins plugin;

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        this.getCommand("skiny").setExecutor(new CMD_Skins());
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SMSkins getInstance() {
        return plugin;
    }
}
