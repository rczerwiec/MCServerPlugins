package smpvp.smpvp.commands;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import smpvp.smpvp.SMPvp;
import smpvp.smpvp.arenas.Arena;
import smpvp.smpvp.arenas.ArenaManager;

public class setupArena implements CommandExecutor {
    SMPvp plugin = SMPvp.getInstance();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.isOp()) {
                try {
                    if (args[0].equals("setspawn")) {
                        if (args.length > 2) {
                            String arena = buildStringFromArgs(args, 4);
                            Arena selectedArena = (Arena)ArenaManager.arenas.get(arena);
                            selectedArena.spawnLocations.add(new Location(Bukkit.getWorld("world"), (double)Integer.parseInt(args[1]), (double)Integer.parseInt(args[2]), (double)Integer.parseInt(args[3])));
                            List<String> locationList = this.plugin.getConfig().getStringList(selectedArena.ID + ".location");
                            locationList.add(args[1] + "." + args[2] + "." + args[3]);
                            this.plugin.getConfig().set(selectedArena.ID + ".location", locationList);
                            this.plugin.saveConfig();
                        } else {
                            p.sendMessage("wprowadź wszystkie argumenty /smpvp <x> <y> <z> <nazwa areny z pliku config.yml>");
                        }
                    } else {
                        p.sendMessage("wprowadź /smpvp setspawn <idareny>");
                    }
                } catch (NullPointerException var9) {
                    p.sendMessage("Wprowadź argumenty!");
                    return false;
                }
            }
        }

        return false;
    }

    public static String buildStringFromArgs(String[] args, int start) {
        String w = "";

        for(int i = start; i < args.length; ++i) {
            w = w + args[i] + " ";
        }

        w = w.substring(0, w.length() - 1);
        return w;
    }
}