package dev.panda.combofly.utilities.balance.impl;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.profile.Profile;
import dev.panda.combofly.utilities.balance.Balance;
import org.bukkit.entity.Player;

public class Normal implements Balance {

    @Override
    public double getBalance(Player player) {
        return Profile.getProfiles().get(player.getUniqueId()).getBalance();
    }

    @Override
    public void setBalance(Player player, int amount) {
        Profile.getProfiles().get(player.getUniqueId()).setBalance(amount);
    }

    @Override
    public void giveBalance(Player player, int amount) {
        Profile.getProfiles().get(player.getUniqueId()).setBalance(amount);
    }

    @Override
    public void removeBalance(Player player, int amount) {
        Profile.getProfiles().get(player.getUniqueId()).setBalance(amount);
    }
}
