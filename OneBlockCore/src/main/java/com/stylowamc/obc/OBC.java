package com.stylowamc.obc;

import com.stylowamc.obc.commands.Command_end;
import com.stylowamc.obc.commands.Command_obc;
import com.stylowamc.obc.drop.DisableCraftings;
import com.stylowamc.obc.drop.MobDrops;
import com.stylowamc.obc.drop.VillagerBlock;
import com.stylowamc.obc.events.useEndPassHandler;
import com.stylowamc.obc.items.EndPass;
import com.stylowamc.obc.items.MendingBook;
import com.stylowamc.obc.items.Beacon;
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
        this.getCommand("przepustka").setExecutor(new Command_end());
        getServer().getPluginManager().registerEvents(new MobDrops(),this);
        getServer().getPluginManager().registerEvents(new VillagerBlock(), this);
        getServer().getPluginManager().registerEvents(new useEndPassHandler(), this);
        getServer().getPluginManager().registerEvents(new DisableCraftings(),this);
        SkeletonSkull.register();
        Beacon.register();
        MendingBook.register();
        EndPass.register();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SkeletonSkull.unregister();
        Beacon.unregister();
        MendingBook.unregister();
        EndPass.unregister();
    }
}
