package dev.panda.lib.sound;

import lombok.experimental.UtilityClass;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@UtilityClass
public class SoundUtil {

    public void play(Player player, String sound) {
        if (!sound.isEmpty()) {
            player.playSound(player.getLocation(), Sound.valueOf(sound), 1F, 1F);
        }
    }
}
