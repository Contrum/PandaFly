package dev.panda.combofly.listeners;

import dev.panda.combofly.ComboFly;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.UUID;

public class KillStreakListener implements Listener {

    public KillStreakListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ComboFly.get());
    }

    private final HashMap<UUID, Integer> killStreak = new HashMap<>();

    @EventHandler
    public void onPlayerDeath1(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() != null && event.getEntity() != null) {
            Player killer = event.getEntity().getKiller();
            Player killed = event.getEntity();

            if (!killStreak.containsKey(killer.getUniqueId())) {
                killStreak.put(killer.getUniqueId(), 0);
            }

            int addKills = killStreak.get(killer.getUniqueId());
            killStreak.put(killer.getUniqueId(), addKills + 1);
            killStreak.remove(killed.getUniqueId());
            int kills = killStreak.get(killer.getUniqueId());

            for (String streak : ComboFly.get().getMainConfig().getConfiguration().getConfigurationSection("KILLSTREAK.REWARDS.").getKeys(false)) {
                if (streak.equals(String.valueOf(kills))) {
                    ComboFly.get().getKillstreakManager().messageKillStreak(kills, killer);
                    ComboFly.get().getKillstreakManager().messageReward(kills, killer);
                    ComboFly.get().getKillstreakManager().getRepairAll(killer);
                    ComboFly.get().getKillstreakManager().getReward(kills, killer);
                    return;
                }
            }
        }
    }
}