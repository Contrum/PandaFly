package dev.panda.combofly.managers;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import org.bukkit.entity.Player;

public class SpeedManager {

    private int walkSpeed;
    private int flySpeed;

    public SpeedManager() {
        this.walkSpeed = 0;
        this.flySpeed = 0;
    }

    public void getWalkSpeed(String amount, Player player) {
        try {
            this.walkSpeed = Integer.parseInt(amount);
        }
        catch (NumberFormatException e) {
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("SPEED.WALK.WRONG-FORMAT")));
            return;
        }
        if (this.walkSpeed < 1 || this.walkSpeed > 10) {
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("SPEED.WALK.WRONG-FORMAT")));
            return;
        }
        player.setWalkSpeed((float) this.walkSpeed / 10);
        player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("SPEED.WALK.SPEED")
                .replace("{speed}", String.valueOf(this.walkSpeed))));
    }

    public void getFlySpeed(String amount, Player player) {
        try {
            this.flySpeed = Integer.parseInt(amount);
        }
        catch (NumberFormatException e) {
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("SPEED.FLY.WRONG-FORMAT")));
            return;
        }
        if (this.flySpeed < 1 || this.flySpeed > 10) {
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("SPEED.FLY.WRONG-FORMAT")));
            return;
        }
        player.setFlySpeed((float) this.flySpeed / 10);
        player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("SPEED.FLY.SPEED")
                .replace("{speed}", String.valueOf(this.flySpeed))));
    }
}