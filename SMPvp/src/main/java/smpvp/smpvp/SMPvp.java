package smpvp.smpvp;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import smpvp.smpvp.commands.*;
import smpvp.smpvp.data.AdminKitList;
import smpvp.smpvp.data.CustomArenas;
import smpvp.smpvp.data.DataManager;
import smpvp.smpvp.data.PlayerKitList;
import smpvp.smpvp.events.*;
import smpvp.smpvp.events.adminkits.AdminKitsEvents;
import smpvp.smpvp.events.adminkits.NewAdminKitsEvents;
import smpvp.smpvp.events.playerkits.AcceptationEvents;
import smpvp.smpvp.events.playerkits.NewKitsEvents;
import smpvp.smpvp.events.playerkits.PlayerKitsEvents;

public final class SMPvp extends JavaPlugin implements Listener {
    private static SMPvp plugin;
    public DataManager data;

    public PlayerKitList kitlist;
    public AdminKitList adminKitList;
    public CustomArenas freearenas;

    /*
    TODO:
        kreator kitów w komendy /adminkit dla osób mających op
        Kits.java wyszukuje wszystkie dodane kity i je tworzy.




     */

    public void onEnable() {
        plugin = this;
        this.data = new DataManager(this);
        this.kitlist = new PlayerKitList(this);
        this.adminKitList = new AdminKitList(this);
        this.freearenas = new CustomArenas(this);
        this.getCommand("smpvp").setExecutor(new setupArena());
        this.getCommand("kit").setExecutor(new openPlayerKitGui());
        this.getCommand("mojekity").setExecutor(new openPlayerKitsList());
        this.getCommand("adminkit").setExecutor(new openAdminKitGui());
        this.getCommand("pojedynek").setExecutor(new acceptationGUI());
        this.getCommand("pvp").setExecutor(new PvpGUI());
        this.getCommand("adminkitlist").setExecutor(new openAdminKitsList());
        this.getCommand("pojedynek").setTabCompleter(new acceptationTabCompletion());
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        Bukkit.getPluginManager().registerEvents(new OnFightEvents(), this);
        Bukkit.getPluginManager().registerEvents(new AcceptationEvents(), this);
        Bukkit.getPluginManager().registerEvents(new NewKitsEvents(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerKitsEvents(), this);
        Bukkit.getPluginManager().registerEvents(new NewAdminKitsEvents(), this);
        Bukkit.getPluginManager().registerEvents(new AdminKitsEvents(), this);
        Bukkit.getPluginManager().registerEvents(new MainGUIEvents(), this);
    }

    public void onDisable() {
    }

    public static SMPvp getInstance() {
        return plugin;
    }
}
