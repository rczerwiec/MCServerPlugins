package chaossmp.chaossmp;

import org.bukkit.plugin.java.JavaPlugin;

public final class ChaosSMP extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new HeartsHandler(),this);
        this.getCommand("ocal").setExecutor(new UnbanCommand());
        this.getCommand("setHealth").setExecutor(new setHealthCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
