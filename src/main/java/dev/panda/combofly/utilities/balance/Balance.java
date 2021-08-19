package dev.panda.combofly.utilities.balance;

import org.bukkit.entity.Player;

public interface Balance {

    double getBalance(Player player);

    void setBalance(Player player, int amount);

    void giveBalance(Player player, int amount);

    void removeBalance(Player player, int amount);
}
