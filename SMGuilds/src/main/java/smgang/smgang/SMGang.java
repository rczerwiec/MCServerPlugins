package smgang.smgang;

import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;
import smgang.smgang.commands.CreateGuild;

public final class SMGang extends JavaPlugin {

    private static Gson gson = new Gson();

    @Override
    public void onEnable() {
        this.getCommand("gang").setExecutor(new CreateGuild());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
