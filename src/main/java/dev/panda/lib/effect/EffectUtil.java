package dev.panda.lib.effect;

import lombok.experimental.UtilityClass;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

@UtilityClass
public class EffectUtil {

    public void play(Player player, String effect) {
        if (!effect.isEmpty()) {
            player.getWorld().spigot().playEffect(player.getLocation(), Effect.valueOf(effect), 26, 0, 0.2F, 0.5F, 0.2F, 0.2F, 12, 387);
        }
    }
}
