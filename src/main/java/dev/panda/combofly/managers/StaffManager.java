package dev.panda.combofly.managers;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.combofly.utilities.staff.StaffItems;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaffManager {

    public List<String> staff = new ArrayList<>();
    public List<String> staffmode = new ArrayList<>();
    public List<String> staffchat = new ArrayList<>();

    public HashMap<String, ItemStack[]> armor = new HashMap<>();
    public HashMap<String, ItemStack[]> inventory = new HashMap<>();
    @Getter public boolean staffEnable = ComboFly.get().getMainConfig().getBoolean("STAFF.ENABLE");

    public boolean isStaff(Player player) {
        return player.hasPermission("pandafly.rank.staff");
    }

    public void setStaff(Player player) {
        staff.add(player.getName());
    }

    public void removeStaff(Player player) {
        staff.remove(player.getName());
    }

    public boolean isStaffMode(Player player) {
        return staffmode.contains(player.getName());
    }

    public void setStaffMode(Player player) {
        staffmode.add(player.getName());
        ComboFly.get().getVanishManager().setVanish(player);
        this.enableStaffMode(player);
        player.setGameMode(GameMode.CREATIVE);
    }

    public void removeStaffMode(Player player) {
        staffmode.remove(player.getName());
        ComboFly.get().getVanishManager().removeVanish(player);
        this.disableStaffMode(player);
        player.setGameMode(GameMode.SURVIVAL);
    }

    public boolean isStaffChat(Player player) {
        return staffchat.contains(player.getName());
    }

    public void setStaffChat(Player player) {
        staffchat.add(player.getName());
    }

    public void removeStaffChat(Player player) {
        staffchat.remove(player.getName());
    }

    public void sendMessageAllStaffs(String message) {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            if (isStaff(online)) {
                online.sendMessage(ChatUtil.translate(message));
            }
        }
    }

    public void getOnlineStaff(CommandSender sender) {
        sender.sendMessage(ChatUtil.translate("&cStaff's Online"));
        sender.sendMessage(ChatUtil.translate(""));
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            if (ComboFly.get().getStaffManager().isStaff(online)) {
                sender.sendMessage(ChatUtil.translate("&f\u2738 &c" + online.getName()));
            }
        }
        sender.sendMessage(ChatUtil.translate(""));
    }

    public void saveArmor(Player player) {
        armor.put(player.getName(), player.getInventory().getArmorContents());
    }

    public void restoreArmor(Player player) {
        player.getInventory().setArmorContents(armor.get(player.getName()));
        armor.remove(player.getName());
    }

    public void saveInventory(Player player) {
        inventory.put(player.getName(), player.getInventory().getContents());
    }

    public void restoreInventory(Player player) {
        player.getInventory().setContents(inventory.get(player.getName()));
        player.updateInventory();
        inventory.remove(player.getName());
    }

    public void setStaffModeItems(Player player) {
        player.getInventory().clear();
        if (ComboFly.get().getStaffitemsConfig().getBoolean("PHASE.ENABLE")) {
            player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("PHASE.SLOT"), StaffItems.getPhase());
        }
        if (ComboFly.get().getStaffitemsConfig().getBoolean("INSPECTOR.ENABLE")) {
            player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("INSPECTOR.SLOT"), StaffItems.getInspector());
        }
        if (ComboFly.get().getStaffitemsConfig().getBoolean("WORLD-EDIT.ENABLE")) {
            player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("WORLD-EDIT.SLOT"), StaffItems.getWorldEdit());
        }
        if (ComboFly.get().getStaffitemsConfig().getBoolean("FREEZE.ENABLE")) {
            player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("FREEZE.SLOT"), StaffItems.getFreeze());
        }
        if (ComboFly.get().getStaffitemsConfig().getBoolean("MINER.ENABLE")) {
            player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("MINER.SLOT"), StaffItems.getMiners());
        }
        if (ComboFly.get().getStaffitemsConfig().getBoolean("STAFF-ONLINE.ENABLE")) {
            player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("STAFF-ONLINE.SLOT"), StaffItems.getOnlineStaff());
        }
        if (ComboFly.get().getStaffitemsConfig().getBoolean("VANISH-ON.ENABLE")) {
            if (ComboFly.get().getVanishManager().isVanish(player)) {
                player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("VANISH-ON.SLOT"), StaffItems.getVanishOn());
            }
            else {
                player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("VANISH-OFF.SLOT"), StaffItems.getVanishOff());
            }
        }
        if (ComboFly.get().getStaffitemsConfig().getBoolean("RANDOM-TELEPORT.ENABLE")) {
            player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("RANDOM-TELEPORT.SLOT"), StaffItems.getRandomTeleport());
        }
        player.updateInventory();
    }

    public void enableStaffMode(Player player) {
        this.saveArmor(player);
        this.saveInventory(player);
        player.getInventory().setArmorContents(null);
        this.setStaffModeItems(player);
    }

    public void disableStaffMode(Player player) {
        player.getInventory().clear();
        this.restoreArmor(player);
        this.restoreInventory(player);
        player.updateInventory();
    }
}
