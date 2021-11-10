package dev.panda.combofly.kit;

import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MenuKitManager {

    public ItemStack getKitsDefault(String kit) {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate("&c" + kit + " &7kit"));
        meta.setLore(ChatUtil.translate(Arrays.asList(
                "&7&m-------------------------------",
                "&7Click here to select a &cDefault &7kit.",
                "&7&m-------------------------------")));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getBack() {
        ItemStack item = new ItemStack(Material.FEATHER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate("&7«« &b&lBack &7««"));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getKits(String kit) {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate("&c" + kit + " &7kit"));
        meta.setLore(ChatUtil.translate(Arrays.asList(
                "&7&m-------------------------------",
                "&7Click here to &cedit &7kit.",
                "&7&m-------------------------------")));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getSaveInv() {
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate("&c&lSave Kit"));
        meta.setLore(ChatUtil.translate(Arrays.asList(
                "&7&m-------------------------------",
                "&7Click here to save kit &cinventory.",
                "&7&m-------------------------------")));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getCooldown(String kit) {
        ItemStack item = new ItemStack(Material.WATCH);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate("&c&lCooldown Kit"));
        meta.setLore(ChatUtil.translate(Arrays.asList(
                "&7&m-------------------------------",
                "&7Click here to set &ccooldown &7kit.",
                "",
                "&7Current Cooldown: &c" + ComboFly.get().getKitManager().getDelayKitString(kit),
                "&7&m-------------------------------")));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getGoEditor() {
        ItemStack item = new ItemStack(Material.BED);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate("&c&lGo to Editor"));
        meta.setLore(ChatUtil.translate(Arrays.asList(
                "&7&m-------------------------------",
                "&7Click here to go &ceditor.",
                "&7&m-------------------------------")));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getToggle(String kit) {
        ItemStack item = new ItemStack(Material.REDSTONE_TORCH_ON);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate("&c&lToggle Kit"));
        meta.setLore(ChatUtil.translate(Arrays.asList(
                "&7&m-------------------------------",
                "&7Click here to enable or disable kit.",
                "",
                "&7➤ " + (ComboFly.get().getKitManager().isState(kit) ? "&aEnable" : "&cDisable"),
                "&7&m-------------------------------")));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getDefaultKit() {
        ItemStack item = new ItemStack(Material.ENCHANTMENT_TABLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.translate("&cDefault Kit"));
        meta.setLore(ChatUtil.translate(Arrays.asList(
                "&7&m-------------------------------",
                "&7Click here to select a default kit.",
                "&7&m-------------------------------")));
        item.setItemMeta(meta);
        return item;
    }
}
