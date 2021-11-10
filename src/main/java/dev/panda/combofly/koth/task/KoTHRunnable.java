package dev.panda.combofly.koth.task;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.koth.KoTH;
import dev.panda.lib.chat.ChatUtil;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class KoTHRunnable extends BukkitRunnable {

    private final KoTH koth;

    @Override
    public void run() {
        if (koth.isFinished()) {
            koth.stop(true);
            return;
        }

        if (koth.getCapPlayer() != null) {
            Player player = koth.getCapPlayer();

            if (player.isDead() || !player.isValid() || !player.isOnline()) {
                koth.setCappingPlayer(null);
            } else {
                if (koth.getDecisecondsLeft() % 600 == 0 && koth.getDecisecondsLeft() != koth.getCapTime() / 100 && koth.getDecisecondsLeft() != 0) {
                    ComboFly.get().getMessageConfig().getStringList("KOTH.CONTEST").forEach(s ->
                            Bukkit.broadcastMessage(ChatUtil.translate(s.replace("{koth}", koth.getName())
                                    .replace("{time}" , koth.getTimeLeft()))));
                }
            }
        }
        else koth.setTime(0);
    }
}
