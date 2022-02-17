package pl.stylowamc.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Integer> cooldowns = new HashMap();

    public void setCooldown(UUID player, int time) {
        if (time < 1) {
            this.cooldowns.remove(player);
        } else {
            this.cooldowns.put(player, time);
        }

    }

    public int getCooldown(UUID player) {
        return (Integer)this.cooldowns.getOrDefault(player, 0);
    }
}