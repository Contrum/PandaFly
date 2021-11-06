package dev.panda.combofly.managers;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FreezeManager {

    public List<String> freeze = new ArrayList<>();

    public boolean isFrozen(Player player) {
        return freeze.contains(player.getName());
    }

    public void setFreeze(Player player) {
        freeze.add(player.getName());
        this.frozenMessage(player);
    }

    public void removeFreeze(Player player) {
        freeze.remove(player.getName());
    }

    private void frozenMessage(Player player) {
        for (String lines : ComboFly.get().getMessageConfig().getStringList("FREEZE.FROZEN")) {
            player.sendMessage(ChatUtil.translate(lines));
        }
    }
}
