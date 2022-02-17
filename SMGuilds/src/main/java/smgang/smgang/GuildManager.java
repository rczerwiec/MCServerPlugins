package smgang.smgang;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public final class GuildManager {
    //Służy do zarządzania gildią
    //

    public GuildManager(){};

    public static HashMap<String,Guild> guilds = new HashMap<>();
    public static HashMap<Player,Guild> playersInGuilds = new HashMap<>();

    public static void CreateGuild(String guildName,Player p){
        Guild guild = new Guild(guildName);
        guilds.put(guildName,guild);
        playersInGuilds.put(p,guild);
        Bukkit.broadcastMessage("Dodano nowy gang:"+guildName);
    }

    public static void AddGuildMember(Player p, String guildName){
        Guild guild = guilds.get(guildName);
        if(!isPlayerInGuild(p)){
            playersInGuilds.put(p,guild);
            guild.AddGuildMember(p);
        }
        else{
            Bukkit.broadcastMessage("Gracz jest już w gangu");
        }

    }
    public static void RemoveGuildMember(Player p, String guildName){
        Guild guild = guilds.get(guildName);
        if(!playersInGuilds.get(p).InGuild(p)){
            playersInGuilds.remove(p,guild);
            guild.RemoveGuildMember(p);
        }
        else{
            Bukkit.broadcastMessage("Gracz nie nalezy do tej gildii");
        }
    }
    public static void GuildsList(){
        for(HashMap.Entry<String,Guild> entry : guilds.entrySet()){
            String Name = entry.getKey();
            Guild guild = entry.getValue();

            Bukkit.getServer().broadcastMessage(Name);
        }
    }

    public static void PlayersList(String guildName){
        Guild guild = guilds.get(guildName);
        guild.PlayerList();
    }

    public static boolean isPlayerInGuild(Player p){
        if(playersInGuilds.get(p)!=null){
            Guild guild = playersInGuilds.get(p);
            if(guild.InGuild(p)){
                return true;
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }

    }
    public static boolean doGuildExists(String guildName){
        if(guilds.get(guildName)!=null){
            return true;
        }
        else{
            return false;
        }

    }

    public static Guild getGuild(Player p){
        Guild g = playersInGuilds.get(p);
        return g;
    }
    public static String getGuildName(Player p){
        Guild g = playersInGuilds.get(p);
        return g.GetName();
    }

}
