package dev.panda.combofly.listeners;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderpearlListener implements Listener {

	public EnderpearlListener() {
		Bukkit.getServer().getPluginManager().registerEvents(this, ComboFly.get());
	}

	@EventHandler
	public void onPearl(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();

		if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {

			if (player.getItemInHand().getType().equals(Material.ENDER_PEARL)
					&& !player.getGameMode().equals(GameMode.CREATIVE)) {

				if (ComboFly.get().getEnderpearlManager().hasCooldown(player)) {
					event.setCancelled(true);
					player.updateInventory();
					player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("ENDERPEARL.ON-COOLDOWN")
							.replace("{cooldown}", ComboFly.get().getEnderpearlManager().getCooldown(player))));
					return;
				}
				ComboFly.get().getEnderpearlManager().setCooldown(player);
			}
		}
	}
}
