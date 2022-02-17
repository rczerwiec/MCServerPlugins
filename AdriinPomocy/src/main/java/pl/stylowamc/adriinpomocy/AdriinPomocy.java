package pl.stylowamc.adriinpomocy;

import org.bukkit.plugin.java.JavaPlugin;
import pl.stylowamc.adriinpomocy.commands.all.p_event;
import pl.stylowamc.adriinpomocy.commands.all.p_itemshop;
import pl.stylowamc.adriinpomocy.commands.all.p_sprawdzanie;
import pl.stylowamc.adriinpomocy.commands.all.p_stream;
import pl.stylowamc.adriinpomocy.commands.oneblock.*;
import pl.stylowamc.adriinpomocy.commands.survival.*;
import sun.security.krb5.Config;

public final class AdriinPomocy extends JavaPlugin {
    private static Config config;
    private static AdriinPomocy plugin;

    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();

        this.getCommand("o_wyspa").setExecutor(new o_wyspa());
        this.getCommand("o_lvl").setExecutor(new o_lvl());
        this.getCommand("o_powieksz").setExecutor(new o_powieksz());
        this.getCommand("o_chromite").setExecutor(new o_chromite());
        this.getCommand("o_zarabianie").setExecutor(new o_zarabianie());
        this.getCommand("o_zaufany").setExecutor(new o_zaufany());
        this.getCommand("o_nowy").setExecutor(new o_nowy());
        this.getCommand("o_faza").setExecutor(new o_faza());
        this.getCommand("o_pvp").setExecutor(new o_pvp());
        this.getCommand("o_warp").setExecutor(new o_warp());

        this.getCommand("s_home").setExecutor(new s_home());
        this.getCommand("s_dzialka").setExecutor(new s_dzialka());
        this.getCommand("s_sklep").setExecutor(new s_sklep());
        this.getCommand("s_bloki").setExecutor(new s_bloki());
        this.getCommand("s_pvp").setExecutor(new s_pvp());
        this.getCommand("s_end").setExecutor(new s_end());
        this.getCommand("s_tpr").setExecutor(new s_end());
        this.getCommand("s_zarabianie").setExecutor(new s_end());

        this.getCommand("p_event").setExecutor(new p_event());
        this.getCommand("p_sprawdzanie").setExecutor(new p_sprawdzanie());
        this.getCommand("p_itemshop").setExecutor(new p_itemshop());
        this.getCommand("p_stream").setExecutor(new p_stream());
    }

    public void onDisable() {
    }

    public static AdriinPomocy getInstance() {
        return plugin;
    }

    }

