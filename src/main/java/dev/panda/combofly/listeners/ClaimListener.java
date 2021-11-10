package dev.panda.combofly.listeners;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class ClaimListener implements Listener {

    public ClaimListener() {
        Bukkit.getPluginManager().registerEvents(this, ComboFly.get());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onClaimEntering(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        for (String claim : ComboFly.get().getClaimManager().getClaimList()) {
            CuboidSelection selection = new CuboidSelection(
                    ComboFly.get().getClaimManager().getClaimWorld(claim),
                    ComboFly.get().getClaimManager().getLocation(claim, "cornerA"),
                    ComboFly.get().getClaimManager().getLocation(claim, "cornerB"));

            if (selection.contains(player.getLocation())) {
                if (ComboFly.get().getCombatManager().hasCooldown(player)) {
                    player.setVelocity(player.getWorld().getSpawnLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(-2));
                }
            }

            if (selection.contains(to)
                    && !selection.contains(from)) {
                if (ComboFly.get().getClaimManager().isPvPClaim(claim)) {
                    continue;
                }
                if (ComboFly.get().getCombatManager().hasCooldown(player)) {
                    player.setVelocity(player.getWorld().getSpawnLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(-1));
                }
                player.sendMessage(ChatUtil.translate("&eNow entering: &c" + (ComboFly.get().getClaimManager().isPvPClaim(claim) ? claim : ("&a" + claim))));
            }
            else {
                if (!selection.contains(from) || selection.contains(to)) {
                    continue;
                }
                if (ComboFly.get().getCombatManager().hasCooldown(player)) {
                    player.setVelocity(player.getWorld().getSpawnLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(-1));
                }
                player.sendMessage(ChatUtil.translate("&eNow leaving: &c" + (ComboFly.get().getClaimManager().isPvPClaim(claim) ? claim : ("&a" + claim))));
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            for (String claim : ComboFly.get().getClaimManager().getClaimList()) {
                CuboidSelection selection = new CuboidSelection(
                        ComboFly.get().getClaimManager().getClaimWorld(claim),
                        ComboFly.get().getClaimManager().getLocation(claim, "cornerA"),
                        ComboFly.get().getClaimManager().getLocation(claim, "cornerB"));
                Player player = (Player)event.getEntity();
                if (selection.contains(player.getLocation())
                        && !ComboFly.get().getClaimManager().isPvPClaim(claim)) {
                    event.setCancelled(true);
                }
                if (selection.contains(event.getDamager().getLocation())
                        && !ComboFly.get().getClaimManager().isPvPClaim(claim)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            for (String claim : ComboFly.get().getClaimManager().getClaimList()) {
                CuboidSelection selection = new CuboidSelection(
                        ComboFly.get().getClaimManager().getClaimWorld(claim),
                        ComboFly.get().getClaimManager().getLocation(claim, "cornerA"),
                        ComboFly.get().getClaimManager().getLocation(claim, "cornerB"));
                Player player = (Player) event.getEntity();
                if (selection.contains(player.getLocation())
                        && !ComboFly.get().getClaimManager().isPvPClaim(claim)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler()
    public void enderpearlClaim(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            for (String claim : ComboFly.get().getClaimManager().getClaimList()) {
                CuboidSelection selection = new CuboidSelection(
                        ComboFly.get().getClaimManager().getClaimWorld(claim),
                        ComboFly.get().getClaimManager().getLocation(claim, "cornerA"),
                        ComboFly.get().getClaimManager().getLocation(claim, "cornerB"));
                Player player = event.getPlayer();
                if (selection.contains(event.getTo())) {
                    event.setCancelled(true);
                    ComboFly.get().getEnderpearlManager().removeCooldown(player);
                    player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
                    player.updateInventory();
                    player.sendMessage(ChatUtil.translate("&cYou can not use &lEnderpearl &cthere."));
                    return;
                }
            }
        }
    }
}
