package pl.smpickaxe.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.smpickaxe.ores.*;
import pl.smpickaxe.spawners.SpawnersUtil;

public class GivePickaxe implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.isOp()){
                if(args.length == 0){
                    p.sendMessage("Dopisz materiał");
                    return false;
                }
                else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("meteoryt")){
                        p.getInventory().addItem(Meteorite.createMeteorite());
                        return true;
                    }
                    else if(args[0].equalsIgnoreCase("rubin")){
                        p.getInventory().addItem(CustomDiamond.createCustomDiamond());
                        return true;
                    }
                    else if(args[0].equalsIgnoreCase("platyna")){
                        p.getInventory().addItem(Platinium.createPlatinium());
                        return true;
                    }
                    else if(args[0].equalsIgnoreCase("bedrock")){
                        p.getInventory().addItem(Bedrock.createBedrock());
                        return true;
                    }
                    else if(args[0].equalsIgnoreCase("netheritium")){
                        p.getInventory().addItem(Netherium.createNetherium());
                        return true;
                    }
                    else if(args[0].equalsIgnoreCase("spawner")){
                        p.getInventory().addItem(SpawnersUtil.CreateSpawner(EntityType.COW,"Creeperów"));
                        return true;
                    }
                    else if(args[0].equalsIgnoreCase("spawner")){
                        p.getInventory().addItem(KE.createEmber());
                        return true;
                    }
                    else{
                        p.sendMessage("Nie ma takiego materiału");
                        return false;
                    }
                }
                else return false;
            }
            else return false;
        }
        else return true;

    }
}
