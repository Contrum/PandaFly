package dev.panda.lib.effect;

import dev.panda.lib.utilities.Server;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

public enum CompatibleEffect {

    HEART("HEART", "HEART_PARTICLE", "HEART_PARTICLE");

    private final String effect8;
    private final String effect912;
    private final String effect13;

    CompatibleEffect(String effect8, String effect912, String effect13) {
        this.effect8 = effect8;
        this.effect912 = effect912;
        this.effect13 = effect13;
    }

    CompatibleEffect(String effect8, String effect913) {
        this(effect8, effect913, effect913);
    }

    CompatibleEffect(String effect13) {
        this(null, null, effect13);
    }

    public void play(Player player) {
        player.getWorld().spigot().playEffect(player.getLocation(), getEffect(), 26, 0, 0.2F, 0.5F, 0.2F, 0.2F, 12, 387);
    }

    private Effect getEffect() {
        if (Server.SERVER_VERSION_INT == 8) {
            return effect8 == null ? Effect.valueOf("HEART") : Effect.valueOf(effect8);
        } else if (Server.SERVER_VERSION_INT > 9 && Server.SERVER_VERSION_INT < 13) {
            return effect912 == null ? Effect.valueOf("HEART_PARTICLE") : Effect.valueOf(effect912);
        } else if (Server.SERVER_VERSION_INT >= 13) {
            return effect13 == null ? Effect.valueOf("HEART_PARTICLE") : Effect.valueOf(effect13);
        } else {
            return null;
        }
    }
}
