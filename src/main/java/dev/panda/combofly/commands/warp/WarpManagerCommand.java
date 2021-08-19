package dev.panda.combofly.commands.warp;

import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.entity.Player;
import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;

public class WarpManagerCommand extends BaseCommand {

	@Command(name = "warpmanager", permission = "pandafly.warpmanager", aliases = {"warpm", "wmanager", "wm"})
	@Override
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();
		String label = command.getLabel();

		if (args.length < 1) {
			this.getUsage(player, label);
			return;
		}

		if (args[0].equalsIgnoreCase("create")) {
			if (args.length < 2) {
				player.sendMessage(CC.translate("&cUsage: /" + label + " create <warpName>"));
				return;
			}

			String warpName = CC.capitalize(args[1]);

			if (ComboFly.get().getWarpConfig().getString("WARPS." + warpName) != null) {
				player.sendMessage(CC.getWarpNotFound(warpName));
				return;
			}

			ComboFly.get().getWarpManager().createWarp(warpName, player);
			player.sendMessage(CC.translate("&aWarp '" + warpName + "' has been create."));
		}
		else if (args[0].equalsIgnoreCase("delete")) {

			if (args.length < 2) {
				player.sendMessage(CC.translate("&cUsage: /" + label + " delete <warpName>"));
				return;
			}
			String warpName = CC.capitalize(args[1]);

			if (ComboFly.get().getWarpConfig().getString("WARPS." + warpName) == null) {
				player.sendMessage(CC.getWarpNotFound(warpName));
				return;
			}

			ComboFly.get().getWarpManager().deleteWarp(warpName);
			player.sendMessage(CC.translate("&aWarp '" + warpName + "' has been delete."));
		}
		else if (args[0].equalsIgnoreCase("set")){

			if (args.length < 2) {
				player.sendMessage(CC.translate("&cUsage: /" + label + " set <warpName>"));
				return;
			}
			String warpName = CC.capitalize(args[1]);

			if (ComboFly.get().getWarpConfig().getString("WARPS." + warpName) == null) {
				player.sendMessage(CC.getWarpNotFound(warpName));
				return;
			}

			ComboFly.get().getWarpManager().createWarp(warpName, player);
			player.sendMessage(CC.translate("&aYou have set new Location for Warp '" + warpName + "'."));
		}
		else if (args[0].equalsIgnoreCase("list")) {
			player.sendMessage(CC.translate("&cAvailables Warps: " + ComboFly.get().getWarpManager().getWarps()));
		}
		else {
			this.getUsage(player, label);
		}
	}

	private void getUsage(Player player, String label) {
		player.sendMessage(CC.translate("&7&m-------------------------"));
		player.sendMessage(CC.translate("&4&lWarpManager Commands"));
		player.sendMessage(CC.translate(""));
		player.sendMessage(CC.translate("&c/" + label + " create <warpName>"));
		player.sendMessage(CC.translate("&c/" + label + " delete <warpName>"));
		player.sendMessage(CC.translate("&c/" + label + " set <warpName>"));
		player.sendMessage(CC.translate("&c/" + label + " list"));
		player.sendMessage(CC.translate("&7&m-------------------------"));
	}
}
