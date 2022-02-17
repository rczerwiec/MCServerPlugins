package com.stylowamc.obc;

import com.stylowamc.obc.commands.Command_obc;
import com.stylowamc.obc.drop.MobDrops;
import com.stylowamc.obc.drop.VillagerBlock;
import com.stylowamc.obc.items.MendingBook;
import com.stylowamc.obc.items.NetherStar;
import com.stylowamc.obc.items.SkeletonSkull;
import org.bukkit.plugin.java.JavaPlugin;

public final class OBC extends JavaPlugin {

    private static OBC plugin;

    public static OBC getInstance(){
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        this.getCommand("obc").setExecutor(new Command_obc());
        getServer().getPluginManager().registerEvents(new MobDrops(),this);
        getServer().getPluginManager().registerEvents(new VillagerBlock(), this);
        SkeletonSkull.register();
        NetherStar.register();
        MendingBook.register();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SkeletonSkull.unregister();
        NetherStar.unregister();
        MendingBook.unregister();
    }
}
