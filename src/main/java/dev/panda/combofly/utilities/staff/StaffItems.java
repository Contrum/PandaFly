package dev.panda.combofly.utilities.staff;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StaffItems {

    public static ItemStack getPhase() {
        ItemStack item = new ItemStack(Material.valueOf(ComboFly.get().getStaffitemsConfig().getString("PHASE.ITEM").toUpperCase()),
                1, (short) ComboFly.get().getStaffitemsConfig().getInt("PHASE.DATA"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate(ComboFly.get().getStaffitemsConfig().getString("PHASE.NAME")));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getInspector() {
        ItemStack item = new ItemStack(Material.valueOf(ComboFly.get().getStaffitemsConfig().getString("INSPECTOR.ITEM").toUpperCase()),
                1, (short) ComboFly.get().getStaffitemsConfig().getInt("INSPECTOR.DATA"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate(ComboFly.get().getStaffitemsConfig().getString("INSPECTOR.NAME")));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getWorldEdit() {
        ItemStack item = new ItemStack(Material.valueOf(ComboFly.get().getStaffitemsConfig().getString("WORLD-EDIT.ITEM").toUpperCase()),
                1, (short) ComboFly.get().getStaffitemsConfig().getInt("WORLD-EDIT.DATA"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate(ComboFly.get().getStaffitemsConfig().getString("WORLD-EDIT.NAME")));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getFreeze() {
        ItemStack item = new ItemStack(Material.valueOf(ComboFly.get().getStaffitemsConfig().getString("FREEZE.ITEM").toUpperCase()),
                1, (short) ComboFly.get().getStaffitemsConfig().getInt("FREEZE.DATA"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate(ComboFly.get().getStaffitemsConfig().getString("FREEZE.NAME")));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getMiners() {
        ItemStack item = new ItemStack(Material.valueOf(ComboFly.get().getStaffitemsConfig().getString("MINER.ITEM").toUpperCase()),
                1, (short) ComboFly.get().getStaffitemsConfig().getInt("MINER.DATA"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate(ComboFly.get().getStaffitemsConfig().getString("MINER.NAME")));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getOnlineStaff() {
        ItemStack item = new ItemStack(Material.valueOf(ComboFly.get().getStaffitemsConfig().getString("STAFF-ONLINE.ITEM").toUpperCase()),
                1, (short) ComboFly.get().getStaffitemsConfig().getInt("STAFF-ONLINE.DATA"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate(ComboFly.get().getStaffitemsConfig().getString("STAFF-ONLINE.NAME")));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getVanishOn() {
        ItemStack item = new ItemStack(Material.valueOf(ComboFly.get().getStaffitemsConfig().getString("VANISH-ON.ITEM").toUpperCase()),
                1, (short) ComboFly.get().getStaffitemsConfig().getInt("VANISH-ON.DATA"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate(ComboFly.get().getStaffitemsConfig().getString("VANISH-ON.NAME")));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getVanishOff() {
        ItemStack item = new ItemStack(Material.valueOf(ComboFly.get().getStaffitemsConfig().getString("VANISH-OFF.ITEM").toUpperCase()),
                1, (short) ComboFly.get().getStaffitemsConfig().getInt("VANISH-OFF.DATA"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate(ComboFly.get().getStaffitemsConfig().getString("VANISH-OFF.NAME")));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getRandomTeleport() {
        ItemStack item = new ItemStack(Material.valueOf(ComboFly.get().getStaffitemsConfig().getString("RANDOM-TELEPORT.ITEM").toUpperCase()),
                1, (short) ComboFly.get().getStaffitemsConfig().getInt("RANDOM-TELEPORT.DATA"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate(ComboFly.get().getStaffitemsConfig().getString("RANDOM-TELEPORT.NAME")));
        item.setItemMeta(meta);
        return item;
    }
}
