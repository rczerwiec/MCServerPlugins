package com.stylowamc.smskins.commands;

import com.stylowamc.smskins.guis.GUI_Skins;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Skins implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        GUI_Skins skins = new GUI_Skins();
        skins.openInventory(p);
        //open gui
        return false;
    }
}
