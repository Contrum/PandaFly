package dev.panda.combofly.balance;

import org.bukkit.entity.Player;

public interface Balance {

    double getBalance(Player player);

    void setBalance(Player player, int amount);

    void giveBalance(Player player, int amount);

    void removeBalance(Player player, int amount);
}
