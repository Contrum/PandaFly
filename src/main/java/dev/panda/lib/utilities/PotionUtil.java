package dev.panda.lib.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Risas
 * Project: PandaLib
 * Date: 25-10-2021 - 14:33
 * Twitter: @RisasDev
 * Github: https://github.com/RisasDev
 */

@UtilityClass
public class PotionUtil {

    public void addPotion(Player player, PotionEffectType potionEffectType) {
        addPotion(player, potionEffectType, 1);
    }

    public void addPotion(Player player, PotionEffectType potionEffectType, int duration) {
        addPotion(player, potionEffectType, duration, 1);
    }

    public void addPotion(Player player, PotionEffectType potionEffectType, int duration, int level) {
        addPotion(player, potionEffectType, duration, level, false);
    }

    public void addPotion(Player player, PotionEffectType potionEffectType, int duration, int level, boolean remove) {
        addPotion(player, potionEffectType, duration, level, remove, false);
    }

    public void addPotion(Player player, PotionEffectType potionEffectType, int duration, int level, boolean remove, boolean checkLevel) {
        boolean canRemove = false;

        if (checkLevel) {
            if (getPotion(player, potionEffectType) != null) {
                PotionEffect potionEffect = getPotion(player, potionEffectType);
                int potionAmplifier = potionEffect.getAmplifier() + 1;

                if (level < potionAmplifier) return;

                canRemove = true;
            }
        }
        if (remove || canRemove) {
            player.removePotionEffect(potionEffectType);
        }
        player.addPotionEffect(new PotionEffect(potionEffectType, 20 * (duration + 1), level - 1));
    }

    public PotionEffect getPotion(Player player, PotionEffectType potionEffectType) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (potionEffect.getType().equals(potionEffectType)) {
                return potionEffect;
            }
        }
        return null;
    }
}
