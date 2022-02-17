package pl.stylowamc.Data;

import java.util.HashMap;
import java.util.Map;
import pl.stylowamc.Events;

public class PlayerManager {
    private static Map<String, PlayerData> players = new HashMap();

    public static void addPlayer(String p) {
        PlayerData data = new PlayerData();
        players.put(p, data);
    }

    public static PlayerData getPlayer(String p) {
        if (players.get(p) == null) {
            addPlayer(p);
            Events.getfromdatabase(p);
        }

        return (PlayerData)players.get(p);
    }

    public void removePlayer(String p) {
        players.remove(p);
    }

    public static int getTimeplayed(String p) {
        if (getPlayer(p).timestampJoin == 0L) {
            getPlayer(p).timestampJoin = System.currentTimeMillis();
        }

        if (getPlayer(p).timestampleave == 0L) {
            getPlayer(p).timestampleave = System.currentTimeMillis();
        }

        return (int)((getPlayer(p).timestampleave - getPlayer(p).timestampJoin) / 60000L) + getPlayer(p).minuty;
    }
}