package dev.panda.combofly.managers;

import java.util.Set;

import dev.panda.combofly.ComboFly;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WarpManager {
	
	public void createWarp(String warp, Player player) {
		ComboFly.get().getWarpConfig().getConfiguration().set("WARPS." + warp + ".WORLD", player.getWorld().getName());
		ComboFly.get().getWarpConfig().getConfiguration().set("WARPS." + warp + ".X", player.getLocation().getX());
		ComboFly.get().getWarpConfig().getConfiguration().set("WARPS." + warp + ".Y", player.getLocation().getY());
		ComboFly.get().getWarpConfig().getConfiguration().set("WARPS." + warp + ".Z", player.getLocation().getZ());
		ComboFly.get().getWarpConfig().getConfiguration().set("WARPS." + warp + ".YAW", player.getLocation().getYaw());
		ComboFly.get().getWarpConfig().getConfiguration().set("WARPS." + warp + ".PITCH", player.getLocation().getPitch());
		ComboFly.get().getWarpConfig().save();
		ComboFly.get().getWarpConfig().reload();
	}
	
	public void deleteWarp(String warp) {
		ComboFly.get().getWarpConfig().getConfiguration().set("WARPS." + warp, null);
		ComboFly.get().getWarpConfig().save();
		ComboFly.get().getWarpConfig().reload();
	}
	
	public Location getWarpLocation(String warp) {
		return new Location(
				getWarpWorld(warp), 
				getWarpX(warp), 
				getWarpY(warp), 
				getWarpZ(warp), 
				getWarpYaw(warp), 
				getWarpPitch(warp));
	}

	public Set<String> getWarps() {
		return ComboFly.get().getWarpConfig().getConfiguration().getConfigurationSection("WARPS").getKeys(false);
	}
	
	private World getWarpWorld(String warp) {
		return Bukkit.getWorld(ComboFly.get().getWarpConfig().getString("WARPS." + warp + ".WORLD"));
	}
	
	private double getWarpX(String warp) {
		return ComboFly.get().getWarpConfig().getDouble("WARPS." + warp + ".X");
	}

	private double getWarpY(String warp) {
		return ComboFly.get().getWarpConfig().getDouble("WARPS." + warp + ".Y");
	}

	private double getWarpZ(String warp) {
		return ComboFly.get().getWarpConfig().getDouble("WARPS." + warp + ".Z");
	}

	private int getWarpYaw(String warp) {
		return ComboFly.get().getWarpConfig().getInt("WARPS." + warp + ".YAW");
	}

	private int getWarpPitch(String warp) {
		return ComboFly.get().getWarpConfig().getInt("WARPS." + warp + ".PITCH");
	}
}
