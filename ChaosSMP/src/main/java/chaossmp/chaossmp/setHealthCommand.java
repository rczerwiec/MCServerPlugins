package chaossmp.chaossmp;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class setHealthCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.isOp()) {
                Player h = Bukkit.getPlayer(args[0]);
                Objects.requireNonNull(h.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(Double.parseDouble(args[1]));
                }
            }

            return true;
        }
    }
