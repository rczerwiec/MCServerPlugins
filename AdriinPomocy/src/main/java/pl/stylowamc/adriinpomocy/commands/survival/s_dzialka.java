package pl.stylowamc.adriinpomocy.commands.survival;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.stylowamc.adriinpomocy.AdriinPomocy;

public class s_dzialka implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        AdriinPomocy plugin = AdriinPomocy.getInstance();
        if (p.hasPermission("ap.all")) {
            p.chat(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("s_dzialka")));
            return true;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("noperms")));
            return false;
        }
    }
}
