package dev.panda.lib.rank.impl;

import dev.panda.lib.rank.Rank;
import dev.panda.lib.rank.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PermissionsEx implements Rank {

    @Override
    public String getName(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return RankManager.getInstance().getChat().getPrimaryGroup(
                String.valueOf(RankManager.getInstance().getPlugin().getServer().getWorlds().get(0).getName()), player);
    }

    @Override
    public String getPrefix(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return RankManager.getInstance().getChat().getPlayerPrefix(String.valueOf(
                RankManager.getInstance().getPlugin().getServer().getWorlds().get(0).getName()), player);
    }

    @Override
    public String getSuffix(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return RankManager.getInstance().getChat().getPlayerSuffix(String.valueOf(
                RankManager.getInstance().getPlugin().getServer().getWorlds().get(0).getName()), player);
    }

    @Override
    public String getColor(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return RankManager.getInstance().getChat().getPrimaryGroup(String.valueOf(
                RankManager.getInstance().getPlugin().getServer().getWorlds().get(0).getName()), player);
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
