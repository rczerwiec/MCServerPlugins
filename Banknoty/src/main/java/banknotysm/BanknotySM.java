package banknotysm;

import banknotysm.commands.BanknotCommand;
import banknotysm.configs.Config;
import banknotysm.events.SellBanknotOnCrouchRightclick;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import banknotysm.util.BanknotyUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

//Vault imports
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

import java.util.logging.Logger;

public final class BanknotySM extends JavaPlugin {

    private final Logger logger = Logger.getLogger("banknotysm");

    private static Config config;

    private static BanknotySM plugin;

    //Vault statics
    private static Economy econ = null;
    private static Permission perms = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.getCommand("banknoty").setExecutor(new BanknotCommand());
        Bukkit.getPluginManager().registerEvents(new SellBanknotOnCrouchRightclick(),this);
        config = new Config();
        BanknotyUtil.registerPermissions();

        if (!setupEconomy() ) {
            logger.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        BanknotyUtil.unregisterPermissions();

        logger.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    public Logger getLogger(){
        return logger;
    }

    public static BanknotySM getInstance(){
        return plugin;
    }

    public static Config getInternalConfig(){
        return config;
    }

    //Vault

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

}
