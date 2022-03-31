package smgang.smgang.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import smgang.smgang.SMGang;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class OnServerJoin implements Listener {

    SMGang plugin = SMGang.getInstance();

    @EventHandler
    public void RegisterNewPlayer(PlayerJoinEvent e) throws SQLException, IOException {
        if(isInDatabase(e.getPlayer().getName())){
            e.getPlayer().sendMessage("Jesteś już w bazie!");
        }
        else{
            PreparedStatement stat = SMGang.connection.prepareStatement("INSERT INTO obywatele(Name,Country,City,Age,Ranga,Stopien,UUID) VALUES (?,?,?,?,?,?,?)");
            stat.setString(1,e.getPlayer().getName());
            stat.setString(2,getCountry());
            stat.setString(3,"Testowe");
            stat.setInt(4,0);
            stat.setString(5,"GM");
            stat.setString(6,"Obywatel");
            stat.setString(7, String.valueOf(e.getPlayer().getUniqueId()));
            stat.executeUpdate();

        }
    }

    public String getCountry() throws IOException {
        int x = ThreadLocalRandom.current().nextInt(0, 3 + 1);
        String name = plugin.getCountryConfig().getString(x+".name");
        int population = plugin.getCountryConfig().getInt(x+".liczebnosc");
        plugin.getCountryConfig().set(x+".liczebnosc",population+1);
        plugin.getCountryConfig().save(plugin.getCountryConfigFile());

        return name;
    }

    boolean isInDatabase(String p) {
        int query_result = 0;

        try {
            PreparedStatement stat = SMGang.connection.prepareStatement("SELECT COUNT(Name) AS Result FROM obywatele WHERE Name = ?");
            stat.setString(1, p);

            for(ResultSet result = stat.executeQuery(); result.next(); query_result = result.getInt("Result")) {
            }

            return query_result >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

}
