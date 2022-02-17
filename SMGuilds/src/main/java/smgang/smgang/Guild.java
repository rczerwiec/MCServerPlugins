package smgang.smgang;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.rmi.server.LogStream.log;

public class Guild {
        private ArrayList<Player> members = new ArrayList<>();
        private String guildName;

        public Guild(String guildName){
                this.guildName = guildName;
        }

        public void AddGuildMember(Player p){
                members.add(p);
                Bukkit.broadcastMessage("Dodana nowy członek gangu "+guildName+":"+p.getName());
        }

        public void RemoveGuildMember(Player p){
                members.remove(p);
                Bukkit.broadcastMessage("Usunięty członek gangu "+guildName+":"+p.getName());
        }

        public boolean InGuild(Player p){
                for(Player pl:members){
                        if(pl.equals(p)) return true;
                }
                return false;
        }

        public void PlayerList(){
                for(Player p:members){
                        Bukkit.broadcastMessage("Żółtodziub:"+guildName+":"+p.getName());
                }
        }

        public String GetName(){
                return guildName;
        }
}
