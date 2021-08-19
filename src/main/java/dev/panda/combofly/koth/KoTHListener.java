package dev.panda.combofly.koth;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.koth.event.EnterCuboidEvent;
import dev.panda.combofly.koth.event.LeaveCuboidEvent;
import dev.panda.combofly.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class KoTHListener implements Listener {

    public KoTHListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ComboFly.get());
    }

    @EventHandler
    public void onEnterZone(EnterCuboidEvent event) {
        Player player = event.getPlayer();
        KoTH koth = event.getKoth();
        koth.getPeopleInCuboid().add(player);

        if (koth.getCapPlayer() == null) koth.setCappingPlayer(player);
    }

    @EventHandler
    public void onLeaveZone(LeaveCuboidEvent event) {
        Player player = event.getPlayer();
        KoTH koth = event.getKoth();
        koth.getPeopleInCuboid().remove(player);

        if (koth.getCapPlayer() != null && koth.getCapPlayer().equals(player)) {
            koth.setCappingPlayer(null);
        }
        if (!koth.getPeopleInCuboid().isEmpty() && koth.getCapPlayer() == null) {
            System.out.println(2);
            koth.setCappingPlayer(koth.getPeopleInCuboid().get(0));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        if (KoTH.getActiveKoth() != null) {
            Player player = event.getPlayer();
            KoTH koth = KoTH.getActiveKoth();
            Location to = event.getTo();
            Location from = event.getFrom();

            if (!koth.getCuboid().contains(from) && koth.getCuboid().contains(to)) {
                Bukkit.getServer().getPluginManager().callEvent(new EnterCuboidEvent(player, koth));
            }
            else if (koth.getCuboid().contains(from) && !koth.getCuboid().contains(to)) {
                Bukkit.getServer().getPluginManager().callEvent(new LeaveCuboidEvent(player, koth));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTeleport(PlayerTeleportEvent event) {
        if (KoTH.getActiveKoth() != null) {
            Player player = event.getPlayer();
            KoTH koth = KoTH.getActiveKoth();
            Location to = event.getTo();
            Location from = event.getFrom();

            if (!koth.getCuboid().contains(from) && koth.getCuboid().contains(to)) {
                Bukkit.getServer().getPluginManager().callEvent(new EnterCuboidEvent(player, koth));
            }
            else if (koth.getCuboid().contains(from) && !koth.getCuboid().contains(to)) {
                Bukkit.getServer().getPluginManager().callEvent(new LeaveCuboidEvent(player, koth));
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onWandInteract(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK)) return;

        Player player = event.getPlayer();

        if (player.getItemInHand() != null && player.getItemInHand().isSimilar(Selection.SELECTION_WAND)) {
            Block clicked = event.getClickedBlock();
            int location = 0;

            Selection selection = Selection.createOrGetSelection(player);

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                selection.setPoint2(clicked.getLocation());
                location = 2;
            } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                selection.setPoint1(clicked.getLocation());
                location = 1;
            }

            event.setCancelled(true);
            event.setUseItemInHand(Event.Result.DENY);
            event.setUseInteractedBlock(Event.Result.DENY);

            String message = CC.AQUA + (location == 1 ? "First" : "Second") +
                    " location " + CC.YELLOW + "(" + CC.GREEN +
                    clicked.getX() + CC.YELLOW + ", " + CC.GREEN +
                    clicked.getY() + CC.YELLOW + ", " + CC.GREEN +
                    clicked.getZ() + CC.YELLOW + ")" + CC.AQUA + " has been set!";

            if (selection.isFullObject()) {
                message += CC.RED + " (" + CC.YELLOW + selection.getCuboid().volume() + CC.AQUA + " blocks" +
                        CC.RED + ")";
            }

            player.sendMessage(message);
        }
    }
}
