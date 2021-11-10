package dev.panda.combofly.profile;

import com.google.common.collect.Maps;
import dev.panda.combofly.ComboFly;
import dev.panda.combofly.profile.comparator.ProfileDeathsComparator;
import dev.panda.combofly.profile.comparator.ProfileKillsComparator;
import dev.panda.combofly.profile.entry.KDEntry;
import dev.panda.lib.file.FileConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter @Setter
public class Profile implements Supplier<Profile> {

    @Getter public static Map<UUID, Profile> profiles = Maps.newHashMap();
    @Getter public static Map<String, List<Profile>> leaderboards = Maps.newHashMap();
    private final UUID uuid;
    private String name;
    private KDEntry kd;
    private int balance;

    public Profile(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.kd = new KDEntry(0, 0);
    }

    public static void init() {
        ComboFly.get().getServer().getScheduler().runTaskTimerAsynchronously(ComboFly.get(), () -> {
            for (Player online : Bukkit.getOnlinePlayers()) {
                profiles.get(online.getUniqueId()).save(online);
            }
        }, 6000L, 6000L);

        ComboFly.get().getProfileConfig().getConfiguration().getConfigurationSection("PROFILES").getKeys(false).forEach(uuids -> {
            UUID uuid = UUID.fromString(uuids);
            Profile profile = new Profile(uuid, Bukkit.getOfflinePlayer(uuid).getName());
            profile.load();
            profiles.put(uuid, profile);
        });

        ComboFly.get().getServer().getScheduler().runTaskTimerAsynchronously(ComboFly.get(), () -> {
            leaderboards.clear();
            leaderboards.put("kills", profiles.values().stream()
                    .sorted(new ProfileKillsComparator().reversed())
                    .collect(Collectors.toList()));
            leaderboards.put("deaths", profiles.values().stream()
                    .sorted(new ProfileDeathsComparator().reversed())
                    .collect(Collectors.toList()));
        }, 40L, 1800L);
    }

    public static Profile get(UUID uuid) {
        return profiles.get(uuid);
    }

    public void save(Player player) {
        FileConfig config = ComboFly.get().getProfileConfig();

        if (player != null) config.getConfiguration().set("PROFILES." + uuid + ".name", player.getName());
        config.getConfiguration().set("PROFILES." + uuid + ".kills", kd.getKills());
        config.getConfiguration().set("PROFILES." + uuid + ".deaths", kd.getDeaths());
        config.getConfiguration().set("PROFILES." + uuid + ".balance", balance);

        config.save();
        config.reload();
    }

    public void load() {
        FileConfig config = ComboFly.get().getProfileConfig();
        try {
            this.name = Bukkit.getOfflinePlayer(uuid).getName();
            this.kd.setKills(config.getInt("PROFILES." + uuid + ".kills"));
            this.kd.setDeaths(config.getInt("PROFILES." + uuid + ".deaths"));
            this.balance = config.getInt("PROFILES." + uuid + ".balance");
        }
        catch (Exception exception) {
            this.kd.setKills(0);
            this.kd.setDeaths(0);
            this.balance = 0;
        }
    }

    @Override
    public Profile get() {
        return this;
    }
}