package dev.panda.combofly.koth;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import dev.panda.combofly.ComboFly;
import dev.panda.combofly.koth.cuboid.Cuboid;
import dev.panda.combofly.koth.task.KoTHRunnable;
import dev.panda.chat.ChatUtil;
import dev.panda.combofly.utilities.LocationUtil;
import dev.panda.combofly.utilities.TimeUtil;
import dev.panda.file.FileConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class KoTH {

    @Getter public static Set<KoTH> koths = Sets.newHashSet();
    @Getter public static KoTH activeKoth = null;
    private static final DecimalFormat SECONDS_FORMATTER = new DecimalFormat("#0.0");

    public final String name;
    public Cuboid cuboid;
    public String creator, date;
    public long time, capTime, grace;
    public Player capPlayer;
    public boolean enabled = true;
    private int taskId;
    private List<Player> peopleInCuboid = Lists.newArrayList();

    public void start(long capTime) {
        if (enabled) {
            this.capTime = capTime;
            BukkitRunnable runnable = new KoTHRunnable(this);
            runnable.runTaskTimerAsynchronously(ComboFly.get(), 0L, 4L);
            this.taskId = runnable.getTaskId();
            activeKoth = this;
            List<Player> suckMyAss = Lists.newArrayList();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (cuboid.contains(onlinePlayer.getLocation())) {
                    suckMyAss.add(onlinePlayer);
                }
            }
            setCappingPlayer(suckMyAss.get(ThreadLocalRandom.current().nextInt(suckMyAss.size())));
        }
        else System.out.println("PLEASE ENABLE KOTH");
    }

    public void stop(boolean winner) {
        if (winner) {
            ComboFly.get().getServer().getScheduler().runTask(ComboFly.get(), () -> {
                ComboFly.get().getMainConfig().getStringList("KOTH-REWARDS").forEach(s ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{winner}", this.capPlayer.getName())));
                ComboFly.get().getMessageConfig().getStringList("KOTH.WINNING").forEach(s ->
                        Bukkit.broadcastMessage(ChatUtil.translate(s.replace("{koth}", this.name)
                                .replace("{winner}", this.capPlayer.getName()))));
            });
        }
        ComboFly.get().getServer().getScheduler().runTask(ComboFly.get(), () -> {
            this.capTime = 0;
            this.time = 0;
            this.grace = 0;
            this.capPlayer = null;
            ComboFly.get().getServer().getScheduler().cancelTask(this.taskId);
            activeKoth = null;
        });
    }

    public long getDecisecondsLeft() {
        if (capPlayer == null) {
            return capTime / 100;
        } else {
            return (time + capTime - System.currentTimeMillis()) / 100;
        }
    }

    public boolean isGrace() {
        return time + grace > System.currentTimeMillis();
    }

    public boolean isFinished() {
        return activeKoth == this && time + capTime - (System.currentTimeMillis() + 1000) <= 0 && capPlayer != null;
    }

    public void setCappingPlayer(Player player) {
        if (player == null) {
            ComboFly.get().getMessageConfig().getStringList("KOTH.KNOCKED").forEach(s ->
                Bukkit.broadcastMessage(ChatUtil.translate(s.replace("{koth}", this.name)
                        .replace("{time}", getTimeLeft()))));

            grace = 1000;
            time = System.currentTimeMillis();
        } else {
            ComboFly.get().getMessageConfig().getStringList("KOTH.CONTEST").forEach(s ->
                    Bukkit.broadcastMessage(ChatUtil.translate(s.replace("{koth}", this.name)
                            .replace("{time}" , getTimeLeft()))));
        }

        this.capPlayer = player;
        this.time = System.currentTimeMillis();
    }

    public String getTimeLeft() {
        long millis;

        if (capPlayer == null) millis = capTime;
        else millis = time + capTime - System.currentTimeMillis();

        if (millis >= 3600000) return TimeUtil.millisToTimer(millis);
        else if (millis >= 60000) return TimeUtil.millisToTimer(millis);
        else return SECONDS_FORMATTER.format(((millis) / 1000.0f)) + "s";
    }

    public String getCapper() {
        if (capPlayer == null || capPlayer.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            return "None";
        }
        else {
            return capPlayer.getName();
        }
    }
    
    public static void init() {
        FileConfig config = ComboFly.get().getKothsConfig();

        for (String key : config.getConfiguration().getConfigurationSection("KOTHS").getKeys(false)) {
            KoTH koth = new KoTH(key);
            Location loc1 = LocationUtil.deserialize(config.getString("KOTHS." + key + ".cuboid.higher"));
            Location loc2 = LocationUtil.deserialize(config.getString("KOTHS." + key + ".cuboid.lower"));
            koth.setCuboid(new Cuboid(loc1, loc2));
            koth.setCreator(config.getString("KOTHS." + key + ".creator"));
            koth.setEnabled(config.getBoolean("KOTHS." + key + ".enabled"));
            koths.add(koth);
        }
    }

    public static KoTH getFromName(String name) {
        for (KoTH koth : koths) {
            if (koth.getName().equalsIgnoreCase(name)) return koth;
        }
        return null;
    }

    public void save() {
        FileConfig config = ComboFly.get().getKothsConfig();

        config.getConfiguration().set("KOTHS." + name + ".cuboid.higher", LocationUtil.serialize(cuboid.getUpperCorner()));
        config.getConfiguration().set("KOTHS." + name + ".cuboid.lower", LocationUtil.serialize(cuboid.getLowerCorner()));
        config.getConfiguration().set("KOTHS." + name + ".date", date);
        config.getConfiguration().set("KOTHS." + name + ".creator" , creator);
        config.getConfiguration().set("KOTHS." + name + ".enabled", enabled);

        config.save();
        config.reload();
    }

    public void delete() {
        FileConfig config = ComboFly.get().getKothsConfig();

        config.getConfiguration().set("KOTHS." + name, null);
        config.save();
        config.reload();

        koths.remove(this);
    }

}
