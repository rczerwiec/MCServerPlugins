package com.stylowamc.smpresident;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

public class OpenGuiCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1){
            MainGUI.openInventory((HumanEntity) sender);
        }
        else{
            sender.sendMessage("Użyj /wybory");
        }

        return false;
    }
}
