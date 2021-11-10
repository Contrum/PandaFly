package dev.panda.combofly.listeners;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class CombatListener implements Listener {

	public CombatListener() {
		Bukkit.getServer().getPluginManager().registerEvents(this, ComboFly.get());
	}

	@EventHandler
	private void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (ComboFly.get().getCombatManager().hasCooldown(player)) {
			player.setHealth(0.0);
			Bukkit.broadcastMessage(ChatUtil.translate("&7[&4Combat&7] &c" + player.getName() + " &fhas been disconnect in Combat."));
		}
		ComboFly.get().getCombatManager().removeCooldown(player);
	}

	@EventHandler
	private void onKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		if (ComboFly.get().getCombatManager().hasCooldown(player)) {
			player.setHealth(0.0);
			Bukkit.broadcastMessage(ChatUtil.translate("&7[&4Combat&7] &c" + player.getName() + " &fhas been disconnect in Combat."));
		}
		ComboFly.get().getCombatManager().removeCooldown(player);
	}

	@EventHandler
	private void onDeath(PlayerDeathEvent event) {
		if (event.getEntity() != null) {
			Player player = event.getEntity();
			if (ComboFly.get().getCombatManager().hasCooldown(player)) {
				ComboFly.get().getCombatManager().removeCooldown(player);
			}
		}
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if (ComboFly.get().getCombatManager().hasCooldown(player)) ComboFly.get().getCombatManager().removeCooldown(player);

		if (!ComboFly.get().getStaffManager().isStaffMode(player)) {
			for (String kit: ComboFly.get().getKitConfig().getConfiguration().getConfigurationSection("KITS.").getKeys(false)) {
				if (!ComboFly.get().getKitManager().getDefaultKit().equals(kit)) {
					return;
				}
				ComboFly.get().getKitManager().equipKit(ComboFly.get().getKitManager().getDefaultKit(), player);
			}
		}
		if (ComboFly.get().getSpawnManager().getSpawnLocation() != null) {
			player.teleport(ComboFly.get().getSpawnManager().getSpawnLocation());
		} else System.out.println("No Spawn Location!");
	}

	@EventHandler
	private void onCombat(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			Player damager = (Player) event.getDamager();
			for (String claim : ComboFly.get().getClaimManager().getClaimList()) {
				CuboidSelection selection = new CuboidSelection(
						ComboFly.get().getClaimManager().getClaimWorld(claim),
						ComboFly.get().getClaimManager().getLocation(claim, "cornerA"),
						ComboFly.get().getClaimManager().getLocation(claim, "cornerB"));
				if (selection.contains(player.getLocation())
						&& !ComboFly.get().getClaimManager().isPvPClaim(claim)) {
					return;
				}
				if (selection.contains(damager.getLocation())
						&& !ComboFly.get().getClaimManager().isPvPClaim(claim)) {
					return;
				}
			}
			if (ComboFly.get().getStaffManager().isStaffMode(player) || ComboFly.get().getStaffManager().isStaffMode(damager)) {
				return;
			}
			ComboFly.get().getCombatManager().setCooldown(player);
			ComboFly.get().getCombatManager().setCooldown(damager);
		}
	}
}
