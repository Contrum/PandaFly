package dev.panda.combofly.balance.impl;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.balance.Balance;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class Vault implements Balance {

    @Override
    public double getBalance(Player player) {
        return ComboFly.get().getEcon().getBalance(player);
    }

    @Override
    public void setBalance(Player player, int amount) {
        EconomyResponse response = ComboFly.get().getEcon().depositPlayer(player, amount);
        ComboFly.get().getEcon().format(response.amount);
    }

    @Override
    public void giveBalance(Player player, int amount) {
        EconomyResponse response = ComboFly.get().getEcon().depositPlayer(player, getBalance(player) + amount);
        ComboFly.get().getEcon().format(response.amount);
    }

    @Override
    public void removeBalance(Player player, int amount) {
        EconomyResponse response = ComboFly.get().getEcon().withdrawPlayer(player, amount);
        ComboFly.get().getEcon().format(response.amount);
    }
}
