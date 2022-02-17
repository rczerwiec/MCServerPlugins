package pl.stylowamc;

import org.bukkit.plugin.java.JavaPlugin;

public final class SMMobs extends JavaPlugin {

    private static SMMobs plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new SpawnEvents(), this);
    }


    public static SMMobs getInstance(){
        return plugin;
    }
}
