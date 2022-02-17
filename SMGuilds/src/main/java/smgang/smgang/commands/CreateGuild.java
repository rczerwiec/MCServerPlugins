package smgang.smgang.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import smgang.smgang.GuildManager;

public class CreateGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            return true;
        }
        Player p = (Player) sender;
        //Permissions
        if(!p.hasPermission("gang.use")){
            p.sendMessage(ChatColor.RED + "Nie mozesz uzyc tej komendy!");
            return true;
        }
            if (args[0].equalsIgnoreCase("stworz")) {
                //Checking if player is in guild and that he added name
                if (!args[1].isEmpty()) {
                    GuildManager.CreateGuild(args[1],p);//Creating guild
                    GuildManager.AddGuildMember(p,args[1]);//Adding owner(creator) to guild
                    return true;
                }  else if(args[1].isEmpty()){
                    Bukkit.getServer().broadcastMessage("Wpisz nazwe gangu");
                    return false;
                }

            }
            //List of guilds
            if (args[0].equals("lista")) {
                GuildManager.GuildsList();
                return true;

            }
            //Players in guilds
            if (args[0].equals("gildia")){
                if(!args[1].equals(null) && GuildManager.doGuildExists(args[1])){//if guild name is passed
                    GuildManager.PlayersList(args[1]);
                }
                if(args[1].equals(null)){
                    Bukkit.getServer().broadcastMessage("Wpisz nazwe gildii");
                    return true;
                }
                if(!GuildManager.doGuildExists(args[1])){
                    Bukkit.getServer().broadcastMessage("Ta gildia nie istnieje");
                    return true;
                }
            }
            //Adding to guilds
            if (args[0].equals("dodaj")){
                Player pl = Bukkit.getPlayer(args[1]);
                if(pl.isOnline()){
                    if(!args[1].isEmpty()){
                        String test1 = pl.getName();
                        String test2 = GuildManager.getGuildName(p);
                        Bukkit.getServer().broadcastMessage("Debug:"+test1+test2);
                        GuildManager.AddGuildMember(pl,GuildManager.getGuildName(p));
                        return true;
                    }
                    else{
                        Bukkit.getServer().broadcastMessage("Cos sie zjebalo");
                        return true;
                    }
                }
                else{
                    Bukkit.getServer().broadcastMessage("Nie ma takiego gracza");
                    return true;
                }

            }
            p.sendMessage("Użycie:/gang stworz/lista/gildia/dodaj");
            return false;
}
}
