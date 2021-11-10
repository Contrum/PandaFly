package dev.panda.lib.rank.impl;

import club.frozed.core.ZoomAPI;
import dev.panda.lib.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Zoom implements Rank {

    @Override
    public String getName(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return ZoomAPI.getRankName(player);
    }

    @Override
    public String getPrefix(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return ZoomAPI.getRankPrefix(player);
    }

    @Override
    public String getSuffix(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return ZoomAPI.getRankSuffix(player);
    }

    @Override
    public String getColor(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return ZoomAPI.getRankColor(player) + ZoomAPI.getRankName(player);
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
