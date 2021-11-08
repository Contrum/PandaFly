package dev.panda.combofly.listeners;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import dev.panda.combofly.ComboFly;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

public class KnockBackListener implements Listener {

    private final ComboFly plugin = ComboFly.get();
    private final boolean maxHeight = plugin.getMainConfig().getBoolean("KNOCKBACK.ENABLE-MAXHEIGHT");

    public KnockBackListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player attacked = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();

            for (String claim : plugin.getClaimManager().getClaimList()) {
                CuboidSelection selection = new CuboidSelection(
                        plugin.getClaimManager().getClaimWorld(claim),
                        plugin.getClaimManager().getLocation(claim, "cornerA"),
                        plugin.getClaimManager().getLocation(claim, "cornerB"));
                if (selection.contains(attacked.getLocation()) && !plugin.getClaimManager().isPvPClaim(claim))
                    return;
                if (selection.contains(attacker.getLocation()) && !plugin.getClaimManager().isPvPClaim(claim))
                    return;
            }

            if (plugin.getStaffManager().isStaffMode(attacker)) return;

            if (plugin.getKbManager().hasKnockBackEnabled()) {
                double vecX = plugin.getKbManager().getKbX();
                double vecY = plugin.getKbManager().getKbY();
                double vecZ = plugin.getKbManager().getKbZ();
                Vector dir = attacker.getLocation().getDirection();
                Vector vec = new Vector(dir.getX() * vecX, vecY, dir.getZ() * vecZ);
                attacked.setVelocity(vec);
                double highestKB = attacked.getLocation().getBlockY() - attacker.getLocation().getBlockY();
                double heightKB = plugin.getKbManager().getHeightKB();
                Vector dir2 = attacker.getLocation().getDirection();

                ComboFly.get().getServer().getScheduler().runTaskLaterAsynchronously(ComboFly.get(), () -> {
                    if (maxHeight) {
                        if (highestKB == heightKB) {
                            Vector vec2 = new Vector(dir2.getX() * vecX, 0, dir2.getZ() * vecZ);
                            attacked.setVelocity(vec2);
                        }
                    }
                    if (highestKB > heightKB) {
                        Vector vec2 = new Vector(dir2.getX() * vecX, -0.05, dir2.getZ() * vecZ);
                        attacked.setVelocity(vec2);
                    }
                }, 0L);
            }
        }
    }

    @EventHandler
    public void onTicksDamage(PlayerJoinEvent event) {
        if (plugin.getKbManager().hasMaxTicksEnabled()) {
            int ticks = ComboFly.get().getMainConfig().getInt("HIT-TICKS.TICKS");
            event.getPlayer().setMaximumNoDamageTicks(ticks);
        }
    }
}

