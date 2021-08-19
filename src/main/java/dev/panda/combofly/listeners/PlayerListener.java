package dev.panda.combofly.listeners;

import dev.panda.combofly.ComboFly;

import dev.panda.combofly.commands.essentials.BuildCommand;
import dev.panda.combofly.profile.Profile;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import dev.panda.combofly.utilities.CC;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class PlayerListener implements Listener {

	public PlayerListener() {
		Bukkit.getServer().getPluginManager().registerEvents(this, ComboFly.get());
	}

	@EventHandler
	private void cancelSpawn(PlayerMoveEvent event) {
		if (ComboFly.get().getSpawnManager().hasCooldown(event.getPlayer())) {
			if (event.getFrom().getBlockX() == event.getTo().getBlockX()
					&& event.getFrom().getBlockY() == event.getTo().getBlockY()
					&& event.getFrom().getBlockZ() == event.getTo().getBlockZ())
				return;
			ComboFly.get().getSpawnManager().removeRunnable(event.getPlayer());
			ComboFly.get().getSpawnManager().removeCooldown(event.getPlayer());
			event.getPlayer().sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("SPAWN.CANCEL")));
		}
	}

	@EventHandler
	private void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
	}

	@EventHandler
	public void onDropItem(PlayerDropItemEvent event) {
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	private void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		event.setCancelled(!BuildCommand.mode.contains(player.getName()));
	}

	@EventHandler
	private void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		event.setCancelled(!BuildCommand.mode.contains(player.getName()));
	}

	@EventHandler
	private void onWeather(WeatherChangeEvent event){
		event.setCancelled(true);
	}

	@EventHandler
	private void onChunkUnload(ChunkUnloadEvent event) {
		event.setCancelled(true);
	}
}