package dev.panda.combofly.managers;

import dev.panda.combofly.ComboFly;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class EnderpearlManager {

    private HashMap<UUID, Long> cooldown;
    private int time;

    public EnderpearlManager() {
        this.cooldown = new HashMap<>();
        this.time = ComboFly.get().getMessageConfig().getInt("ENDERPEARL.COOLDOWN");
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
