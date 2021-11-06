package dev.panda.combofly.listeners;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.combofly.utilities.staff.StaffItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class
StaffListener implements Listener {

    private final ComboFly plugin = ComboFly.get();

    public StaffListener() {
        Bukkit.getPluginManager().registerEvents(this, ComboFly.get());
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getName().equals("TulioTriste") || player.getName().equals("Risas")) {
            player.sendMessage(ChatUtil.translate("&7&m--------------------------------"));
            player.sendMessage(ChatUtil.translate("&4&lThis server is using the PandaFly"));
            player.sendMessage(ChatUtil.translate(""));
            player.sendMessage(ChatUtil.translate(" &cPlugin Version: &f" + plugin.getDescription().getVersion()));
            player.sendMessage(ChatUtil.translate(" &cPlugin Name: &f" + plugin.getDescription().getName()));
            player.sendMessage(ChatUtil.translate(""));
            player.sendMessage(ChatUtil.translate(" &cServer Version: &f" + plugin.getServer().getVersion()));
            player.sendMessage(ChatUtil.translate(" &cSpigot Name: &f" + plugin.getServer().getName()));
            player.sendMessage(ChatUtil.translate("&7&m--------------------------------"));
        }

        if (ComboFly.get().getStaffManager().isStaffEnable() && ComboFly.get().getStaffManager().isStaff(player)) {
            ComboFly.get().getStaffManager().setStaff(player);
            if (ComboFly.get().getMainConfig().getBoolean("STAFF.STAFFMODE-JOIN")) {
                ComboFly.get().getStaffManager().setStaffMode(player);
            }
            if (ComboFly.get().getMainConfig().getBoolean("STAFF.JOIN-MESSAGE.ENABLE")) {
                ComboFly.get().getStaffManager().sendMessageAllStaffs(ComboFly.get().getMainConfig().getString("STAFF.JOIN-MESSAGE.MESSAGE")
                        .replace("{staff}", player.getName()));
            }
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (ComboFly.get().getStaffManager().isStaffEnable() && ComboFly.get().getStaffManager().isStaff(player)) {
            ComboFly.get().getStaffManager().removeStaff(player);
            if (ComboFly.get().getMainConfig().getBoolean("STAFF.QUIT-MESSAGE.ENABLE")) {
                ComboFly.get().getStaffManager().sendMessageAllStaffs(ComboFly.get().getMainConfig().getString("STAFF.QUIT-MESSAGE.MESSAGE")
                        .replace("{staff}", player.getName()));
            }
        }
        if (ComboFly.get().getStaffManager().isStaffMode(player)) {
            ComboFly.get().getStaffManager().removeStaffMode(player);
        }
        if (ComboFly.get().getStaffManager().isStaffChat(player)) {
            ComboFly.get().getStaffManager().removeStaffChat(player);
        }
    }

    @EventHandler
    private void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (ComboFly.get().getStaffManager().isStaffEnable() && ComboFly.get().getStaffManager().isStaffMode(player)) {
            ComboFly.get().getVanishManager().setVanish(player);
        }
    }

    @EventHandler
    private void onStaffDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (ComboFly.get().getStaffManager().isStaffMode(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onStaffHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (ComboFly.get().getStaffManager().isStaffMode(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onStaffBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (ComboFly.get().getStaffManager().isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onStaffPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (ComboFly.get().getStaffManager().isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onStaffInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (ComboFly.get().getStaffManager().isStaffMode(player)) {
            event.setCancelled(true);
            player.updateInventory();
        }
    }

    @EventHandler
    private void onOnlineStaffClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (ComboFly.get().getStaffManager().isStaffEnable() && event.getView().getTitle().equals("Online Staff")) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();

            if (item != null && !item.getType().equals(Material.AIR)) {

                Player staff = Bukkit.getPlayer(item.getItemMeta().getDisplayName().substring(2));

                if (staff != null) {
                    player.teleport(staff);
                    player.sendMessage(ChatUtil.translate("&fTeleport to &c" + staff.getName() + "&f."));
                }
                else {
                    player.sendMessage(ChatUtil.translate("&cStaff not found."));
                }
                player.closeInventory();
            }
        }
    }

    @EventHandler
    private void onMinersClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (ComboFly.get().getStaffManager().isStaffEnable() && event.getView().getTitle().equals("Mining")) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();

            if (item != null && !item.getType().equals(Material.AIR)) {

                Player miner = Bukkit.getPlayer(item.getItemMeta().getDisplayName().substring(2));

                if (miner != null) {
                    player.teleport(miner);
                    player.sendMessage(ChatUtil.translate("&fTeleport to &c" + miner.getName() + "&f."));
                }
                else {
                    player.sendMessage(ChatUtil.translate("&cMiner not found."));
                }
                player.closeInventory();
            }
        }
    }

    @EventHandler
    private void onStaffPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if (ComboFly.get().getStaffManager().isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onStaffPickup(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (ComboFly.get().getStaffManager().isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onStaffDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (ComboFly.get().getStaffManager().isStaffMode(player)) {
            event.getDrops().clear();
        }
    }

    @EventHandler
    private void onFreezeItem(PlayerInteractEntityEvent event) {
        if (ComboFly.get().getStaffManager().isStaffEnable()
                && ComboFly.get().getStaffManager().isStaffMode(event.getPlayer())
                && ComboFly.get().getStaffManager().isStaff(event.getPlayer())) {

            if (event.getRightClicked() instanceof Player) {
                Player player = event.getPlayer();
                Player target = (Player) event.getRightClicked();
                ItemStack item = player.getInventory().getItemInHand();

                if (StaffItems.getFreeze().isSimilar(item)) {
                    player.performCommand("ss " + target.getName());
                }
            }
        }
    }

    @EventHandler
    private void onInspectorItem(PlayerInteractEntityEvent event) {
        if (ComboFly.get().getStaffManager().isStaffEnable()
                && ComboFly.get().getStaffManager().isStaffMode(event.getPlayer())
                && ComboFly.get().getStaffManager().isStaff(event.getPlayer())) {

            if (event.getRightClicked() instanceof Player) {
                Player player = event.getPlayer();
                Player target = (Player) event.getRightClicked();
                ItemStack item = player.getInventory().getItemInHand();

                if (StaffItems.getInspector().isSimilar(item)) {
                    player.openInventory(ComboFly.get().getInventoryManager().getInspector(target));
                }
            }
        }
    }

    @EventHandler
    private void onVanishItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (ComboFly.get().getStaffManager().isStaffEnable()
                && ComboFly.get().getStaffManager().isStaffMode(player)
                && ComboFly.get().getStaffManager().isStaff(player)) {

            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                ItemStack item = player.getInventory().getItemInHand();

                if (StaffItems.getVanishOn().isSimilar(item)) {
                    ComboFly.get().getVanishManager().removeVanish(player);
                    if (ComboFly.get().getStaffManager().isStaffMode(player)) {
                        player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("VANISH-OFF.SLOT"), StaffItems.getVanishOff());
                    }
                    player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("VANISH.DISABLE")));
                }
                if (StaffItems.getVanishOff().isSimilar(item)) {
                    ComboFly.get().getVanishManager().setVanish(player);
                    if (ComboFly.get().getStaffManager().isStaffMode(player)) {
                        player.getInventory().setItem(ComboFly.get().getStaffitemsConfig().getInt("VANISH-ON.SLOT"), StaffItems.getVanishOn());
                    }
                    player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("VANISH.ENABLE")));
                }
            }
        }
    }

    @EventHandler
    private void onOnlineStaffItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (ComboFly.get().getStaffManager().isStaffEnable()
                && ComboFly.get().getStaffManager().isStaffMode(player)
                && ComboFly.get().getStaffManager().isStaff(player)) {

            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                ItemStack item = player.getInventory().getItemInHand();

                if (StaffItems.getOnlineStaff().isSimilar(item)) {
                    player.openInventory(ComboFly.get().getInventoryManager().getOnlineStaff(player));
                }
            }
        }
    }

    @EventHandler
    private void onMinersItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (ComboFly.get().getStaffManager().isStaffEnable()
                && ComboFly.get().getStaffManager().isStaffMode(player)
                && ComboFly.get().getStaffManager().isStaff(player)) {

            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                ItemStack item = player.getInventory().getItemInHand();

                if (StaffItems.getMiners().isSimilar(item)) {
                    player.openInventory(ComboFly.get().getInventoryManager().getMiners(player));
                }
            }
        }
    }

    @EventHandler
    private void onRandomTeleportItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (ComboFly.get().getStaffManager().isStaffEnable()
                && ComboFly.get().getStaffManager().isStaffMode(player)
                && ComboFly.get().getStaffManager().isStaff(player)) {

            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                ItemStack item = player.getInventory().getItemInHand();

                if (StaffItems.getRandomTeleport().isSimilar(item)) {
                    List<Player> players = new ArrayList<>();
                    Random rand = new Random();

                    for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                        if (!ComboFly.get().getStaffManager().isStaff(online)
                                || !online.hasPermission("*")
                                || !online.isOp()) {
                            players.add(online);
                        }
                    }

                    if (players.isEmpty()) {
                        player.sendMessage(ChatUtil.translate("&cNothing to teleport."));
                        return;
                    }

                    Player random = players.get(rand.nextInt(players.size()));

                    if (player != random) {
                        player.teleport(random);
                        player.sendMessage(ChatUtil.translate("&fTeleport to &c" + random.getName() + "&f."));
                    }
                }
            }
        }
    }
}
