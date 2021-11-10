package dev.panda.lib.rank.impl;

import dev.panda.lib.rank.Rank;
import me.quartz.hestia.HestiaAPI;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HestiaCore implements Rank {

    @Override
    public String getName(UUID uuid) {
        return HestiaAPI.instance.getRank(uuid);
    }

    @Override
    public String getPrefix(UUID uuid) {
        return HestiaAPI.instance.getRankPrefix(uuid);
    }

    @Override
    public String getSuffix(UUID uuid) {
        return HestiaAPI.instance.getRankSuffix(uuid);
    }

    @Override
    public String getColor(UUID uuid) {
        return HestiaAPI.instance.getRank(uuid);
    }

    @Override
    public String getRealName(Player player) {
        return null;
    }

    @Override
    public int getWeight(UUID uuid) {
        return 0;
    }
}
