package dev.panda.combofly.kit.listener;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.combofly.utilities.Time;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class KitListener implements Listener {

    private final Set<String> cooldownEdit = new HashSet<>();

    public KitListener() {
        Bukkit.getPluginManager().registerEvents(this, ComboFly.get());
    }

    @EventHandler
    public void onKitInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getTitle().equals(ChatUtil.translate("&c&lEditing Kit &7➤ &f&l" + ComboFly.get().getKitManager().getName(player))) ||
                event.getInventory().getTitle().equals(ChatUtil.translate("&c&lSelect a Default kit")) ||
                event.getInventory().getTitle().equals(ChatUtil.translate("&c&lEditor Kits"))) {

            event.setCancelled(true);

            if (event.getClickedInventory() == null || event.getInventory() != event.getClickedInventory()) return;

            if (event.getCurrentItem() == null
                    || event.getSlotType() == null
                    || event.getCurrentItem().getType().equals(Material.AIR))
                return;

            String kit = ComboFly.get().getKitManager().getName(player);

            ItemStack is = event.getCurrentItem();

            if (ComboFly.get().getMenuKitManager().getToggle(kit).isSimilar(is)) {
                ComboFly.get().getKitManager().setState(kit, !ComboFly.get().getKitManager().isState(kit));
                event.setCurrentItem(ComboFly.get().getMenuKitManager().getToggle(kit));
            }
            else if (ComboFly.get().getMenuKitManager().getCooldown(kit).isSimilar(is)) {
                player.closeInventory();
                player.sendMessage(ChatUtil.translate("&8[&c&l!&8] &7Enter &acooldown &7duration to kit &a" + kit + " &7or type: &ccancel"));
                player.sendMessage(ChatUtil.translate("&8[&c&l!&8] &7Usage for example: &a45m 30s"));
                this.cooldownEdit.add(player.getName());
            }
            else if (ComboFly.get().getMenuKitManager().getGoEditor().isSimilar(is)) {
                ComboFly.get().getInventoryManager().getKitsEditor(player);
            }
            else if (ComboFly.get().getMenuKitManager().getSaveInv().isSimilar(is)) {
                ComboFly.get().getKitManager().setItems(kit, player);
                player.closeInventory();
                player.sendMessage(ChatUtil.translate("&aSuccessfully! &7save Inventory for the &c" + kit + " &7kit."));
            }
            else if (ComboFly.get().getMenuKitManager().getDefaultKit().isSimilar(is)) {
                ComboFly.get().getInventoryManager().getKitDefault(player);
            }
            else if (ComboFly.get().getMenuKitManager().getBack().isSimilar(is)) {
                ComboFly.get().getInventoryManager().getKitsEditor(player);
            }
            for (String key : ComboFly.get().getKitConfig().getConfiguration().getConfigurationSection("KITS").getKeys(false)) {
                if (ComboFly.get().getMenuKitManager().getKits(key).isSimilar(is)) {
                    ComboFly.get().getKitManager().setName(player, key);
                    ComboFly.get().getInventoryManager().getEditKit(player, key);
                }
                else if (ComboFly.get().getMenuKitManager().getKitsDefault(key).isSimilar(is)) {
                    ComboFly.get().getKitManager().setDefaultKit(key);
                    player.closeInventory();
                    player.sendMessage(ChatUtil.translate("&aSuccesfully! &7kit &a" + key + " &7is now &6default &7kit"));
                }
            }
        }
    }

    @EventHandler
    public void signColor(SignChangeEvent event) {
        String[] lines = event.getLines();
        for (int n = 0; n <= 3; n++) event.setLine(n, ChatUtil.translate(lines[n]));
    }

    @EventHandler
    public void SignKit(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            BlockState state = block.getState();
            if (state instanceof Sign) {
                Sign sign = (Sign) state;
                String line1 = sign.getLine(0);
                String line2 = sign.getLine(1);
                if (line1.contains("[Kit]")) {
                    for (String kit : ComboFly.get().getKitConfig().getConfiguration().getConfigurationSection("KITS").getKeys(false)) {
                        if (line2.contains(kit)) {
                            if (ComboFly.get().getKitManager().hasCooldown(kit, player)) {
                                player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("KIT.COOLDOWN")
                                        .replace("{kit}", kit)
                                        .replace("{cooldown}", String.valueOf(ComboFly.get().getKitManager().getCooldown(kit, player)))));
                                return;
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (this.cooldownEdit.contains(player.getName())) {
            event.setCancelled(true);

            String message = event.getMessage();
            String kitName = ComboFly.get().getKitManager().getName(player);

            if (message.equalsIgnoreCase("cancel")) {
                this.cooldownEdit.remove(player.getName());
                ComboFly.get().getInventoryManager().getEditKit(player, kitName);
                player.sendMessage(ChatUtil.translate("&cYour cooldown edit has been cancelled."));
                return;
            }

            int delay = Time.format(message);
            this.cooldownEdit.remove(player.getName());
            ComboFly.get().getKitManager().setDelayKit(kitName, delay);
            ComboFly.get().getInventoryManager().getEditKit(player, kitName);
            player.sendMessage(ChatUtil.translate("&aSuccessfully! &7set &a" + Time.formatDuration(delay * 1000L) + " &7cooldown of &c" + kitName + " &7Kit."));
        }
    }
}