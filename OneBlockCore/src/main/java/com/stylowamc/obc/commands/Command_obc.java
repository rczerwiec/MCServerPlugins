package com.stylowamc.obc.commands;

import com.stylowamc.obc.items.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class Command_obc implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("obc.fullperms")){
                if(args.length == 0){
                    p.sendMessage("Dopisz materiał");
                }
                else if(args.length == 1){
                    if (args[0].equalsIgnoreCase("0")){
                        p.getInventory().addItem(DarkPiece.create());
                    }
                    else if (args[0].equalsIgnoreCase("1")){
                        p.getInventory().addItem(StarBook.create());
                    }
                    else if (args[0].equalsIgnoreCase("2")){
                        p.getInventory().addItem(LightPiece.create());
                    }
                    else if (args[0].equalsIgnoreCase("3")){
                        p.getInventory().addItem(EndFlower.create());
                    }
                    else if (args[0].equalsIgnoreCase("2")){
                        p.getInventory().addItem(EndPass.create());
                    }
                }
            }
        }



        return false;
    }
}
