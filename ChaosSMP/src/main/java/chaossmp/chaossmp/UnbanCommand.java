package chaossmp.chaossmp;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class UnbanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage("§c§lOcal kogoś wpisując §4§l/ocal <nick> §c§lUtracisz 10 serduszek");
            }
            if (args.length == 1) {
                p.sendMessage("§c§lPotwierdź, wpisując §4§l/ocal <nick> teraz §c§lpamiętaj, że stracisz §4§l10 serduszek!");
            }
            if (args[1].equalsIgnoreCase("teraz")) {
                if (Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() <= 22) {
                    p.sendMessage("§4§lPotrzebujesz minimum 11 serduszek żeby to wykonać!");
                } else {
                    Bukkit.broadcastMessage("§4§l "+p.getName()+" ożywił gracza " + args[0] + "!");
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "lunban " + args[0]);
                    Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() - 10);
                    return true;
                }

            }

            return true;
        }
        return true;
    }
}
