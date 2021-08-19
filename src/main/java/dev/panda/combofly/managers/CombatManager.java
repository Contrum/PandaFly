package dev.panda.combofly.managers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class CombatManager {

    private HashMap<UUID, Long> cooldown;
    private int time;

    public CombatManager() {
        this.cooldown = new HashMap<>();
        this.time = 30;
    }

    public boolean hasCooldown(Player player) {
        return this.cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()) > System.currentTimeMillis();
    }

    public String getCooldown(Player player) {
        return String.valueOf((this.cooldown.get(player.getUniqueId()) / 1000) - (System.currentTimeMillis() / 1000));
    }

    public void setCooldown(Player player) {
        this.cooldown.put(player.getUniqueId(), System.currentTimeMillis() + (this.getTime() * 1000));
    }

    public void removeCooldown(Player player) {
        this.cooldown.remove(player.getUniqueId());
    }
}
