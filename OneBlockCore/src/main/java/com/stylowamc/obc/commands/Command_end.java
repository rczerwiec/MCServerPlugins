package com.stylowamc.obc.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.stylowamc.obc.items.DarkPiece;
import com.stylowamc.obc.items.EndPass;
import com.stylowamc.obc.items.LightPiece;
import com.stylowamc.obc.items.StarBook;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_end implements CommandExecutor {

    MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
    MVWorldManager worldManager = core.getMVWorldManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("obc.end")){
                if(args.length == 0){
                    if (p.getInventory().getItemInMainHand().equals(EndPass.create())){
                        p.sendMessage("§a§lZostałeś przeteleportowany do endu!");
                        p.getInventory().removeItem(p.getInventory().getItemInMainHand());
                        Location loc = new Location(Bukkit.getWorld("new_end"),0,65,0);
                        p.teleport(loc);
                    }
                    else{
                        p.sendMessage("§c§lMusisz trzymać przepustkę w dłoni!");
                    }
                }
            }
        }



        return false;
    }
}
