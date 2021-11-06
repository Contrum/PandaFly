package dev.panda.combofly.managers;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;

public class KillStreakManager {

    public void messageKillStreak(int streak, Player player) {
        String message = ComboFly.get().getMainConfig().getString("KILLSTREAK.MESSAGE")
                .replace("{streak}", String.valueOf(streak))
                .replace("{player}", player.getName());
        Bukkit.broadcastMessage(ChatUtil.translate(message));
    }

    public void messageReward(int streak, Player player){
        for (String message : ComboFly.get().getMainConfig().getConfiguration().getConfigurationSection("KILLSTREAK.REWARDS.STREAK." + streak + ".MESSAGE").getKeys(false)) {
            player.sendMessage(ChatUtil.translate(message));
        }
    }

    public void getRepairAll(Player p) {
        for (String streak : ComboFly.get().getMainConfig().getConfiguration().getConfigurationSection("KILLSTREAK.REWARDS.STREAK").getKeys(false)) {
            if (ComboFly.get().getMainConfig().getBoolean("KILLSTREAK.REWARDS.STREAK." + streak + ".REPAIRALL")) {
                for (ItemStack items : p.getInventory().getContents()) {
                    this.canRepair(items);
                }
                for (ItemStack items : p.getInventory().getArmorContents()) {
                    this.canRepair(items);
                }
                p.updateInventory();
            }
        }
    }
    public void getReward(int streak, Player player) {
        for (String commands : ComboFly.get().getMainConfig().getStringList("KILLSTREAK.REWARDS.STREAK." + streak + ".COMMANDS")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands
                    .replace("{player}", player.getName()));
        }
    }
    private void canRepair(ItemStack item) {
        if (item instanceof Repairable) {
            item.setDurability((short)0);
        }
    }
}