package dev.panda.lib.rank.impl;

import dev.panda.lib.rank.Rank;
import me.activated.core.api.player.GlobalPlayer;
import me.activated.core.api.player.PlayerData;
import me.activated.core.plugin.AquaCoreAPI;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AquaCore implements Rank {

    @Override
    public String getName(UUID uuid) {
        PlayerData data = AquaCoreAPI.INSTANCE.getPlayerData(uuid);
        return data == null ? "No Data" : data.getHighestRank().getName();
    }

    @Override
    public String getPrefix(UUID uuid) {
        PlayerData data = AquaCoreAPI.INSTANCE.getPlayerData(uuid);
        return data == null ? "No Data" : data.getHighestRank().getPrefix();
    }

    @Override
    public String getSuffix(UUID uuid) {
        PlayerData data = AquaCoreAPI.INSTANCE.getPlayerData(uuid);
        return data == null ? "No Data" : data.getHighestRank().getSuffix();
    }

    @Override
    public String getColor(UUID uuid) {
        PlayerData data = AquaCoreAPI.INSTANCE.getPlayerData(uuid);
        return data == null ? "No Data" : data.getHighestRank().getColor() + data.getHighestRank().getName();
    }

    @Override
    public String getRealName(Player player) {
        return AquaCoreAPI.INSTANCE.getRealName(player);
    }

    @Override
    public int getWeight(UUID uuid) {
        GlobalPlayer data = AquaCoreAPI.INSTANCE.getGlobalPlayer(uuid);
        return data == null ? 0 : data.getRankWeight();
    }
}
