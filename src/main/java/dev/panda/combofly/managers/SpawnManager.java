package dev.panda.combofly.managers;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class SpawnManager {

    private HashMap<UUID, Long> cooldown;
    private HashMap<UUID, SpawnTask> spawnTasks;
    private int time;

    public SpawnManager() {
        this.cooldown = new HashMap<>();
        this.spawnTasks = new HashMap<>();
        this.time = ComboFly.get().getMessageConfig().getInt("SPAWN.COOLDOWN");
    }

    public void createSpawn(Player player) {
        ComboFly.get().getSpawnConfig().getConfiguration().set("SPAWN.WORLD", player.getWorld().getName());
        ComboFly.get().getSpawnConfig().getConfiguration().set("SPAWN.X", player.getLocation().getX());
        ComboFly.get().getSpawnConfig().getConfiguration().set("SPAWN.Y", player.getLocation().getY());
        ComboFly.get().getSpawnConfig().getConfiguration().set("SPAWN.Z", player.getLocation().getZ());
        ComboFly.get().getSpawnConfig().getConfiguration().set("SPAWN.YAW", player.getLocation().getYaw());
        ComboFly.get().getSpawnConfig().getConfiguration().set("SPAWN.PITCH", player.getLocation().getPitch());
        ComboFly.get().getSpawnConfig().save();
        ComboFly.get().getSpawnConfig().reload();
    }

    public Location getSpawnLocation() {
        return new Location(
                getSpawnWorld(),
                getSpawnX(),
                getSpawnY(),
                getSpawnZ(),
                getSpawnYaw(),
                getSpawnPitch());
    }

    private World getSpawnWorld() {
        return Bukkit.getWorld(ComboFly.get().getSpawnConfig().getString("SPAWN.WORLD"));
    }

    private double getSpawnX() {
        return ComboFly.get().getSpawnConfig().getDouble("SPAWN.X");
    }

    private double getSpawnY() {
        return ComboFly.get().getSpawnConfig().getDouble("SPAWN.Y");
    }

    private double getSpawnZ() {
        return ComboFly.get().getSpawnConfig().getDouble("SPAWN.Z");
    }

    private int getSpawnYaw() {
        return ComboFly.get().getSpawnConfig().getInt("SPAWN.YAW");
    }

    private int getSpawnPitch() {
        return ComboFly.get().getSpawnConfig().getInt("SPAWN.PITCH");
    }

    public boolean hasCooldown(Player player) {
        return this.cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()) > System.currentTimeMillis();
    }

    public long getCooldown(Player player) {
        return (this.cooldown.get(player.getUniqueId()) / 1000) - (System.currentTimeMillis() / 1000);
    }

    public void setCooldown(Player player) {
        this.cooldown.put(player.getUniqueId(), System.currentTimeMillis() + (this.getTime() * 1000L));
    }

    public void removeCooldown(Player player) {
        this.cooldown.remove(player.getUniqueId());
    }

    public void setRunnable(final Player player) {
        final SpawnTask SpawnTask = new SpawnTask(player);
        SpawnTask.runTaskLater(ComboFly.get(), (this.getTime() * 20L));
        this.spawnTasks.put(player.getUniqueId(), SpawnTask);
    }

    public void removeRunnable(final Player player) {
        if (this.spawnTasks.containsKey(player.getUniqueId())) {
            this.spawnTasks.get(player.getUniqueId()).cancel();
            this.spawnTasks.remove(player.getUniqueId());
        }
    }

    public static class SpawnTask extends BukkitRunnable {
        private final Player player;

        public SpawnTask(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            if (ComboFly.get().getSpawnManager().getCooldown(player) <= 0L) {
                this.cancel();
                ComboFly.get().getSpawnManager().removeCooldown(player);
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10F, 0.2F);
                player.teleport(ComboFly.get().getSpawnManager().getSpawnLocation());
                player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("SPAWN.TELEPORTED")));
            }
        }
    }
}
